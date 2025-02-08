DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS rents;


CREATE TABLE users
(
    id       BIGSERIAL,
    name     VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    role     VARCHAR NOT NULL,
    bill     BIGINT,
    ---------------------------------
    CONSTRAINT name_uq UNIQUE (name),
    CONSTRAINT user_id_pk PRIMARY KEY (id)
);

CREATE TABLE cars
(
    id           BIGSERIAL,
    seller_id    BIGSERIAL,
    name         VARCHAR NOT NULL,
    price        NUMERIC NOT NULL,
    is_available BOOLEAN NOT NULL,
    is_for_sale  BOOLEAN NOT NULL,
    --------------------------------------------------------------------
    CONSTRAINT car_id_pk PRIMARY KEY (id),
    CONSTRAINT seller_id_fk FOREIGN KEY (seller_id) REFERENCES users(id)
);

CREATE TABLE purchases
(
    id      BIGSERIAL,
    user_id BIGSERIAL,
    car_id  BIGSERIAL,
    payment VARCHAR,
    ------------------------------------------------------------------
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT car_id_fk FOREIGN KEY (car_id) REFERENCES cars (id)
);

CREATE TABLE rents
(
    id         BIGSERIAL,
    user_id    BIGSERIAL,
    car_id     BIGSERIAL,
    date_start DATE,
    date_end   DATE,
    ------------------------------------------------------------------
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT car_id_fk FOREIGN KEY (car_id) REFERENCES cars (id)
);