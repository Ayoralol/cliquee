from flask import Flask 
from flask_cors import CORS
from config import Config
from flask_jwt_extended import JWTManager
from .extensions import db
from .routes.user_routes import user_bp
from .routes.group_routes import group_bp
from .routes.notification_routes import notification_bp
from .routes.friendship_routes import friendship_bp
from .routes.conversation_routes import conversation_bp
from .routes.admin_routes import admin_bp

jwt = JWTManager()

def create_app():
    app = Flask(__name__)
    CORS(app, resources={r"/*": {"origins": "*"}})
    app.config.from_object(Config)
    db.init_app(app)
    jwt.init_app(app)

    with app.app_context():
        from .models import audit_log, block, conversation, event_comment, event_participant, event, friend_request, friendship, group_availability, group_message, group, message, notification, user_group, user
        db.create_all()
        app.register_blueprint(user_bp)
        app.register_blueprint(group_bp)
        app.register_blueprint(notification_bp)
        app.register_blueprint(friendship_bp)
        app.register_blueprint(conversation_bp)
        app.register_blueprint(admin_bp)

    return app
