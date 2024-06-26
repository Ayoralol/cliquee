from app.models.notification import Notification
from app.models.user_group import User_Group
from app.services.user_service import get_username_by_id_service
from app.services.audit_log_service import create_audit_log, create_audit_log_inc_related_id
from ..extensions import db

def create_notification(user_id, data):
    notification = Notification(user_id=user_id, sender_id=data.get('sender_id'), type=data.get('type'), related_id=data.get('related_id'), message=data.get('message'))
    db.session.add(notification)
    db.session.commit()

def create_group_notification(group_id, data):
    user_groups = User_Group.query.filter_by(group_id=group_id).all()
    group_member_ids = [user_group.user_id for user_group in user_groups]
    notifications = [Notification(user_id=user_id, sender_id=data.get('sender_id'), type=data.get('type'), related_id=data.get('related_id'), message=data.get('message')) for user_id in group_member_ids]
    for notification in notifications:
        db.session.add(notification)
    db.session.commit()

def get_all_notifications_service(current_user_id):
    notifications = Notification.query.filter_by(user_id=current_user_id).all()
    if not notifications:
        return {'message': 'No notifications found'}, 404
    create_audit_log(current_user_id, 'GET_ALL_NOTIFICATIONS')
    return {'notifications': [{'id': notification.id, 'sender': get_username_by_id_service(notification.sender_id), 'type': notification.type, 'related_id': notification.related_id, 'message': notification.message, 'is_read': notification.is_read, 'responded': notification.responded} for notification in notifications]}, 200

def get_notification_service(notification_id, current_user_id):
    notification = Notification.query.filter_by(id=notification_id).first()
    if not notification:
        return {'message': 'Notification not found'}, 404
    if notification.user_id != current_user_id:
        return {'message': 'Unauthorized'}, 401
    notification.is_read = True
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, notification.related_id, 'GET_NOTIFICATION')
    return {'id': notification.id, 'sender': get_username_by_id_service(notification.sender_id), 'type': notification.type, 'related_id': notification.related_id, 'message': notification.message, 'responded': notification.responded}, 200

def read_notification_service(notification_id, current_user_id):
    notification = Notification.query.filter_by(id=notification_id).first()
    if not notification:
        return {'message': 'Notification not found'}, 404
    if notification.user_id != current_user_id:
        return {'message': 'Unauthorized'}, 401
    notification.is_read = True
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, notification.related_id, 'READ_NOTIFICATION')
    return {'message': 'Notification read successfully'}, 200

def respond_to_notification_service(notification_id, current_user_id, response):
    from app.services.friendship_service import accept_friend_request_service, deny_friend_request_service
    notification = Notification.query.filter_by(id=notification_id).first()
    if not notification:
        return {'message': 'Notification not found'}, 404
    if notification.user_id != current_user_id:
        return {'message': 'Unauthorized'}, 401
    type = notification.type
    if type == 'FRIEND_REQUEST':
        if response.get('response') == 'ACCEPT':
            request_response, request_status_code = accept_friend_request_service(notification.related_id, current_user_id, notification.sender_id)
            if request_status_code != 200:
                return request_response, request_status_code
            notification.responded = True
            db.session.commit()
            create_notification(notification.sender_id, {'sender_id': current_user_id, 'type': 'FRIEND_REQUEST_ACCEPTED', 'related_id': notification.related_id, 'message': f'{get_username_by_id_service(current_user_id)} has accepted your friend request!'})
            return {'message': 'Friend request accepted'}, 200
        elif response.get('response') == 'DENY':
            request_response, request_status_code = deny_friend_request_service(notification.related_id, current_user_id, notification.sender_id)
            if request_status_code != 200:
                return request_response, request_status_code
            db.session.delete(notification)
            db.session.commit()
            return {'message': 'Friend request declined'}, 200
    notification.is_read = True
    notification.responded = True
    db.session.commit()
    return {'message': 'Notification responded successfully'}, 200