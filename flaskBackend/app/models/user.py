from datetime import datetime
from ..extensions import db

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(64), index=True, unique=True)
    email = db.Column(db.String(120), index=True, unique=True)
    password_hash = db.Column(db.String(128))
    first_name = db.Column(db.String(64))
    last_name = db.Column(db.String(64))
    role = db.Column(db.String(64), default="USER")
    privacy = db.Column(db.String(64), default="PUBLIC")
    created_at = db.Column(db.DateTime, default=datetime.now())
    updated_at = db.Column(db.DateTime, default=datetime.now(), onupdate=datetime.now())

    audit_log = db.relationship('Audit_Log', backref='user', lazy=True)
    blocked_users = db.relationship('Block', foreign_keys='Block.blocker_id', backref='blocker', lazy='dynamic', cascade='all, delete-orphan') 
    blocked_by_users = db.relationship('Block', foreign_keys='Block.blocked_id', backref='blocked', lazy='dynamic', cascade='all, delete-orphan') 
    conversation_user_one = db.relationship('Conversation', foreign_keys='Conversation.user_one_id', backref='user_one', lazy='dynamic', cascade='all, delete-orphan')
    conversation_user_two = db.relationship('Conversation', foreign_keys='Conversation.user_two_id', backref='user_two', lazy='dynamic', cascade='all, delete-orphan')
    event_comment = db.relationship('Event_Comment', backref='user', lazy=True)
    event_participant = db.relationship('Event_Participant', backref='user', lazy=True)
    friend_request_send = db.relationship('Friend_Request', foreign_keys='Friend_Request.sender_id', backref='sender', lazy='dynamic', cascade='all, delete-orphan')
    friend_request_receive = db.relationship('Friend_Request', foreign_keys='Friend_Request.receiver_id', backref='receiver', lazy='dynamic', cascade='all, delete-orphan')
    friendship_user_one = db.relationship('Friendship', foreign_keys='Friendship.user_one_id', backref='user_one', lazy='dynamic', cascade='all, delete-orphan')
    friendship_user_two = db.relationship('Friendship', foreign_keys='Friendship.user_two_id', backref='user_two', lazy='dynamic', cascade='all, delete-orphan')
    group_availability = db.relationship('Group_Availability', backref='user', lazy=True)
    group_message = db.relationship('Group_Message', backref='user', lazy=True)
    message = db.relationship('Message', backref='user', lazy=True)
    notification_user = db.relationship('Notification', foreign_keys='Notification.user_id', backref='user', lazy='dynamic', cascade='all, delete-orphan')
    notification_sender = db.relationship('Notification', foreign_keys='Notification.sender_id', backref='sender', lazy='dynamic', cascade='all, delete-orphan')
    user_group = db.relationship('User_Group', backref='user', lazy=True)

    def __repr__(self):
        return f'<User {self.username}>'

