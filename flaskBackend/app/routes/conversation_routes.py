from flask import Blueprint, jsonify, request
from ..services.conversation_service import *

conversation_bp = Blueprint('conversations', __name__, url_prefix='/conversations')

@conversation_bp.route('/<current_user_id>/all', methods=['GET'])
def get_all_conversations(current_user_id):
    response, status_code = get_all_conversations_service(current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/<conversation_id>', methods=['GET'])
def get_conversation(conversation_id):
    current_user_id = request.args.get('current_user_id')
    response, status_code = get_conversation_service(conversation_id, current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/create/<friend_id>', methods=['POST'])
def create_conversation(friend_id):
    current_user_id = request.args.get('current_user_id')
    response, status_code = create_conversation_service(current_user_id, friend_id)
    return jsonify(response), status_code

@conversation_bp.route('/<conversation_id>/messages', methods=['GET'])
def get_messages(conversation_id):
    current_user_id = request.args.get('current_user_id')
    response, status_code = get_messages_service(conversation_id, current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/<conversation_id>/send', methods=['POST'])
def send_message(conversation_id):
    current_user_id = request.args.get('current_user_id')
    response, status_code = send_message_service(conversation_id, current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/group/<group_id>', methods=['GET'])
def get_group_conversation(group_id):
    current_user_id = request.args.get('current_user_id')
    response, status_code = get_group_conversation_service(group_id, current_user_id)
    return jsonify(response), status_code

@conversation_bp.route('/group/<group_id>/send', methods=['POST'])
def send_group_message(group_id):
    current_user_id = request.args.get('current_user_id')
    response, status_code = send_group_message_service(group_id, current_user_id)
    return jsonify(response), status_code