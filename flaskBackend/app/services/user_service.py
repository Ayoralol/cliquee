from app.models.block import Block
from app.models.user import User
from app.services.block_service import single_block_check
from app.services.audit_log_service import create_audit_log, create_audit_log_inc_related_id, create_audit_log_inc_other_user
from sqlalchemy import or_
from ..extensions import db

def get_user_by_id_service(user_id, current_user_id):
    user = User.query.filter_by(id=user_id).first()
    if not user:
        return {'error': 'User not found'}, 404
    if single_block_check(user.id, current_user_id):
        return {'error': 'User not found'}, 404
    create_audit_log_inc_other_user(current_user_id, user_id, 'GET_USER_BY_ID')
    return {'username': user.username, "first_name": user.first_name, "last_name": user.last_name}, 200

def get_user_by_username_service(username, current_user_id):
    user = User.query.filter_by(username=username).first()
    if not user:
        return {'error': 'User not found'}, 404
    if single_block_check(user.id, current_user_id):
        return {'error': 'User not found'}, 404
    create_audit_log_inc_other_user(current_user_id, username, 'GET_USER_BY_USERNAME')
    return {'username': user.username, "first_name": user.first_name, "last_name": user.last_name}, 200

def get_user_by_email_service(email, current_user_id):
    user = User.query.filter_by(email=email).first()
    if not user:
        return {'error': 'User not found'}, 404
    if single_block_check(user.id, current_user_id):
        return {'error': 'User not found'}, 404
    create_audit_log_inc_other_user(current_user_id, email, 'GET_USER_BY_EMAIL')
    return {'username': user.username, "first_name": user.first_name, "last_name": user.last_name}, 200

def search_users_service(current_user_id, keyword):
    if not keyword:
        return {'error': 'Missing search keyword'}, 400
    users = User.query.filter(
        or_(
            User.username.like(f'%{keyword}%'),
            User.first_name.like(f'%{keyword}%'),
            User.last_name.like(f'%{keyword}%'),
            User.email.like(f'%{keyword}%')
        )
    ).all()
    users = [user for user in users if not single_block_check(user.id, current_user_id)]
    if not users:
        return {'error': 'No users found'}, 404
    create_audit_log_inc_related_id(current_user_id, keyword, 'SEARCH_USERS')
    return {"users": [{'username': user.username, 'first_name': user.first_name, 'last_name': user.last_name} for user in users]}, 200

def update_user_service(current_user_id, data):
    user = User.query.filter_by(id=current_user_id).first()
    if not user:
        return {'error': 'User not found'}, 404
    if 'username' in data:
        user.username = data.get('username')
    if 'email' in data:
        user.email = data.get('email')
    if 'first_name' in data:
        user.first_name = data.get('first_name')
    if 'last_name' in data:
        user.last_name = data.get('last_name')
    db.session.commit()
    create_audit_log(current_user_id, 'UPDATE_USER')
    return {'message': 'User updated successfully'}, 200

def create_user_service(data):
    username = data.get('username')
    email = data.get('email')
    password = data.get('password')
    first_name = data.get('first_name')
    last_name = data.get('last_name')
    if not username or not email or not password or not first_name or not last_name:
        return {'error': 'Missing required fields'}, 400
    if User.query.filter_by(username=username).first():
        return {'error': 'Username already exists'}, 400
    if User.query.filter_by(email=email).first():
        return {'error': 'Email already exists'}, 400
    new_user = User(username=username, email=email, first_name=first_name, last_name=last_name)
    new_user.set_password(password)
    db.session.add(new_user)
    db.session.commit()
    create_audit_log(new_user.id, 'CREATE_USER')
    return {'message': 'User added successfully'}, 201

def delete_user_service(current_user_id):
    user = User.query.filter_by(id=current_user_id).first()
    if not user:
        return {'error': 'User not found'}, 404
    db.session.delete(user)
    db.session.commit()
    create_audit_log(current_user_id, 'DELETE_USER')
    return {'message': 'User deleted successfully'}, 200

def change_password_service(current_user_id, data):
    user = User.query.filter_by(id=current_user_id).first()
    old_password = data.get('old_password')
    new_password = data.get('new_password')
    if not user:
        return {'error': 'User not found'}, 404
    if not user.check_password(old_password):
        return {'error': 'Incorrect password'}, 400
    user.set_password(new_password)
    db.session.commit()
    create_audit_log(current_user_id, 'CHANGE_PASSWORD')
    return {'message': 'Password changed successfully'}, 200

def block_user_service(blocked_id, current_user_id):
    if blocked_id == current_user_id:
        return {'error': 'Cannot block self'}, 400
    if single_block_check(blocked_id, current_user_id):
        return {'error': 'User already blocked'}, 400
    new_block = Block(blocker_id=current_user_id, blocked_id=blocked_id)
    db.session.add(new_block)
    db.session.commit()
    create_audit_log_inc_other_user(current_user_id, blocked_id, 'BLOCK_USER')
    return {'message': 'User blocked successfully'}, 200

def unblock_user_service(blocked_id, current_user_id):
    block = Block.query.filter(Block.blocker_id == current_user_id, Block.blocked_id == blocked_id).first()
    if not block:
        return {'error': 'Block not found'}, 404
    db.session.delete(block)
    db.session.commit()
    create_audit_log_inc_other_user(current_user_id, blocked_id, 'UNBLOCK_USER')
    return {'message': 'User unblocked successfully'}, 200

def get_blocked_users_service(current_user_id):
    blocks = Block.query.filter_by(blocker_id=current_user_id).all()
    if not blocks:
        return {'error': 'No blocked users found'}, 404
    blocked_users = [block.blocked_id for block in blocks]
    create_audit_log(current_user_id, 'GET_BLOCKED_USERS')
    return {"blocked_users": [{'username': user.username, "first_name": user.first_name, "last_name": user.last_name} for user in blocked_users]}, 200

def get_username_by_id_service(user_id):
    user = User.query.filter_by(id=user_id).first()
    if not user:
        return {'error': 'User not found'}, 404
    return user.username