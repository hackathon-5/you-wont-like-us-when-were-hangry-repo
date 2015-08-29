from datetime import datetime
from flask import request, json
from flask.json import JSONEncoder
from sqlalchemy.ext.declarative import DeclarativeMeta
from .errors import APIException

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

            # UNIX timestamps from datetimes
            if isinstance(obj, datetime):
                if obj.utcoffset() is not None:
                    obj = obj - obj.utcoffset()

                seconds = int(calendar.timegm(obj.timetuple()))
                return seconds

            iterable = iter(obj)
        except TypeError:
            pass
        else:
            return list(iterable)

        return JSONEncoder.default(self, obj)
