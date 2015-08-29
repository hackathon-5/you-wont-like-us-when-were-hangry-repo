import time
import random
import os
import hashlib

from passlib.hash import bcrypt as bc
from flask import json
from . import app, db
from sqlalchemy.ext.mutable import Mutable
from sqlalchemy.ext.hybrid import hybrid_property
from sqlalchemy.dialects.postgres import ARRAY

class MutableList(Mutable, list):
    def append(self, value):
        list.append(self, value)
        self.changed()

    def remove(self, value):
        list.remove(self, value)
        self.changed()

    @classmethod
    def coerce(cls, key, value):
        if not isinstance(value, MutableList):
            if isinstance(value, list):
                return MutableList(value)
            return Mutable.coerce(key, value)
        else:
            return value


class Base(db.Model):
    @declare_attr
    def __tablename__(cls):
        return cls.__name__lower()

    id = db.Column(db.Integer, primary_key=True, default=hash(time.time() + random.randrange(1000, 9999)))
    created_at = db.Column(db.Integer, default=int(time.time()))
    updated_at = db.Column(db.Integer, default=int(time.time()), onupdate=int(time.time()))

    def get_field_names(self):
        return [p.key for p in self.__mapper__.iterate_properties if p.key[0] != '_']

    def to_dict(self):
        return {f: getattr(self, f) for f in self.get_field_names()}


class Pet(Base):
    name = db.Column(db.String, nullable=False)
    type = db.Column(db.String, nullable=False)
    description = db.Column(db.String, nullable=False)
    age = db.Column(db.Float)
    cuddle_score = db.Column(db.Float, default=0)
    photo_url = db.Column(db.String)
    reservations = db.relationship('Reservations', backref='pet', lazy='dynamic')
    shelter_id = db.Column(db.Integer, db.ForeignKey('shelter.id'))


class Shelter(Base):
    name = db.Column(db.String, nullable=False)
    description = db.Column(db.String, nullable=False)
    address = db.Column(db.String, nullable=False)
    latitude = db.Column(db.Float, nullable=False)
    longitude = db.Column(db.Float, nullable=False)
    open = db.Column(db.Integer, default=900)
    close = db.Column(db.Integer, default=1700)
    pets = db.relationship('Pet', backref='shelter', lazy='dynamic')


class Reservations(Base):
    date = db.Column(db.String, nullable=False)
    times = db.Column(MutableList.as_mutable(ARRAY(db.Integer)))
    pet_id = db.Column(db.Integer, db.ForeignKey('pet.id'))
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))


class User(Base):
    name = db.Column(db.String, nullable=False)
    email = db.Column(db.String, nullable=False, unique=True)
    phone = db.Column(db.Integer, nullable=False)
    reservations = db.relationship('Reservations', backref='user', lazy='dynamic')
    access_token = db.relationship('AccessToken', backref='user')

    _password = db.Column("password", db.String, nullable=False)

    @hybrid_property
    def password(self):
        return self._password

    @password.setter
    def password(self, value):
        self._password = bc.encrypt(value)

    def check_password(self, value):
        return bc.verify(value, self._password)


class AccessToken(Base):
    token = db.Column(db.String, default=hashlib.sha256(os.urandom(1024)).hexdigest())
    user_id = db.Column(db.Integer, ForeignKey('user.id'))