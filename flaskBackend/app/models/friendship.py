from datetime import datetime
from ..extensions import db

class Friendship(db.Model):
    user_one_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False, primary_key=True)
    user_two_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False, primary_key=True)
    created_at = db.Column(db.DateTime, default=datetime.now())


    def __repr__(self):
        return f'<Friendship {self.user_one_id} and {self.user_two_id}>'