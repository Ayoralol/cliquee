from app.models.friendship import Friendship
from app.models.user import User
from app.models.friend_request import Friend_Request
from sqlalchemy import or_
from app.services.block_service import single_block_check
from app.services.notification_service import create_notification
from app.services.user_service import get_username_by_id_service
from app.services.audit_log_service import create_audit_log, create_audit_log_inc_other_user
from ..extensions import db

def get_friends_service(current_user_id):
    friends = Friendship.query.filter(
        or_(
            Friendship.user_one_id == current_user_id,
            Friendship.user_two_id == current_user_id
        )
    ).all()
    create_audit_log(current_user_id, 'GET_FRIENDS')
    return {'friends': [{'id': get_friend_id(current_user_id, friend.user_one_id, friend.user_two_id), 'username': get_username_by_id_service(get_friend_id(current_user_id, friend.user_one_id, friend.user_two_id))} for friend in friends]}, 200

def get_friend_id(current_user_id, user_one, user_two):
    if user_one == current_user_id:
        return user_two
    return user_one

def send_friend_request_service(friend_id, current_user_id):
    friend = User.query.filter_by(id=friend_id).first()
    if single_block_check(friend_id, current_user_id) or not friend:
        return {'error': 'User not found'}, 404
    friend_request = Friend_Request(sender_id=current_user_id, receiver_id=friend_id)
    db.session.add(friend_request)
    db.session.commit()
    create_notification(friend_id, {'sender_id': current_user_id, 'type': 'FRIEND_REQUEST', 'related_id': friend_request.id, 'message': f'{get_username_by_id_service(current_user_id)} has sent you a friend request!'})
    create_audit_log_inc_other_user(current_user_id, friend_id, 'SEND_FRIEND_REQUEST')
    return {'message': 'Friend request sent'}, 200

def get_friend_requests_service(current_user_id):
    friend_requests = Friend_Request.query.filter_by(receiver_id=current_user_id).all()
    create_audit_log(current_user_id, 'GET_FRIEND_REQUESTS')
    return {'friend_requests': [{'request_id': request.id, 'username': request.sender_id.username, 'first_name': request.user.first_name, 'last_name': request.user.last_name} for request in friend_requests]}, 200

def accept_friend_request_service(request_id, current_user_id):
    friend_request = Friend_Request.query.filter_by(id=request_id).first()
    friend_id = friend_request.sender_id
    if not friend_request or not friend_request.receiver_id == current_user_id or not friend_request.sender_id == friend_id:
        return {'error': 'Friend request not found'}, 404
    new_friendship = Friendship(user_one_id=current_user_id, user_two_id=friend_id)
    friend_request.status = 'ACCEPTED'
    db.session.add(new_friendship)
    db.session.commit()
    create_notification(friend_id, {'sender_id': current_user_id, 'type': 'FRIEND_REQUEST_ACCEPTED', 'related_id': request_id, 'message': f'{get_username_by_id_service(current_user_id)} has accepted your friend request!'})
    create_audit_log_inc_other_user(current_user_id, friend_id, 'ACCEPT_FRIEND_REQUEST')
    return {'message': 'Friend request accepted'}, 200

def deny_friend_request_service(request_id, current_user_id):
    friend_request = Friend_Request.query.filter_by(id=request_id).first()
    friend_id = friend_request.sender_id
    if not friend_request or not friend_request.receiver_id == current_user_id or not friend_request.sender_id == friend_id:
        return {'error': 'Friend request not found'}, 404
    db.session.delete(friend_request)
    db.session.commit()
    create_audit_log_inc_other_user(current_user_id, friend_id, 'DENY_FRIEND_REQUEST')
    return {'message': 'Friend request denied'}, 200
    
