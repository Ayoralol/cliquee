from ..extensions import db

class X(db.Model):
    id = db.Column(db.Integer, primary_key=True)

    def __repr__(self):
        return f'<X {self.name}>'