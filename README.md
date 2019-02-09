# JIMI REST APP


Restaurant table management web application.
To be used from a tactile screen no less than 7.9 inches horizontally.


## Accounts

_Admin:_

* User: _admin_

* Password: _admin_

_Waiter:_

* User: _user_

* Password: _user_


## Authors

* Alonso, Juan Manuel
* Capparelli, Martín
* Cifuentes, Ignacio
* Nielavitzky, Ioni Zelig

## Version Log

### V1 (427615a8e0d2cbe8c96b8b6a173bdf16a71fad35)

* Views with JSP 
* Persistence with JDBC
 
### V2 (80e2d61f9a570613b4a8ca4f41cc8b3c3284f8d7)

* Changed Persistence to Hibernate
* Change DB structure (@see migrations note)

## Screen Resolutions Supported
*  1024x768
*  1366x768

## DB Migrations V1 to V2
Migration queries should be run on database **BEFORE** deploying V2 release.
Queries should prepare database for new structure and migrate information, afterwards hibernate will generate
missing constraints, foreigein keys and else.

Structure Queries 
```
ALTER TABLE public.dishes ADD minStock int DEFAULT 0 NULL;
ALTER TABLE public.dishes ALTER COLUMN name TYPE varchar(25) USING name::varchar(25);
ALTER TABLE public.dishes ALTER COLUMN price TYPE real USING price::real;
ALTER TABLE public.users ALTER COLUMN userid TYPE bigint USING userid::bigint;
ALTER TABLE public.user_roles RENAME COLUMN role TO roles;
ALTER TABLE public.user_roles ALTER COLUMN roles TYPE varchar(255) USING roles::varchar(255);
ALTER TABLE public.tables ALTER COLUMN name TYPE varchar(20) USING name::varchar(20);
ALTER TABLE public.tables RENAME COLUMN statusid TO status;
ALTER TABLE public.tables RENAME COLUMN orderid TO order_id;
ALTER TABLE public.tables ALTER COLUMN order_id TYPE bigint USING order_id::bigint;
create table order_donedishes
(
  order_id       bigint  not null,
  donedishes     integer,
  donedishes_key integer not null,
  constraint order_donedishes_pkey
  primary key (order_id, donedishes_key)
);
ALTER TABLE public.orders RENAME COLUMN orderid TO id;
ALTER TABLE public.orders ALTER COLUMN id TYPE bigint USING id::bigint;
ALTER TABLE public.orders ALTER COLUMN openedat TYPE timestamp USING openedat::timestamp;
ALTER TABLE public.orders ALTER COLUMN closedat TYPE timestamp USING closedat::timestamp;
ALTER TABLE public.orders RENAME COLUMN statusid TO status;
ALTER TABLE public.orders ALTER COLUMN total TYPE real USING total::real;
ALTER TABLE public.orders_items RENAME COLUMN orderid TO order_id;
ALTER TABLE public.orders_items ALTER COLUMN order_id TYPE bigint USING order_id::bigint;
ALTER TABLE public.orders_items RENAME COLUMN dishid TO undonedishes_key;
ALTER TABLE public.orders_items RENAME COLUMN quantity TO undonedishes;
ALTER TABLE public.orders_items RENAME TO order_undonedishes;
ALTER TABLE public.user_roles RENAME TO user_role;
ALTER TABLE public.users ALTER COLUMN username TYPE varchar(40) USING username::varchar(40);
-- Migrate table status enum value
UPDATE tables SET status = status-1;

-- Migrate order status enum values
UPDATE orders SET status = status-1;
```

## Development
For development go to /reactUI/src/conf.js and setup the location of the development server with CORS enabled. Generaly
will be `http://localhost:8080`. Then start tomcat and get workink :)

## Production
For production build go to /reactUI/src/conf.js and set all API data to blank. 
We will serve the api on the same domain as the page.
Navigate to /reactUI and run `npm run build`. Then copy recursivly all content from /reactUI/* to /webapp/src/main/webapp.
Finally on the root folder of the project package the application with `mvn package`.
The final war will be in `/webapp/target/webapp.war`




