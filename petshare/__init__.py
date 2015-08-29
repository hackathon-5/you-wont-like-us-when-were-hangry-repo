import os
import sys
import logging

from flask import Flask, g, jsonify
from flask.ext.sqlalchemy import SQLAlchemy
from werkzeug.exceptions import default_exceptions

from .config import Config
from .errors import APIException

app = Flask(__name__)

# Logging
class RequestContextualFilter(logging.Filter):
    def filter(self, log_record):
        with app.app_context():
            log_record.user_id = getattr(g, 'user', None)
            return True

log_format = "%(levelname)s\t[%(asctime)s]\t[%(module)s:%(funcName)s:%(lineno)d]: %(message)s"
formatter = logging.Formatter(log_format)
handler = logging.StreamHandler(sys.stdout)
handler.setFormatter(formatter)

app.logger.addFilter(RequestContextualFilter())
app.logger.addHandler(handler)
app.logger.setLevel(logging.DEBUG)
app.logger.info('Logging has been configured.')


# Set configuration module
app.config.from_object(Config)

# Rest of environment
db = SQLAlchemy(app)

# Error handling
@app.errorhandler(APIException)
def handle_invalid_usage(error):
    response = jsonify(error.to_dict())
    response.status_code = error.status_code
    return response

# Auth Checks
# app.before_request(whatever)
# app.after_request(whatever)

# Register Blueprints
from .routes.login import login_bp
from .routes.evil import evil_bp

app.register_blueprint(login_bp)

# Health check EP
@app.route('/')
def ping():
  return jsonify({'status': 'OK'})

# Populate db with any new tables
db.create_all()
