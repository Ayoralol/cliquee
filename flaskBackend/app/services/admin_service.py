from app.models.user import User
from app.models.group import Group
from app.models.audit_log import Audit_Log
from app.services.audit_log_service import create_audit_log_service
from ..extensions import db

def get_all_users_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    users = User.query.all()
    create_audit_log_service(admin_id, 'GET_ALL_USERS')
    return [{'id': user.id, 'username': user.username, 'email': user.email, 'first_name': user.first_name, 'last_name': user.last_name, 'created_at': user.created_at, 'updated_at': user.created_at} for user in users], 200

def get_all_groups_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    groups = Group.query.all()
    create_audit_log_service(admin_id, 'GET_ALL_GROUPS')
    return [{'id': group.id, 'name': group.name,'description': group.description, 'created_at': group.created_at, 'updated_at': group.created_at, 'members': [{'member_id': member.id, 'member_username': member.username} for member in group.members]} for group in groups], 200

def get_logs_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    logs = Audit_Log.query.all()
    create_audit_log_service(admin_id, 'GET_AUDIT_LOGS')
    return {[{'id': log.id, 'user_id': log.user_id, 'action': log.action, 'details': log.details, 'created_at': log.created_at} for log in logs]}, 200

def clear_logs_service(admin_id):
    admin = User.query.get(admin_id)
    if not admin.role == 'ADMIN' or not admin:
        return {'message': 'Unauthorized'}, 404
    logs = Audit_Log.query.all()
    for log in logs:
        db.session.delete(log)
    db.session.commit()
    create_audit_log_service(admin_id, 'CLEAR_AUDIT_LOGS')
    return {'message': 'Logs cleared'}, 200