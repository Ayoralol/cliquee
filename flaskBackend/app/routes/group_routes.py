from flask import Blueprint, request
from ..services.group_service import *

group_bp = Blueprint('groups', __name__, url_prefix='/groups')

@group_bp.route('/create', methods=['POST'])
def create_group():
    current_user_id = request.args.get('current_user_id')
    data = request.get_json()
    return create_group_service(current_user_id, data)

@group_bp.route('/search', methods=['GET'])
def search_groups():
    current_user_id = request.args.get('current_user_id')
    keyword = request.args.get('keyword')
    return search_groups_service(current_user_id, keyword)

@group_bp.route('/<current_user_id>/all', methods=['GET'])
def get_groups(current_user_id):
    return get_groups_service(current_user_id)

@group_bp.route('/<group_id>', methods=['GET'])
def get_group(group_id):
    current_user_id = request.args.get('current_user_id')
    return get_group_service(group_id, current_user_id)

@group_bp.route('/<group_id>/availabilities', methods=['GET'])
def get_group_availabilities(group_id):
    current_user_id = request.args.get('current_user_id')
    return get_group_availabilities_service(group_id, current_user_id)

@group_bp.route('/<group_id>/availabilities/create', methods=['POST'])
def create_group_availability(group_id):
    current_user_id = request.args.get('current_user_id')
    data = request.get_json()
    return create_group_availability_service(group_id, current_user_id, data)

@group_bp.route('/<group_id>/availabilities/remove/<availability_id>', methods=['DELETE'])
def remove_group_availability(group_id, availability_id):
    current_user_id = request.args.get('current_user_id')
    return remove_group_availability_service(group_id, availability_id, current_user_id)

@group_bp.route('/<group_id>/events', methods=['GET'])
def get_group_events(group_id):
    current_user_id = request.args.get('current_user_id')
    return get_group_events_service(group_id, current_user_id)

@group_bp.route('/<group_id>/events/create', methods=['POST'])
def create_group_event(group_id):
    current_user_id = request.args.get('current_user_id')
    data = request.get_json()
    return create_group_event_service(group_id, current_user_id, data)

@group_bp.route('/<group_id>/events/<event_id>', methods=['GET'])
def get_group_event(group_id, event_id):
    current_user_id = request.args.get('current_user_id')
    return get_group_event_service(group_id, event_id, current_user_id)

@group_bp.route('/<group_id>/events/<event_id>/update', methods=['PUT'])
def update_group_event(group_id, event_id):
    current_user_id = request.args.get('current_user_id')
    data = request.get_json()
    return update_group_event_service(group_id, event_id, current_user_id, data)

@group_bp.route('/<group_id>/events/<event_id>/cancel', methods=['DELETE'])
def cancel_group_event(group_id, event_id):
    current_user_id = request.args.get('current_user_id')
    return cancel_group_event_service(group_id, event_id, current_user_id)

@group_bp.route('/<group_id>/events/<event_id>/participants', methods=['GET'])
def get_event_participants(group_id, event_id):
    current_user_id = request.args.get('current_user_id')
    return get_event_participants_service(group_id, event_id, current_user_id)

@group_bp.route('/<group_id>/events/<event_id>/join', methods=['POST'])
def join_group_event(group_id, event_id):
    current_user_id = request.args.get('current_user_id')
    return join_group_event_service(group_id, event_id, current_user_id)
    

@group_bp.route('/<group_id>/events/<event_id>/leave', methods=['DELETE'])
def leave_group_event(group_id, event_id):
    current_user_id = request.args.get('current_user_id')
    return leave_group_event_service(group_id, event_id, current_user_id)

@group_bp.route('/<group_id>/members', methods=['GET'])
def get_group_members(group_id):
    current_user_id = request.args.get('current_user_id')
    return get_group_members_service(group_id, current_user_id)

# GROUP ADMIN ONLY ENDPOINTS BELOW

@group_bp.route('/<group_id>/update', methods=['PUT'])
def update_group(group_id):
    current_user_id = request.args.get('current_user_id')
    data = request.get_json()
    return update_group_service(group_id, current_user_id, data)

@group_bp.route('/<group_id>/members/add/<friend_id>', methods=['POST'])
def add_group_member(group_id, friend_id):
    current_user_id = request.args.get('current_user_id')
    return add_group_member_service(group_id, friend_id, current_user_id)

@group_bp.route('/<group_id>/members/remove/<member_id>', methods=['DELETE'])
def remove_group_member(group_id, member_id):
    current_user_id = request.args.get('current_user_id')
    return remove_group_member_service(group_id, member_id, current_user_id)

@group_bp.route('/<group_id>/members/promote/<member_id>', methods=['POST'])
def promote_group_member(group_id, member_id):
    current_user_id = request.args.get('current_user_id')
    return promote_group_member_service(group_id, member_id, current_user_id)

@group_bp.route('/<group_id>/members/demote/<member_id>', methods=['POST'])
def demote_group_member(group_id, member_id):
    current_user_id = request.args.get('current_user_id')
    return demote_group_member_service(group_id, member_id, current_user_id)