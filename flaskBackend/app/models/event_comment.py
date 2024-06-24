from datetime import datetime
from ..extensions import db

class Event_Comment(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    event_id = db.Column(db.Integer, db.ForeignKey('event.id'), nullable=False)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    comment = db.Column(db.String(64))
    created_at = db.Column(db.DateTime, default=datetime.now())

    def __repr__(self):
        return f'<Event_Comment {self.comment} on {self.event_id}>'