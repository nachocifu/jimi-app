CREATE TABLE IF NOT EXISTS users (
userid INTEGER IDENTITY PRIMARY KEY,
username varchar(100),
password varchar(100)
);

CREATE TABLE IF NOT EXISTS dishes (
  dishid INTEGER IDENTITY PRIMARY KEY,
  name varchar(100),
  price FLOAT
);