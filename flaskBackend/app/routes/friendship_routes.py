from flask import Blueprint, jsonify, request
from ..services.friendship_service import *

friendship_bp = Blueprint('friendships', __name__, url_prefix='/friendships')

@friendship_bp.route('/<current_user_id>', methods=['GET'])
def get_friends(current_user_id):
    response, status_code = get_friends_service(current_user_id)
    return jsonify(response), status_code

@friendship_bp.route('/request/<friend_id>', methods=['POST'])
def send_friend_request(friend_id):
    current_user_id = request.args.get('current_user_id')
    response, status_code = send_friend_request_service(friend_id, current_user_id)
    return jsonify(response), status_code

@friendship_bp.route('/requests/<current_user_id>', methods=['GET'])
def get_friend_requests(current_user_id):
    response, status_code = get_friend_requests_service(current_user_id)
    return jsonify(response), status_code