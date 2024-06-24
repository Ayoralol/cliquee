from flask import Blueprint, request, jsonify
from ..services.user_service import *
user_bp = Blueprint('users', __name__, url_prefix='/users')

@user_bp.route('/<user_id>', methods=['GET'])
def get_user(user_id):
    current_user_id = request.args.get('current_user_id') # UPDATE THIS TO CHECK
    return get_user_service(user_id)

