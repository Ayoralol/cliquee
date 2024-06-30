from app.models.block import Block
from sqlalchemy import or_


def single_block_check(user_id, current_user_id):
    blocked = Block.query.filter(
        or_(
            (Block.blocker_id == current_user_id) & (Block.blocked_id == user_id),
            (Block.blocker_id == user_id) & (Block.blocked_id == current_user_id)
        )
    ).first()

    if blocked is not None:
        return True
    return False