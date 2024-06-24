from app.services.block_service import single_block_check
from flask import Blueprint, request, jsonify
from sqlalchemy import or_
from ..services.user_service import *
user_bp = Blueprint('users', __name__, url_prefix='/users')

@user_bp.route('/<user_id>', methods=['GET'])
def get_user_by_id(user_id):
    current_user_id = request.args.get('current_user_id')
    return get_user_by_id_service(user_id, current_user_id)

@user_bp.route('/username/<username>', methods=['GET'])
def get_user_by_username(username):
    current_user_id = request.args.get('current_user_id')
    return get_user_by_username_service(username, current_user_id)

@user_bp.route('/email/<email>', methods=['GET'])
def get_user_by_email(email):
    current_user_id = request.args.get('current_user_id')
    return get_user_by_email_service(email, current_user_id)

@user_bp.route('/search', methods=['GET'])
def search_users():
    current_user_id = request.args.get('current_user_id')
    keyword = request.args.get('keyword')
    return search_users_service(current_user_id, keyword)

@user_bp.route('/update/<current_user_id>', methods=['PUT'])
def update_user():
    current_user_id = request.args.get('current_user_id')
    data = request.get_json()
    return update_user_service(current_user_id, data)