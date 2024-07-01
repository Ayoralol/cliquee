from datetime import datetime
from ..extensions import db

class Audit_Log(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    action = db.Column(db.String(64))
    details = db.Column(db.String(255))
    created_at = db.Column(db.DateTime, default=datetime.now())


    def __repr__(self):
        return f'<Audit_Log {self.action}>'