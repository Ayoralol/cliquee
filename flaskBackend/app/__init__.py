from flask import Flask 
from config import Config
from flask_jwt_extended import JWTManager
from .extensions import db
from .routes.user_routes import user_bp

jwt = JWTManager()

def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)
    db.init_app(app)
    jwt.init_app(app)

    with app.app_context():
        from .models import user
        db.create_all()
        app.register_blueprint(user_bp)

    return app
