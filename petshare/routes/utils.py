from datetime import datetime
from flask import request, json
from flask.json import JSONEncoder
from sqlalchemy.ext.declarative import DeclarativeMeta
from ..errors import APIException
from .. import db

def validate_parameters(params):
    """Validate that parameters exist in the request JSON or raise an exception."""
    validation_errors = []

    for p in params:
        if request.json.get(p) is None:
            validation_errors.append(p)

    if validation_errors:
        missing_fields = ', '.join(validation_errors)
        full_errors = ['Missing field {}.'.format(e) for e in validation_errors]
        error_message = 'Unable to validate fields: {}'.format(missing_fields)
        raise APIException(error_message, errors=full_errors)
    else:
        return


class CustomJSONEncoder(JSONEncoder):
    """JSON encoder to account for various simplifications"""
    def default(self, obj):
        try:
            if isinstance(obj, db.Model):
                temp = obj.to_dict(exclude=getattr(obj, 'json_hidden'))
                return temp

        except TypeError:
            pass
        else:
            return list(iterable)

        try:
            rv = JSONEncoder.default(self, obj)
        except:
            print(obj)
            raise

        return rv
