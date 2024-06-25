from app.models.friendship import Friendship
from app.models.user import User
from app.services.block_service import single_block_check
from ..extensions import db

def get_friends_service(current_user_id):
    friends = Friendship.query.filter_by(user_id=current_user_id).all()
    return [{'username': friend.friend.username, 'first_name': friend.first_name, 'last_name': friend.last_name} for friend in friends], 200

def send_friend_request_service(friend_id, current_user_id):
    friend = User.query.filter_by(id=friend_id).first()
    if single_block_check(friend_id, current_user_id) or not friend:
        return {'error': 'User not found'}, 404
    friendship = Friendship(user_one_id=current_user_id, user_two_id=friend_id)
    db.session.add(friendship)
    db.session.commit()
    return {'message': 'Friend request sent'}, 200

def get_friend_requests_service(current_user_id):
    friend_requests = Friendship.query.filter_by(user_two_id=current_user_id).all()
    return [{'username': request.user.username, 'first_name': request.user.first_name, 'last_name': request.user.last_name} for request in friend_requests], 200

def accept_friend_request_service(request_id, current_user_id):
    friend_request = Friendship.query.filter_by(user_one_id=request_id, user_two_id=current_user_id).first()
    if not friend_request:
        return {'error': 'Friend request not found'}, 404
    db.session.delete(friend_request)
    db.session.commit()
    return {'message': 'Friend request accepted'}, 200

def deny_friend_request_service(request_id, current_user_id):
    friend_request = Friendship.query.filter_by(user_one_id=request_id, user_two_id=current_user_id).first()
    if not friend_request:
        return {'error': 'Friend request not found'}, 404
    db.session.delete(friend_request)
    db.session.commit()
    return {'message': 'Friend request denied'}, 200
    