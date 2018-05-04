CREATE TABLE IF NOT EXISTS users (
  userid   SERIAL PRIMARY KEY,
  username varchar(100),
  password varchar(100)
);

CREATE TABLE IF NOT EXISTS dishes (
  dishid SERIAL PRIMARY KEY,
  name   varchar(100),
  price  FLOAT,
  stock  INTEGER
);

CREATE TABLE IF NOT EXISTS "tables"
(
  name     VARCHAR(100)       NOT NULL,
  tableid  SERIAL PRIMARY KEY NOT NULL,
  statusid INTEGER            NOT NULL,
  orderid  INTEGER,
  diners   INTEGER
);

CREATE TABLE IF NOT EXISTS "orders"
(
  orderid SERIAL PRIMARY KEY NOT NULL,
  openedAt TIMESTAMP,
  closedAt TIMESTAMP,
  statusid INTEGER
);

CREATE TABLE IF NOT EXISTS orders_items
(
  orderid  INTEGER NOT NULL,
  dishid   INTEGER NOT NULL,
  quantity INTEGER NOT NULL
);