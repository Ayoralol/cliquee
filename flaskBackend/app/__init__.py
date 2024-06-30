from flask import Flask 
from flask_cors import CORS
from config import Config
from flask_jwt_extended import JWTManager
from .extensions import db
from .routes.user_routes import user_bp

jwt = JWTManager()

def create_app():
    app = Flask(__name__)
    CORS(app, resources={r"/*": {"origins": "*"}})
    app.config.from_object(Config)
    db.init_app(app)
    jwt.init_app(app)

    with app.app_context():
        from .models import audit_log, block, conversation, event_comment, event_participant, event, friend_request, friendship, group_availability, group_message, group, message, notification, user_group, user
        db.drop_all()
        db.create_all()
        app.register_blueprint(user_bp)

    return app
