from flask import Blueprint, jsonify, request
from flask_jwt_extended import get_jwt_identity
from ..services.notification_service import *

notification_bp = Blueprint('notifications', __name__, url_prefix='/notifications')

@notification_bp.route('/all', methods=['GET'])
def get_all_notifications():
    current_user_id = get_jwt_identity()
    response, status_code = get_all_notifications_service(current_user_id)
    return jsonify(response), status_code

@notification_bp.route('/get/<notification_id>', methods=['GET'])
def get_notification(notification_id):
    current_user_id = get_jwt_identity()
    response, status_code = get_notification_service(notification_id, current_user_id)
    return jsonify(response), status_code

@notification_bp.route('/read/<notification_id>', methods=['POST'])
def read_notification(notification_id):
    current_user_id = get_jwt_identity()
    response, status_code = read_notification_service(notification_id, current_user_id)
    return jsonify(response), status_code

@notification_bp.route('/respond/<notification_id>', methods=['POST'])
def respond_to_notification(notification_id):
    current_user_id = get_jwt_identity()
    data = request.get_json()
    response, status_code = respond_to_notification_service(notification_id, current_user_id, data)
    return jsonify(response), status_code