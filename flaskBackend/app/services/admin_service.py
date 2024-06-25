from app.models.user import User
from app.models.group import Group
from app.models.audit_log import Audit_Log
from ..extensions import db

def get_all_users_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    users = User.query.all()
    return [{'id': user.id, 'username': user.username, 'email': user.email, 'first_name': user.first_name, 'last_name': user.last_name, 'created_at': user.created_at, 'updated_at': user.created_at} for user in users], 200

def get_all_groups_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    groups = Group.query.all()
    return [{'id': group.id, 'name': group.name,'description': group.description, 'created_at': group.created_at, 'updated_at': group.created_at, 'members': [{'member_id': member.id, 'member_username': member.username} for member in group.members]} for group in groups], 200

def get_logs_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    logs = Audit_Log.query.all()
    return {[{'id': log.id, 'user_id': log.user_id, 'action': log.action, 'details': log.details, 'created_at': log.created_at} for log in admin.logs]}, 200

def clear_logs_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    logs = Audit_Log.query.all()
    for log in logs:
        log.delete()
    return {'message': 'Logs cleared'}, 200