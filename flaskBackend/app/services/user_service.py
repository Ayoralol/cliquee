from app.models.user import User
from ..extensions import db
from flask import jsonify

def get_user_service(user_id):
    user = User.query.filter_by(id=user_id).first()
    if not user:
        return jsonify({'error': 'User not found'}), 404
    
    return jsonify({'user': user}), 200








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