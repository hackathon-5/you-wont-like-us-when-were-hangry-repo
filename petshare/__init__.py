import os
import sys
import logging

from flask import Flask, g, jsonify
from flask.ext.sqlalchemy import SQLAlchemy

from .config import Config

app = Flask(__name__)

# Set configuration module
app.config.from_object(Config)

# Rest of environment
db = SQLAlchemy(app)

# Auth Checks
# app.before_request(whatever)
# app.after_request(whatever)

# Register Blueprints
# from wherever import whatever_bp
# app.register_blueprint(whatever_bp)

# Health check EP
@app.route('/')
def ping():
  return jsonify({'status': 'OK'})

# Populate db with any new tables
db.create_all()
