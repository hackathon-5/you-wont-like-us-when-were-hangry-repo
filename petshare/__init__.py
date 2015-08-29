import os
import sys
import logging

from flask import Flask, g, jsonify
from flask.ext.sqlalchemy import SQLAlchemy
from werkzeug.exceptions import default_exceptions

from .config import Config
from .errors import handle_error

app = Flask(__name__)

# Set configuration module
app.config.from_object(Config)

# Rest of environment
db = SQLAlchemy(app)

# Error handling
for code in default_exceptions.keys():
    app.error_handler_spec[None][code] = handle_error

# Auth Checks
# app.before_request(whatever)
# app.after_request(whatever)

# Register Blueprints
from . import routes

app.register_blueprint(routes.login.login_bp)

# Health check EP
@app.route('/')
def ping():
  return jsonify({'status': 'OK'})

# Populate db with any new tables
db.create_all()
