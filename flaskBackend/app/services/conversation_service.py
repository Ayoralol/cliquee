from flask import request
from sqlalchemy import or_
from app.models.conversation import Conversation
from app.models.notification import Notification
from app.models.user import User
from app.models.message import Message
from app.models.group import Group
from app.models.group_message import Group_Message
from app.services.notification_service import create_notification, create_group_notification
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
    return [(get_friends_username(current_user_id, conversation), get_most_recent_message(conversation)) for conversation in conversations], 200

def get_conversation_service(conversation_id, current_user_id):
    conversation = Conversation.query.get(conversation_id)
    if not conversation:
        return {'message': 'Conversation not found'}, 404
    if conversation.user_one_id != current_user_id and conversation.user_two_id != current_user_id:
        return {'message': 'Unauthorized'}, 401
    return {'conversation_id': conversation.id, 'friend_username': get_friends_username(current_user_id, conversation), 'messages': [message.message for message in conversation.messages]}, 200

def create_conversation_service(current_user_id, friend_id):
    if current_user_id == friend_id:
        return {'message': 'Cannot create conversation with yourself'}, 400
    conversation = Conversation.query.filter(
        or_(
            Conversation.user_one_id == current_user_id,
            Conversation.user_two_id == current_user_id
        ),
        or_(
            Conversation.user_one_id == friend_id,
            Conversation.user_two_id == friend_id
        )
    ).first()
    if conversation:
        return {'message': 'Conversation already exists'}, 400
    new_conversation = Conversation(user_one_id=current_user_id, user_two_id=friend_id)
    new_conversation.save()
    return {'conversation_id': new_conversation.id}, 201

def send_message_service(conversation_id, current_user_id, message_content):
    conversation = Conversation.query.get(conversation_id)
    if not conversation:
        return {'message': 'Conversation not found'}, 404
    if conversation.user_one_id != current_user_id and conversation.user_two_id != current_user_id:
        return {'message': 'Unauthorized'}, 401
    new_message = Message(conversation_id=conversation_id, user_id=current_user_id, message=message_content)
    receiver = conversation.user_one_id if conversation.user_two_id == current_user_id else conversation.user_two_id
    send_notification(receiver, 'CONVERSATION', current_user_id, conversation_id)
    db.session.add(new_message)
    db.session.commit()
    return {'message': 'Message sent'}, 201

def get_group_conversation_service(group_id, current_user_id):
    group = Group.query.get(group_id)
    if not group:
        return {'message': 'Group not found'}, 404
    if current_user_id not in [member.id for member in group.members]:
        return {'message': 'Unauthorized'}, 401
    return {'group_id': group.id, 'group_name': group.name, 'messages': [message.message for message in group.messages]}, 200

def send_group_message_service(group_id, current_user_id, message_content):
    group = Group.query.get(group_id)
    if not group:
        return {'message': 'Group not found'}, 404
    if current_user_id not in [member.id for member in group.members]:
        return {'message': 'Unauthorized'}, 401
    new_message = Group_Message(group_id=group_id, user_id=current_user_id, message=message_content)
    db.session.add(new_message)
    send_notification(group_id, 'GROUP', current_user_id, new_message.id)
    db.session.commit()
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