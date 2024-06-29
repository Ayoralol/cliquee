from app.models.group import Group
from app.models.event_participant import Event_Participant
from app.models.group_availability import Group_Availability
from app.models.user_group import User_Group
from app.models.event import Event
from app.services.user_service import get_username_by_id_service
from app.services.notification_service import create_notification, create_group_notification
from app.services.audit_log_service import create_audit_log, create_audit_log_inc_related_id
from ..extensions import db

def create_group_service(current_user_id, data):
    name = data.get('name')
    description = data.get('description')
    if not name or not description:
        return {'error': 'Missing group name or description'}, 400
    group = Group(name=name, description=description)
    db.session.add(group)
    db.session.commit()
    user_group = User_Group(user_id=current_user_id, group_id=group.id, role='ADMIN')
    db.session.add(user_group)
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, group.id, 'CREATE_GROUP')
    return {'message': 'Group created successfully'}, 200

def search_groups_service(current_user_id, keyword):
    if not keyword:
        return {'error': 'Missing search keyword'}, 400
    user_groups = User_Group.query.filter_by(user_id=current_user_id).all()
    group_ids = [user_group.group_id for user_group in user_groups]
    groups = Group.query.filter(
        Group.id.in_(group_ids),
        Group.name.like(f'%{keyword}%')
    ).all()
    if not groups:
        return {'error': 'No groups found'}, 404
    create_audit_log_inc_related_id(current_user_id, keyword, 'SEARCH_GROUPS')
    return {"groups": [{'name': group.name, 'description': group.description} for group in groups]}, 200

def get_groups_service(current_user_id):
    user_groups = User_Group.query.filter_by(user_id=current_user_id).all()
    group_ids = [user_group.group_id for user_group in user_groups]
    groups = Group.query.filter(Group.id.in_(group_ids)).all()
    if not groups:
        return {'error': 'No groups found'}, 404
    create_audit_log(current_user_id, 'GET_GROUPS')
    return {"groups": [{'name': group.name, 'description': group.description} for group in groups]}, 200

def get_group_service(group_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    group = Group.query.filter_by(id=group_id).first()
    create_audit_log_inc_related_id(current_user_id, group_id, 'GET_GROUP')
    return {'name': group.name, 'description': group.description}, 200

def get_group_availabilities_service(group_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    group = Group.query.filter_by(id=group_id).first()
    create_audit_log_inc_related_id(current_user_id, group_id, 'GET_GROUP_AVAILABILITIES')
    return [{'username': get_username_by_id_service(availability.user_id), 'day': availability.day, 'start_time': availability.start_time, 'end_time': availability.end_time} for availability in group.group_availability], 200

def get_user_availabilities_service(group_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    group = Group.query.filter_by(id=group_id).first()
    create_audit_log_inc_related_id(current_user_id, group_id, 'GET_USER_AVAILABILITIES')
    return [{'day': availability.day, 'start_time': availability.start_time, 'end_time': availability.end_time} for availability in group.group_availability], 200

def create_group_availability_service(group_id, current_user_id, data):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    day = data.get('day')
    start_time = data.get('start_time')
    end_time = data.get('end_time')
    if not day or not start_time or not end_time:
        return {'error': 'Missing availability data'}, 400
    availability = Group_Availability(group_id=group_id, user_id=current_user_id, day=day, start_time=start_time, end_time=end_time)
    db.session.add(availability)
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, group_id, 'CREATE_GROUP_AVAILABILITY')
    return {'message': 'Availability created successfully'}, 200

def remove_group_availability_service(group_id, availability_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    availability = Group_Availability.query.filter_by(id=availability_id).first()
    if not availability:
        return {'error': 'Availability not found'}, 404
    db.session.delete(availability)
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, group_id, 'REMOVE_GROUP_AVAILABILITY')
    return {'message': 'Availability removed successfully'}, 200

def get_group_events_service(group_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    events = Event.query.filter_by(group_id=group_id).all()
    create_audit_log_inc_related_id(current_user_id, group_id, 'GET_GROUP_EVENTS')
    return {"events": [{'name': event.name, 'description': event.description, 'location': event.location, 'start_time': event.start_time, 'end_time': event.end_time} for event in events.events]}, 200

def create_group_event_service(group_id, current_user_id, data):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    name = data.get('name')
    description = data.get('description')
    location = data.get('location')
    start_time = data.get('start_time')
    end_time = data.get('end_time')
    if not name or not description or not location or not start_time or not end_time:
        return {'error': 'Missing event data'}, 400
    event = Event(group_id=group_id, name=name, description=description, location=location, start_time=start_time, end_time=end_time)
    db.session.add(event)
    db.session.commit()
    create_group_notification(group_id, {'sender_id': current_user_id, 'type': 'GROUP_EVENT', 'related_id': event.id, 'message': f'{get_username_by_id_service(current_user_id)} has created an event in {get_group_name_by_id(group_id)}!'})
    create_audit_log_inc_related_id(current_user_id, group_id, 'CREATE_GROUP_EVENT')
    return {'message': 'Event created successfully'}, 200

def get_group_event_service(group_id, event_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    event = Event.query.filter_by(id=event_id).first()
    if not event:
        return {'error': 'Event not found'}, 404
    create_audit_log_inc_related_id(current_user_id, event_id, 'GET_GROUP_EVENT')
    return {'name': event.name, 'description': event.description, 'location': event.location, 'start_time': event.start_time, 'end_time': event.end_time}, 200

def update_group_event_service(group_id, event_id, current_user_id, data):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    event = Event.query.filter_by(id=event_id).first()
    if not event:
        return {'error': 'Event not found'}, 404
    name = data.get('name')
    description = data.get('description')
    location = data.get('location')
    start_time = data.get('start_time')
    end_time = data.get('end_time')
    if name:
        event.name = name
    if description:
        event.description = description
    if location:
        event.location = location
    if start_time:
        event.start_time = start_time
    if end_time:
        event.end_time = end_time
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, event_id, 'UPDATE_GROUP_EVENT')
    return {'message': 'Event updated successfully'}, 200

def cancel_group_event_service(group_id, event_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    event = Event.query.filter_by(id=event_id).first()
    if not event:
        return {'error': 'Event not found'}, 404
    db.session.delete(event)
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, event_id, 'CANCEL_GROUP_EVENT')
    return {'message': 'Event cancelled successfully'}, 200

def get_event_participants_service(group_id, event_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    event = Event.query.filter_by(id=event_id).first()
    if not event:
        return {'error': 'Event not found'}, 404
    if not event.event_participants:
        return {'error': 'No participants found'}, 404
    create_audit_log_inc_related_id(current_user_id, event_id, 'GET_EVENT_PARTICIPANTS')
    return {"participants": [{'username': get_username_by_id_service(participant.user_id)} for participant in event.event_participants]}, 200

def join_group_event_service(group_id, event_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    event = Event.query.filter_by(id=event_id).first()
    if not event:
        return {'error': 'Event not found'}, 404
    event_participant = Event_Participant(event_id=event_id, user_id=current_user_id)
    db.session.add(event_participant)
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, event_id, 'JOIN_GROUP_EVENT')
    return {'message': 'Joined event successfully'}, 200

def leave_group_event_service(group_id, event_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    event = Event.query.filter_by(id=event_id).first()
    if not event:
        return {'error': 'Event not found'}, 404
    event_participant = Event_Participant.query.filter_by(event_id=event_id, user_id=current_user_id).first()
    if not event_participant:
        return {'error': 'User not found in event'}, 404
    db.session.delete(event_participant)
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, event_id, 'LEAVE_GROUP_EVENT')
    return {'message': 'Left event successfully'}, 200

def get_group_members_service(group_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    user_groups = User_Group.query.filter_by(group_id=group_id).all()
    if not user_groups:
        return {'error': 'No members found'}, 404
    create_audit_log_inc_related_id(current_user_id, group_id, 'GET_GROUP_MEMBERS')
    return {"members": [{'username': get_username_by_id_service(user_group.user_id), 'role': user_group.role} for user_group in user_groups]}, 200

def update_group_service(group_id, current_user_id, data):
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group or user_group.role != 'ADMIN':
        return {'error': 'Group not found'}, 404
    group = Group.query.filter_by(id=group_id).first()
    if not group:
        return {'error': 'Group not found'}, 404
    name = data.get('name')
    description = data.get('description')
    if name:
        group.name = name
    if description:
        group.description = description
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, group_id, 'UPDATE_GROUP')
    return {'message': 'Group updated successfully'}, 200

def add_group_member_service(group_id, friend_id, current_user_id):
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group or user_group.role != 'ADMIN':
        return {'error': 'Group not found'}, 404
    user_group = User_Group.query.filter_by(user_id=friend_id, group_id=group_id).first()
    if user_group:
        return {'error': 'User already in group'}, 400
    user_group = User_Group(user_id=friend_id, group_id=group_id, role='MEMBER')
    db.session.add(user_group)
    db.session.commit()
    create_notification(friend_id, {'sender_id': current_user_id, 'type': 'GROUP_ADD', 'related_id': group_id, 'message': f'{get_username_by_id_service(current_user_id)} has added you to the group {get_group_name_by_id(group_id)}!'})
    create_audit_log_inc_related_id(current_user_id, group_id, 'ADD_GROUP_MEMBER')
    return {'message': 'User added to group successfully'}, 200

def remove_group_member_service(group_id, friend_id, current_user_id):
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group or user_group.role != 'ADMIN':
        return {'error': 'Group not found'}, 404
    user_group = User_Group.query.filter_by(user_id=friend_id, group_id=group_id).first()
    if not user_group:
        return {'error': 'User not in group'}, 400
    db.session.delete(user_group)
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, group_id, 'REMOVE_GROUP_MEMBER')
    return {'message': 'User removed from group successfully'}, 200

def promote_group_member_service(group_id, member_id, current_user_id):
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group or user_group.role != 'ADMIN':
        return {'error': 'Group not found'}, 404
    user_group = User_Group.query.filter_by(user_id=member_id, group_id=group_id).first()
    if not user_group:
        return {'error': 'User not in group'}, 400
    user_group.role = 'ADMIN'
    db.session.commit()
    create_notification(member_id, {'sender_id': current_user_id, 'type': 'GROUP_PROMOTE', 'related_id': group_id, 'message': f'{get_username_by_id_service(current_user_id)} has promoted you to admin in {get_group_name_by_id(group_id)}!'})
    create_audit_log_inc_related_id(current_user_id, group_id, 'PROMOTE_GROUP_MEMBER')
    return {'message': 'User promoted to admin successfully'}, 200

def demote_group_member_service(group_id, member_id, current_user_id):
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group or user_group.role != 'ADMIN':
        return {'error': 'Group not found'}, 404
    user_group = User_Group.query.filter_by(user_id=member_id, group_id=group_id).first()
    if not user_group:
        return {'error': 'User not in group'}, 400
    user_group.role = 'MEMBER'
    db.session.commit()
    create_audit_log_inc_related_id(current_user_id, group_id, 'DEMOTE_GROUP_MEMBER')
    return {'message': 'User demoted to member successfully'}, 200

def get_group_name_by_id(group_id):
    group = Group.query.filter_by(id=group_id).first()
    return group.name