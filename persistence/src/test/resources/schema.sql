CREATE TABLE IF NOT EXISTS users (
  userid   INTEGER IDENTITY PRIMARY KEY,
  username varchar(100) UNIQUE,
  password varchar(100)
);

CREATE TABLE IF NOT EXISTS dishes (
  dishid INTEGER IDENTITY PRIMARY KEY,
  name   varchar(100),
  price  FLOAT,
  stock  INTEGER
);

CREATE TABLE IF NOT EXISTS tables
(
  name     VARCHAR(100) NOT NULL,
  tableid  INTEGER IDENTITY PRIMARY KEY,
  statusid INTEGER      NOT NULL,
  orderid  INTEGER,
  diners   INTEGER
);

CREATE TABLE IF NOT EXISTS orders
(
  orderid INTEGER IDENTITY PRIMARY KEY,
  openedAt TIMESTAMP,
  closedAt TIMESTAMP,
  statusid INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS orders_items
(
  orderid  INTEGER NOT NULL,
  dishid   INTEGER NOT NULL,
  quantity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles
(
  userid   INTEGER NOT NULL,
  role     VARCHAR(30)
);