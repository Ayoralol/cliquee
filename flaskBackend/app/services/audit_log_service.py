from datetime import datetime
from app.models.audit_log import Audit_Log
from ..extensions import db

def create_audit_log(user_id, action):
    from app.services.user_service import get_username_by_id_service
    user = get_username_by_id_service(user_id)
    current_time = datetime.now()
    details = f'{user} called {action} at {current_time}'
    audit_log = Audit_Log(user_id=user_id, action=action, details=details)
    db.session.add(audit_log)
    db.session.commit()

def create_audit_log_inc_other_user(user_id, other_user_id, action):
    from app.services.user_service import get_username_by_id_service
    user = get_username_by_id_service(user_id)
    other_user = get_username_by_id_service(other_user_id)
    current_time = datetime.now()
    details = f'{user} called {action} on {other_user} at {current_time}'
    audit_log = Audit_Log(user_id=user_id, action=action, details=details)
    db.session.add(audit_log)
    db.session.commit()

def create_audit_log_inc_related_id(user_id, related_id, action):
    from app.services.user_service import get_username_by_id_service
    user = get_username_by_id_service(user_id)
    current_time = datetime.now()
    details = f'{user} called {action} on {related_id} at {current_time}'
    audit_log = Audit_Log(user_id=user_id, action=action, details=details)
    db.session.add(audit_log)
    db.session.commit()