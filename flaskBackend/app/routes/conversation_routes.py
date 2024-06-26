from flask import Blueprint, jsonify, request
from flask_jwt_extended import get_jwt_identity, jwt_required
from ..services.conversation_service import *

conversation_bp = Blueprint('conversations', __name__, url_prefix='/conversations')

@conversation_bp.route('/all', methods=['GET'])
@jwt_required()
def get_all_conversations():
    current_user_id = get_jwt_identity()
    response, status_code = get_all_conversations_service(current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/<conversation_id>', methods=['GET'])
@jwt_required()
def get_conversation(conversation_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_conversation_service(conversation_id, current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/create/<friend_id>', methods=['POST'])
@jwt_required()
def create_conversation(friend_id):
    current_user_id = get_jwt_identity()
    response, status_code = create_conversation_service(current_user_id, friend_id)
    return jsonify(response), status_code

@conversation_bp.route('/<conversation_id>/send', methods=['POST'])
@jwt_required()
def send_message(conversation_id):
    current_user_id = get_jwt_identity()
    data = request.get_json()
    message_content = data.get('message')
    if not message_content:
        return {'message': 'Message is required'}, 400
    response, status_code = send_message_service(conversation_id, current_user_id, message_content)
    return jsonify(response), status_code

@conversation_bp.route('/group/<group_id>', methods=['GET'])
@jwt_required()
def get_group_conversation(group_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_group_conversation_service(group_id, current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/group/<group_id>/send', methods=['POST'])
@jwt_required()
def send_group_message(group_id):
    current_user_id = get_jwt_identity()
    data = request.get_json()
    message_content = data.get('message')
    if not message_content:
        return {'message': 'Message is required'}, 400
    response, status_code = send_group_message_service(group_id, current_user_id, message_content)
    return jsonify(response), status_code