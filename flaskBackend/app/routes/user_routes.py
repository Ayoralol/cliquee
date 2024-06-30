from flask import Blueprint, jsonify, request
from flask_jwt_extended import create_access_token, get_jwt_identity, jwt_required
from ..services.user_service import *

user_bp = Blueprint('users', __name__, url_prefix='/users')

@user_bp.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')
    if not username or not password:
        return jsonify({'error': 'Missing username or password'}), 400
    user = User.query.filter_by(username=username).first()
    if user is None or not user.check_password(password):
        return jsonify({'error': 'Invalid username or password'}), 401
    access_token = create_access_token(identity=user.id)
    return jsonify({'id': user.id, 'username': user.username, 'first_name': user.first_name, 'last_name': user.last_name, 'access_token': access_token}), 200

@user_bp.route('/<user_id>', methods=['GET'])
@jwt_required()
def get_user_by_id(user_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_user_by_id_service(user_id, current_user_id)
    return jsonify(response), status_code

@user_bp.route('/username/<username>', methods=['GET'])
@jwt_required()
def get_user_by_username(username):
    current_user_id = get_jwt_identity()
    response, status_code = get_user_by_username_service(username, current_user_id)
    return jsonify(response), status_code

@user_bp.route('/email/<email>', methods=['GET'])
@jwt_required()
def get_user_by_email(email):
    current_user_id = get_jwt_identity()
    response, status_code = get_user_by_email_service(email, current_user_id)
    return jsonify(response), status_code

@user_bp.route('/search', methods=['GET'])
@jwt_required()
def search_users():
    current_user_id = get_jwt_identity()
    keyword = request.args.get('keyword')
    response, status_code = search_users_service(current_user_id, keyword)
    return jsonify(response), status_code

@user_bp.route('/update', methods=['PUT'])
@jwt_required()
def update_user():
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = update_user_service(current_user_id, data)
    return jsonify(response), status_code

@user_bp.route('/create', methods=['POST'])
def create_user():
    data = request.get_json()
    response, status_code = create_user_service(data)
    return jsonify(response), status_code

@user_bp.route('/delete', methods=['DELETE'])
@jwt_required()
def delete_user():
    current_user_id = get_jwt_identity()
    response, status_code = delete_user_service(current_user_id)
    return jsonify(response), status_code

@user_bp.route('/change-password', methods=['PUT'])
@jwt_required()
def change_password():
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = change_password_service(current_user_id, data)
    return jsonify(response), status_code

@user_bp.route('/block/<blocked_id>', methods=['POST'])
@jwt_required()
def block_user(blocked_id):
    current_user_id = get_jwt_identity()
    response, status_code = block_user_service(blocked_id, current_user_id)
    return jsonify(response), status_code

@user_bp.route('/unblock/<blocked_id>', methods=['DELETE'])
@jwt_required()
def unblock_user(blocked_id):
    current_user_id = get_jwt_identity()
    response, status_code = unblock_user_service(blocked_id, current_user_id)
    return jsonify(response), status_code

@user_bp.route('/blocked', methods=['GET'])
@jwt_required()
def get_blocked_users():
    current_user_id = get_jwt_identity()
    response, status_code = get_blocked_users_service(current_user_id)
    return jsonify(response), status_code
