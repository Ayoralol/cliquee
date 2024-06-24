from datetime import datetime
from ..extensions import db

class Group(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(64), index=True, unique=False)
    description = db.Column(db.String(64))
    created_at = db.Column(db.DateTime, default=datetime.now())
    updated_at = db.Column(db.DateTime, default=datetime.now(), onupdate=datetime.now())

    events = db.relationship('Event', backref='group', lazy=True)
    group_availability = db.relationship('Group_Availability', backref='group', lazy=True)
    group_message = db.relationship('Group_Message', backref='group', lazy=True)
    user_group = db.relationship('User_Group', backref='group', lazy=True)

    def __repr__(self):
        return f'<Group {self.name}>'