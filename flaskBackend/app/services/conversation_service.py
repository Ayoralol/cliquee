from flask import request
from sqlalchemy import and_, or_
from app.models.conversation import Conversation
from app.models.notification import Notification
from app.models.user import User
from app.models.message import Message
from app.models.group import Group
from app.models.group_message import Group_Message
from app.models.user_group import User_Group
from app.services.notification_service import create_notification, create_group_notification
from app.services.audit_log_service import create_audit_log, create_audit_log_inc_other_user, create_audit_log_inc_related_id
from app.services.user_service import get_username_by_id_service
from ..extensions import db

def get_friends_username(current_user_id, conversation):
    if conversation.user_one_id == current_user_id:
        friend_username = User.query.get(conversation.user_two_id).username
    else:
        friend_username = User.query.get(conversation.user_one_id).username
    return friend_username

def get_most_recent_message(conversation):
    return conversation.messages[-1].message

def get_all_conversations_service(current_user_id):
    conversations = Conversation.query.filter(
        or_(
            Conversation.user_one_id == current_user_id,
            Conversation.user_two_id == current_user_id
        )
    ).all()
    if not conversations:
        return {'message': 'No conversations found'}, 404
    create_audit_log(current_user_id, 'GET_ALL_CONVERSATIONS')
    return {'conversations': [{'id': conversation.id,'username': get_friends_username(current_user_id, conversation), 'message': get_most_recent_message(conversation)} for conversation in conversations]}, 200

def get_conversation_service(conversation_id, current_user_id):
    conversation = Conversation.query.get(conversation_id)
    user_username = get_username_by_id_service(current_user_id)
    if not conversation:
        return {'message': 'Conversation not found'}, 404
    if conversation.user_one_id != current_user_id and conversation.user_two_id != current_user_id:
        return {'message': 'Unauthorized'}, 401
    audit_id = conversation.user_one_id if conversation.user_two_id == current_user_id else conversation.user_two_id
    create_audit_log_inc_other_user(current_user_id, audit_id, 'GET_CONVERSATION')
    messages = Message.query.filter_by(conversation_id=conversation_id).all()
    return {'id': conversation.id, 'friend_username': get_friends_username(current_user_id, conversation), 'messages': [{'username': get_username_by_id_service(message.user_id), 'message': message.message, 'sender': check_sender(get_username_by_id_service(message.user_id), user_username)} for message in messages]}, 200

def create_conversation_service(current_user_id, friend_id):
    if current_user_id == friend_id:
        return {'message': 'Cannot create conversation with yourself'}, 400
    conversation = Conversation.query.filter(
        and_(
            or_(
                Conversation.user_one_id == current_user_id,
                Conversation.user_two_id == current_user_id
            ),
            or_(
                Conversation.user_one_id == friend_id,
                Conversation.user_two_id == friend_id
            )
        )
    ).first()
    if conversation:
        return {'message': 'Conversation already exists'}, 400
    new_conversation = Conversation(user_one_id=current_user_id, user_two_id=friend_id)
    db.session.add(new_conversation)
    db.session.commit()
    create_audit_log_inc_other_user(current_user_id, friend_id, 'CREATE_CONVERSATION')
    return {'conversation_id': new_conversation.id}, 201

def send_message_service(conversation_id, current_user_id, message_content):
    conversation = Conversation.query.get(conversation_id)
    if not conversation:
        return {'message': 'Conversation not found'}, 404
    if conversation.user_one_id != current_user_id and conversation.user_two_id != current_user_id:
        return {'message': 'Unauthorized'}, 401
    new_message = Message(conversation_id=conversation_id, user_id=current_user_id, message=message_content)
    receiver = conversation.user_one_id if conversation.user_two_id == current_user_id else conversation.user_two_id
    db.session.add(new_message)
    db.session.commit()
    send_notification(receiver, 'CONVERSATION', current_user_id, conversation_id)
    create_audit_log_inc_other_user(current_user_id, receiver, 'SEND_MESSAGE')
    return {'message': 'Message sent'}, 201

def get_group_conversation_service(group_id, current_user_id):
    group = Group.query.get(group_id)
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group:
        return {'message': 'Unauthorized'}, 401
    user_username = get_username_by_id_service(current_user_id)
    create_audit_log_inc_related_id(current_user_id, group.id, 'GET_GROUP_CONVERSATION')
    messages = Group_Message.query.filter_by(group_id=group_id).all()
    if not messages:
        return {'group_id': group.id, 'group_name': group.name, 'messages': []}, 200
    return {'group_id': group.id, 'group_name': group.name, 'messages': [{'username': get_username_by_id_service(message.user_id), 'message': message.message, 'sender': check_sender(message.user_id, user_username)} for message in messages]}, 200

def send_group_message_service(group_id, current_user_id, message_content):
    group = Group.query.get(group_id)
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group:
        return {'message': 'Unauthorized'}, 401
    new_message = Group_Message(group_id=group_id, user_id=current_user_id, message=message_content)
    db.session.add(new_message)
    db.session.commit()
    send_notification(group_id, 'GROUP', current_user_id, new_message.id)
    create_audit_log_inc_related_id(current_user_id, group.id, 'SEND_GROUP_MESSAGE')
    return {'message': 'Message sent'}, 201

def send_notification(sender_id, type, receiver_or_group_id, conversation_or_message_id):
    if type == 'CONVERSATION':
        notification = {'user_id': receiver_or_group_id, 'sender_id': sender_id, 'type': 'CONVERSATION', 'related_id': conversation_or_message_id, 'message': f'{User.query.get(sender_id).username} has sent you a message!'}
        old_notification = Notification.query.filter_by(related_id=conversation_or_message_id).first()
        if old_notification:
            db.session.delete(old_notification)
        create_notification(receiver_or_group_id, notification)
    elif type == 'GROUP':
        notification = {'user_id': receiver_or_group_id, 'sender_id': sender_id, 'type': 'GROUP_MESSAGE', 'related_id': receiver_or_group_id, 'message': f'{User.query.get(sender_id).username} has sent a message in {Group.query.get(receiver_or_group_id).name}!'}
        old_notifications = Notification.query.filter_by(related_id=receiver_or_group_id, type='GROUP_MESSAGE').all()
        if old_notifications:
            for notification in old_notifications:
                db.session.delete(notification)
        create_group_notification(receiver_or_group_id, notification)

def check_sender(sender_username, current_user_username):
    return True if sender_username == current_user_username else False