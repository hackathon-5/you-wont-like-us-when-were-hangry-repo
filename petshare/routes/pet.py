import logging

from flask import Blueprint, jsonify, request, g
from .. import app, db
from .utils import validate_parameters
from ..models import User, Pet, Shelter
from ..errors import APIException
from geopy.distance import vincenty

log = logging.getLogger(__name__)
pet_bp = Blueprint('pet', __name__, url_prefix='/pet')

@pet_bp.route('/findPets', methods=['POST'])
def get_pets():
    validate_parameters(['latitude', 'longitude'])

    user_pos = (float(request.json.get('latitude')), float(request.json.get('longitude')))
    shelters = Shelter.query.all()
    shelters_by_distance = {}

    for shelter in shelters:
        curr_shelter_pos = (shelter.latitude, shelter.longitude)
        distance = vincenty(shelter_pos, user_pos)
        shelters_by_distance[shelter.id] = distance

    sorted_by_distance = [(k,v) for v,k in sorted([(v,k) for k,v in shelters_by_distance.items()])]

    pets = []
    for shelter in sorted_by_distance:
        pets += Pet.query.filter_by(shelter.id==shelter).all()

    return jsonify(pets=pets)
