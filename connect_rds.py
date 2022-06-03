"""
RDS
"""
from enum import unique
from xmlrpc.client import Boolean
from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, DateTime, Boolean

USER = "postgres"
PW = "YOUR_AWS_PW"
URL = "YOUR_RDS_URL"
PORT = "YOUR_RDS_PORT"
DB = "postgres"

engine = create_engine("postgresql://{}:{}@{}:{}/{}".format(USER, PW, URL,PORT, DB))
db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))

Base = declarative_base()
Base.query = db_session.query_property()

class User(Base):
    __tablename__ = 'users'
    id = Column(Integer, primary_key=True)
    uid = Column(String(50), unique=True)
    pw = Column(String(120), unique=False)

    def __init__(self, uid=None, pw=None):
        self.uid = uid
        self.pw = pw

    def __repr__(self):
        return f'<User {self.uid!r}'

    def to_dict(self):
        return dict(
            id = self.id, 
            uid = self.uid, 
            pw = self.pw
        )

class Plan(Base):
    __tablename__ = 'plans'
    id = Column(Integer, primary_key=True)
    uid = Column(String(120), unique=False)
    title = Column(String(120), unique=False)
    year = Column(Integer, unique=False)
    month = Column(Integer, unique=False)
    day = Column(Integer, unique=False)
    memo = Column(String(100), unique=False)
    done = Column(Boolean, unique=False)

    def __init__(self, uid=None, title=None, year=None, month=None, day=None, memo=None, done=None):
        self.uid = uid
        self.title = title
        self.year = year
        self.month = month
        self.day = day
        self.memo = memo
        self.done = done

    def __repr__(self):
        return f'<Plan {self.title!r}'

    def to_dict(self):
        return dict(
            id = self.id, 
            uid = self.uid, 
            title = self.title, 
            year = self.year, 
            month = self.month, 
            day = self.day,
            memo = self.memo, 
            done = self.done
        )

#Base.metadata.drop_all(bind=engine)
#Base.metadata.create_all(bind=engine)

"""
Flask
"""

from flask import Flask
from flask import request
from flask import jsonify
from werkzeug.serving import WSGIRequestHandler
import json

WSGIRequestHandler.protocol_version = "HTTP/1.1"

app = Flask(__name__)

@app.route("/login", methods=['GET'])
def login():
    uid = request.args.get('uid')
    pw = request.args.get('pw')
    check = False
    result = db_session.query(User).all()
    for i in result:
        if i.uid == uid and i.pw == pw:
            check = True
    return jsonify(success=check)

@app.route("/adduser", methods=['POST'])
def add_user():
    content = request.get_json(silent=True)
    uid = content["uid"]
    pw = content["pw"]
    if db_session.query(User).filter_by(uid=uid).first() is None:
        u = User(uid=uid, pw=pw)
        db_session.add(u)
        db_session.commit()
        return jsonify(success=True)
    else:
        return jsonify(success=False)

@app.route("/checkid", methods=['GET'])
def check_id():
    uid = request.args.get('uid')
    check = True
    result = db_session.query(User).all()
    for i in result:
        if i.uid == uid:
            check = False
    return jsonify(success=check)

@app.route("/getplan", methods=['GET'])
def get_plan():
    uid = request.args.get('uid')
    result = db_session.query(Plan).all()
    user_plans = []
    for i in result:
        if i.uid == uid:
            user_plans.append(i.to_dict())
    return jsonify(plans=user_plans)


@app.route("/addplan", methods=['POST'])
def add_plan():
    content = request.get_json(silent=True)
    uid = content["uid"]
    title = content["title"]
    year = content["year"]
    month = content["month"]
    day = content["day"]
    memo = content["memo"]
    done = False
    p = Plan(uid=uid, title=title, year=year, month=month, day=day, memo=memo, done=done)
    db_session.add(p)
    db_session.commit()
    return jsonify(success=True)

@app.route("/deleteplan", methods=['GET'])
def delete_plan():
    id = request.args.get('id')
    check = True
    try:
        print("Del")
        obj = db_session.query(Plan).filter_by(id=id).one()
        db_session.delete(obj)
        db_session.commit()
    except:
        check = False
    return jsonify(success=check)

@app.route("/updateplan", methods=['POST'])
def update_plan():
    content = request.get_json(silent=True)
    id = content["id"]
    title = content["title"]
    year = content["year"]
    month = content["month"]
    day = content["day"]
    memo = content["memo"]
    obj = db_session.query(Plan).filter_by(id=id).one()
    obj.title = title
    obj.year = year
    obj.month = month
    obj.day = day
    obj.memo = memo
    db_session.commit()
    return jsonify(success=True)

@app.route("/updateplanstatus", methods=['GET'])
def update_plan_status():
    id = request.args.get('id')
    done = request.args.get('done')
    obj = db_session.query(Plan).filter_by(id=id).one()
    if done=="true":
        obj.done = True
    else:
        obj.done = False
    db_session.commit()
    return jsonify(success=True)

if __name__ == "__main__":
    app.run(host='localhost', port=8888)
