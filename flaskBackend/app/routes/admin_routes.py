from flask import Blueprint, jsonify, request
from flask_jwt_extended import get_jwt_identity
from ..services.admin_service import *

admin_bp = Blueprint('admin', __name__, url_prefix='/admin')

@admin_bp.route('/users', methods=['GET'])
def get_all_users():
    admin_id = get_jwt_identity()
    if not admin_id:
        return jsonify({'message': 'Admin ID required'}), 400
    response, status_code = get_all_users_service(admin_id)
    return jsonify(response), status_code

@admin_bp.route('/groups', methods=['GET'])
def get_all_groups():
    admin_id = get_jwt_identity()
    if not admin_id:
        return jsonify({'message': 'Admin ID required'}), 400
    response, status_code = get_all_groups_service(admin_id)
    return jsonify(response), status_code

@admin_bp.route('/logs', methods=['GET'])
def get_logs():
    admin_id = get_jwt_identity()
    if not admin_id:
        return jsonify({'message': 'Admin ID required'}), 400
    response, status_code = get_logs_service(admin_id)
    return jsonify(response), status_code

@admin_bp.route('/logs/clear', methods=['DELETE'])
def clear_logs():
    admin_id = get_jwt_identity()
    if not admin_id:
        return jsonify({'message': 'Admin ID required'}), 400
    response, status_code = clear_logs_service(admin_id)
    return jsonify(response), status_code
