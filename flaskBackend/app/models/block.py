from datetime import datetime
from ..extensions import db

class Block(db.Model):
    __tablename__ = 'blocks'
    blocker_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False, primary_key=True)
    blocked_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False, primary_key=True)
    created_at = db.Column(db.DateTime, default=datetime.now())

    def __repr__(self):
        return f'<Block {self.blocker_id} blocked {self.blocked_id}>'