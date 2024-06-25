from app.models.group import Group
from app.models.group_availability import Group_Availability
from app.models.user_group import User_Group
from app.services.user_service import get_username_by_id_service
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
    return [{'name': group.name, 'description': group.description} for group in groups], 200

def get_groups_service(current_user_id):
    user_groups = User_Group.query.filter_by(user_id=current_user_id).all()
    group_ids = [user_group.group_id for user_group in user_groups]
    groups = Group.query.filter(Group.id.in_(group_ids)).all()
    if not groups:
        return {'error': 'No groups found'}, 404
    return [{'name': group.name, 'description': group.description} for group in groups], 200

def get_group_service(group_id, current_user_id):
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group:
        return {'error': 'Group not found'}, 404
    group = Group.query.filter_by(id=group_id).first()
    return {'name': group.name, 'description': group.description}, 200

def get_group_availabilities_service(group_id, current_user_id):
    user_check = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_check:
        return {'error': 'Group not found'}, 404
    all_availabilities = Group_Availability.query.filter_by(group_id=group_id).all()
    return [{'username': get_username_by_id_service(availability.user_id), 'day': availability.day, 'start_time': availability.start_time, 'end_time': availability.end_time} for availability in all_availabilities], 200

def get_user_availabilities_service(group_id, current_user_id):
    user_group = User_Group.query.filter_by(user_id=current_user_id, group_id=group_id).first()
    if not user_group:
        return {'error': 'Group not found'}, 404
    group = Group.query.filter_by(id=group_id).first()
    return [{'day': availability.day, 'start_time': availability.start_time, 'end_time': availability.end_time} for availability in group.group_availability], 200
