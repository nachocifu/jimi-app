CREATE TABLE IF NOT EXISTS users (
userid SERIAL PRIMARY KEY,
username varchar(100),
password varchar(100)
);

CREATE TABLE IF NOT EXISTS dishes (
  dishid SERIAL PRIMARY KEY,
  name varchar(100),
  price FLOAT,
  stock INTEGER 
);
