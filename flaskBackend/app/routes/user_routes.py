from flask import Blueprint, request, jsonify
from ..services.user_service import add_user_service, get_users_service
user_bp = Blueprint('user_bp', __name__)

@user_bp.route('/users', methods=['POST'])
def add_user():
    data = request.get_json()
    return add_user_service(data)

@user_bp.route('/users', methods=['GET'])
def get_users():
    return get_users_service()