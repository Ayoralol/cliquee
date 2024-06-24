from app.models.user import User
from app.services.block_service import single_block_check
from sqlalchemy import or_
from ..extensions import db
from flask import jsonify, request

def get_user_by_id_service(user_id, current_user_id):
    user = User.query.filter_by(id=user_id).first()
    if not user:
        return jsonify({'error': 'User not found'}), 404
    if single_block_check(user.id, current_user_id):
        return jsonify({'error': 'User not found'}), 404
    return jsonify({'username': user.username, "first_name": user.first_name, "last_name": user.last_name}), 200

def get_user_by_username_service(username, current_user_id):
    user = User.query.filter_by(username=username).first()
    if not user:
        return jsonify({'error': 'User not found'}), 404
    if single_block_check(user.id, current_user_id):
        return jsonify({'error': 'User not found'}), 404
    return jsonify({'username': user.username, "first_name": user.first_name, "last_name": user.last_name}), 200

def get_user_by_email_service(email, current_user_id):
    user = User.query.filter_by(email=email).first()
    if not user:
        return jsonify({'error': 'User not found'}), 404
    if single_block_check(user.id, current_user_id):
        return jsonify({'error': 'User not found'}), 404
    return jsonify({'username': user.username, "first_name": user.first_name, "last_name": user.last_name}), 200

def search_users_service(current_user_id, keyword):
    if not keyword:
        return jsonify({'error': 'Missing search keyword'}), 400
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
        return jsonify({'error': 'No users found'}), 404
    return jsonify([{'username': user.username, 'first_name': user.first_name, 'last_name': user.last_name} for user in users]), 200

def update_user_service(current_user_id, data):
    user = User.query.filter_by(id=current_user_id).first()
    if not user:
        return jsonify({'error': 'User not found'}), 404
    if 'username' in data:
        user.username = data.get('username')
    if 'email' in data:
        user.email = data.get('email')
    if 'password' in data:
        user.password_hash = data.get('password') # ADD PASSWORD HASHING
    if 'first_name' in data:
        user.first_name = data.get('first_name')
    if 'last_name' in data:
        user.last_name = data.get('last_name')
    db.session.commit()
    return jsonify({'message': 'User updated successfully'}), 200







def add_user_service(data):
    username = data.get('username')
    email = data.get('email')
    password_hash = data.get('password')
    first_name = data.get('first_name')
    last_name = data.get('last_name')

    if not username or not email or not password_hash or not first_name or not last_name:
        return jsonify({'error': 'Missing required fields'}), 400
    
    new_user = User(username=username, email=email, password_hash=password_hash, first_name=first_name, last_name=last_name)
    db.session.add(new_user)
    db.session.commit()

    return jsonify({'message': 'User added successfully'}), 201