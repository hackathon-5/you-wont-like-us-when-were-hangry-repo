from flask import Blueprint, jsonify, request, g
from .. import app, db

evil_bp = Blueprint('evil', __name__, url_prefix='/evil')

@evil_bp.route('/never_come_here_simba', methods=['POST'])
def nuke_it():
    db.reflect()
    db.drop_all()

    rv = jsonify({'message': 'You have destoyed it you sick bastard.'})
    rv.status_code = 200
    return rv
