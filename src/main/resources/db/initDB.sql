DROP TABLE user_roles IF EXISTS;
DROP TABLE user_votes IF EXISTS ;
DROP TABLE dishes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  nickname         VARCHAR(20)             NOT NULL,
  name             VARCHAR(255)            NOT NULL,
  password         VARCHAR(255)            NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOLEAN DEFAULT TRUE    NOT NULL
);

CREATE UNIQUE INDEX users_unique_nickname_idx ON USERS (nickname);

CREATE TABLE user_roles
(
  user_id          INTEGER                 NOT NULL,
  role_id          VARCHAR(255),
  CONSTRAINT user_role_idx UNIQUE (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  description      VARCHAR(255)            NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE dishes
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  restaurant_id    INTEGER NOT NULL,
  date_time        TIMESTAMP NOT NULL ,
  name             VARCHAR(255)            NOT NULL,
  price            NUMERIC(10,2)           NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX dishes_unique_rest_datetime_idx ON DISHES (restaurant_id, date_time);

CREATE TABLE user_votes
(
  id                          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  user_id                     INTEGER                   NOT NULL ,
  restaurant_id               INTEGER                   NOT NULL ,
  date_time                   TIMESTAMP DEFAULT now()   NOT NULL ,
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);