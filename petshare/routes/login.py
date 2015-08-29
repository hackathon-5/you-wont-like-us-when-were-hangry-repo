import logging

from flask import Blueprint, jsonify, request, g
from .. import app, db
from ..utils import validate_parameters
from ..models import User, AccessToken

log = logging.getLogger(__name__)
login_bp = Blueprint('login', __name__, url_prefix='/login')


@login_bp.route('/', methods=['POST'])
def login():
    validate_parameters(['email', 'password'])

    user = User.query.filter_by(email=request.json.get('email').lower()).first()
    if not user:
        raise APIException('User not found', status_code=404)

    if not user.check_password(request.json.get('password')):
        raise APIException('Bad login information', status_code=401)

    token = AccessToken(user_id=user.id)
    user.access_token = token.token

    db.session.add(token)
    db.session.commit()

    g.user = user

    rv = jsonify(user)
    rv.status_code = 200
    return rv


@login_bp.route('/sign_up', methods=['POST'])
def sign_up():
    #validate_parameters(['name', 'email', 'phone', 'password'])

    existing_user = User.query.filter_by(email=request.json.get('email').lower()).first()
    if existing_user:
        raise APIException('User already exists', status_code=409)

    new_token = AccessToken()

    new_user = User(name=request.json.get('name'),
                    email=request.json.get('email').lower(),
                    phone=request.json.get('phone'),
                    password=request.json.get('password'),
                    access_token=new_token.token)

    db.session.add(new_user)
    db.session.flush()

    new_token.user_id = new_user.id

    db.session.add(new_token)
    db.session.commit()

    g.user = new_user

    rv = jsonify(user=new_user)
    rv.status_code = 200
    return rv
