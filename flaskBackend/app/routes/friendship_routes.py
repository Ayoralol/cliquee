from flask import Blueprint, jsonify, request
from flask_jwt_extended import get_jwt_identity, jwt_required
from ..services.friendship_service import *

friendship_bp = Blueprint('friendships', __name__, url_prefix='/friendships')

@friendship_bp.route('/all', methods=['GET'])
@jwt_required()
def get_friends():
    current_user_id = get_jwt_identity()
    response, status_code = get_friends_service(current_user_id)
    return jsonify(response), status_code

@friendship_bp.route('/request/<friend_id>', methods=['POST'])
@jwt_required()
def send_friend_request(friend_id):
    current_user_id = get_jwt_identity()
    response, status_code = send_friend_request_service(friend_id, current_user_id)
    return jsonify(response), status_code

@friendship_bp.route('/requests', methods=['GET'])
@jwt_required()
def get_friend_requests():
    current_user_id = get_jwt_identity()
    response, status_code = get_friend_requests_service(current_user_id)
    return jsonify(response), status_code

@friendship_bp.route('/requests/accept/<request_id>', methods=['POST'])
@jwt_required()
def accept_friend_request(request_id):
    current_user_id = get_jwt_identity()
    response, status_code = accept_friend_request_service(request_id, current_user_id)
    return jsonify(response), status_code

@friendship_bp.route('/requests/deny/<request_id>', methods=['POST'])
@jwt_required()
def deny_friend_request(request_id):
    current_user_id = get_jwt_identity()
    response, status_code = deny_friend_request_service(request_id, current_user_id)
    return jsonify(response), status_code
