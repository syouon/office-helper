DROP DATABASE IF EXISTS officehelper_db;
CREATE DATABASE officehelper_db;
USE officehelper_db;

CREATE TABLE user (
  id    SERIAL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (email)
);

CREATE TABLE request (
  id              SERIAL,
  title           VARCHAR(255) NOT NULL,
  description     TEXT,
  url             VARCHAR(255),
  quantity        INT,
  status          VARCHAR(10) NOT NULL,
  creation_date   TIMESTAMP NOT NULL,
  order_date      TIMESTAMP NULL,
  reception_date  TIMESTAMP NULL,
  user_id         BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE USER IF NOT EXISTS 'developer'@'localhost' IDENTIFIED BY 'd3v3l0p3r';
GRANT ALL PRIVILEGES ON `officehelper_db`.* TO 'developer'@'localhost';
FLUSH PRIVILEGES;
