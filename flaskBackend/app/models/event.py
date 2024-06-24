from datetime import datetime
from ..extensions import db

class Event(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    group_id = db.Column(db.Integer, db.ForeignKey('group.id'), nullable=False)
    name = db.Column(db.String(64), index=True, unique=False)
    description = db.Column(db.String(64))
    location = db.Column(db.String(64))
    start_time = db.Column(db.DateTime)
    end_time = db.Column(db.DateTime)
    created_at = db.Column(db.DateTime, default=datetime.now())
    updated_at = db.Column(db.DateTime, default=datetime.now(), onupdate=datetime.now())

    event_participants = db.relationship('Event_Participant', backref='event', lazy=True)
    event_comments = db.relationship('Event_Comment', backref='event', lazy=True)

    def __repr__(self):
        return f'<Event {self.name}>'