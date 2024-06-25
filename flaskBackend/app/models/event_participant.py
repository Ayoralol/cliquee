from datetime import datetime
from ..extensions import db

class Event_Participant(db.Model):
    event_id = db.Column(db.Integer, db.ForeignKey('event.id'), nullable=False, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False, primary_key=True)
    created_at = db.Column(db.DateTime, default=datetime.now())

    def __repr__(self):
        return f'<Event_participant {self.user_id} in {self.event_id}>'