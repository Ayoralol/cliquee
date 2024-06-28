from flask import Blueprint, jsonify, request
from flask_jwt_extended import get_jwt_identity
from ..services.group_service import *

group_bp = Blueprint('groups', __name__, url_prefix='/groups')

@group_bp.route('/create', methods=['POST'])
def create_group():
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = create_group_service(current_user_id, data)
    return jsonify(response), status_code

@group_bp.route('/search', methods=['GET'])
def search_groups():
    current_user_id = get_jwt_identity()
    keyword = request.args.get('keyword')
    response, status_code = search_groups_service(current_user_id, keyword)
    return jsonify(response), status_code

@group_bp.route('/all', methods=['GET'])
def get_groups():
    current_user_id = get_jwt_identity()
    response, status_code = get_groups_service(current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>', methods=['GET'])
def get_group(group_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_group_service(group_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/availabilities', methods=['GET'])
def get_group_availabilities(group_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_group_availabilities_service(group_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/user_availabilities', methods=['GET'])
def get_user_availabilities(group_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_user_availabilities_service(group_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/availabilities/create', methods=['POST'])
def create_group_availability(group_id):
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = create_group_availability_service(group_id, current_user_id, data)
    return jsonify(response), status_code
    
@group_bp.route('/<group_id>/availabilities/remove/<availability_id>', methods=['DELETE'])
def remove_group_availability(group_id, availability_id):
    current_user_id = get_jwt_identity()
    response, status_code = remove_group_availability_service(group_id, availability_id, current_user_id)
    return jsonify(response), status_code
    
@group_bp.route('/<group_id>/events', methods=['GET'])
def get_group_events(group_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_group_events_service(group_id, current_user_id)
    return jsonify(response), status_code
    
@group_bp.route('/<group_id>/events/create', methods=['POST'])
def create_group_event(group_id):
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = create_group_event_service(group_id, current_user_id, data)
    return jsonify(response), status_code
    
@group_bp.route('/<group_id>/events/<event_id>', methods=['GET'])
def get_group_event(group_id, event_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_group_event_service(group_id, event_id, current_user_id)
    return jsonify(response), status_code
    
@group_bp.route('/<group_id>/events/<event_id>/update', methods=['PUT'])
def update_group_event(group_id, event_id):
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = update_group_event_service(group_id, event_id, current_user_id, data)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/events/<event_id>/cancel', methods=['DELETE'])
def cancel_group_event(group_id, event_id):
    current_user_id = get_jwt_identity()
    response, status_code = cancel_group_event_service(group_id, event_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/events/<event_id>/participants', methods=['GET'])
def get_event_participants(group_id, event_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_event_participants_service(group_id, event_id, current_user_id)
    return jsonify(response), status_code
    
@group_bp.route('/<group_id>/events/<event_id>/join', methods=['POST'])
def join_group_event(group_id, event_id):
    current_user_id = get_jwt_identity()
    response, status_code = join_group_event_service(group_id, event_id, current_user_id)
    return jsonify(response), status_code
    
@group_bp.route('/<group_id>/events/<event_id>/leave', methods=['DELETE'])
def leave_group_event(group_id, event_id):
    current_user_id = get_jwt_identity()
    response, status_code = leave_group_event_service(group_id, event_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/members', methods=['GET'])
def get_group_members(group_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_group_members_service(group_id, current_user_id)
    return jsonify(response), status_code
    
# GROUP ADMIN ONLY ENDPOINTS BELOW

@group_bp.route('/<group_id>/update', methods=['PUT'])
def update_group(group_id):
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = update_group_service(group_id, current_user_id, data)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/members/add/<friend_id>', methods=['POST'])
def add_group_member(group_id, friend_id):
    current_user_id = get_jwt_identity()
    response, status_code = add_group_member_service(group_id, friend_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/members/remove/<member_id>', methods=['DELETE'])
def remove_group_member(group_id, member_id):
    current_user_id = get_jwt_identity()
    response, status_code = remove_group_member_service(group_id, member_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/members/promote/<member_id>', methods=['POST'])
def promote_group_member(group_id, member_id):
    current_user_id = get_jwt_identity()
    response, status_code = promote_group_member_service(group_id, member_id, current_user_id)
    return jsonify(response), status_code

@group_bp.route('/<group_id>/members/demote/<member_id>', methods=['POST'])
def demote_group_member(group_id, member_id):
    current_user_id = get_jwt_identity()
    response, status_code = demote_group_member_service(group_id, member_id, current_user_id)
    return jsonify(response), status_code
