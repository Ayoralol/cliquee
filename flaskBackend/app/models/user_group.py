from ..extensions import db

class User_Group(db.Model):
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False, primary_key=True)
    group_id = db.Column(db.Integer, db.ForeignKey('group.id'), nullable=False, primary_key=True)
    role = db.Column(db.String(255), nullable=False, default="MEMBER")
    created_at = db.Column(db.DateTime, nullable=False)

    def __repr__(self):
        return f'<User_Group {self.user_id} is an {self.role} of {self.group_id}>'