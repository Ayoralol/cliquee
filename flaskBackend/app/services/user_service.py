from app.models.user import User
from ..extensions import db
from datetime import datetime
from flask import jsonify

def add_user_service(data):
    username = data.get('username')
    email = data.get('email')
    password_hash = data.get('password')
    first_name = data.get('first_name')
    last_name = data.get('last_name')
    role = 'USER'
    privacy = 'PUBLIC'
    created_at = datetime.now()
    updated_at = datetime.now()

    if not username or not email or not password_hash or not first_name or not last_name:
        return jsonify({'error': 'Missing required fields'}), 400
    
    new_user = User(username=username, email=email, password_hash=password_hash, first_name=first_name, last_name=last_name, role=role, privacy=privacy, created_at=created_at, updated_at=updated_at)
    db.session.add(new_user)
    db.session.commit()

    return jsonify({'message': 'User added successfully'}), 201

def get_users_service():
    users = User.query.all()
    return jsonify([{'id': user.id, 'username': user.username, 'email': user.email, 'first_name': user.first_name, "last_name": user.last_name} for user in users])

