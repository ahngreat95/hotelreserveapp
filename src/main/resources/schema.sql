DROP TABLE IF EXISTS hotel_room CASCADE;
DROP TABLE IF EXISTS reservable_room CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS hotel_room(
    room_id SERIAL NOT NULL,
    room_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (room_id)
);

CREATE TABLE IF NOT EXISTS reservable_room (
  reserved_date DATE NOT NULL,
  room_id INT4 NOT NULL,
  PRIMARY KEY (reserved_date, room_id)
);

CREATE TABLE IF NOT EXISTS reservation(
    reservation_id SERIAL NOT NULL,
    end_time TIME NOT NULL,
    start_time TIME NOT NULL,
    reserved_date DATE NOT NULL,
    room_id INT4 NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (reservation_id)
);

CREATE TABLE IF NOT EXISTS users(
    user_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);
ALTER TABLE reservable_room DROP CONSTRAINT IF EXISTS FK_ROOM_ID;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS FK_RESERVED_DATE_ROOM_ID;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS FK_USER_ID;


ALTER TABLE reservable_room ADD CONSTRAINT FK_ROOM_ID FOREIGN KEY (room_id) REFERENCES hotel_room;
ALTER TABLE reservation ADD CONSTRAINT FK_RESERVED_DATE_ROOM_ID FOREIGN KEY (reserved_date, room_id) REFERENCES reservable_room;
ALTER TABLE reservation ADD CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES users;

INSERT INTO hotel_room (room_name) VALUES ('101');
INSERT INTO hotel_room (room_name) VALUES ('102');
INSERT INTO hotel_room (room_name) VALUES ('103');
INSERT INTO hotel_room (room_name) VALUES ('104');
INSERT INTO hotel_room (room_name) VALUES ('105');
INSERT INTO hotel_room (room_name) VALUES ('106');
INSERT INTO hotel_room (room_name) VALUES ('107');
INSERT INTO hotel_room (room_name) VALUES ('108');
INSERT INTO hotel_room (room_name) VALUES ('109');
INSERT INTO hotel_room (room_name) VALUES ('110');
INSERT INTO hotel_room (room_name) VALUES ('111');


INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten0', 'Va', 'Li', 'ADMIN', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten1', 'La', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten2', 'Pa', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten3', 'Da', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten4', 'Ba', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten5', 'Aa', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten6', 'Wa', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten7', 'Ya', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten8', 'Ea', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten9', 'Oa', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten10', 'Qa', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten11', 'Na', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten12', 'Ma', 'Li', 'USER', '1234');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten13', 'Ma', 'Li', 'USER', '$2b$10$uNJ0ZUdq4eCYxzZuXoX4Z.eo6Cw9vNzVJ7dEA1f9lWtaBRYZYgLy2');
INSERT INTO users (user_id, first_name, last_name, role_name, password) VALUES ('rakuten14', 'Ba', 'Li', 'USER', '$2a$10$YWKeCzxnOg81AgCcLUmcb.YlCD55RNKsVXPXCnGtY9RTQkEZ4RSTu');


DROP FUNCTION IF EXISTS REGISTER_RESERVABLE_ROOMS();
CREATE OR REPLACE FUNCTION REGISTER_RESERVABLE_ROOMS()
    RETURNS INT AS $$
DECLARE
    total INT;
    i INT4;
    id INT4;
BEGIN
    total := 0;
    FOR id in SELECT room_id
              FROM hotel_room LOOP
        FOR I IN 0..77 LOOP
            INSERT INTO reservable_room (reserved_date, room_id)
            VALUES (CURRENT_DATE + i - 7, id);
        end loop;
        total := total + i;
    end loop;
    RETURN total;
end;
$$ LANGUAGE plpgsql
    ;
SELECT REGISTER_RESERVABLE_ROOMS();
COMMIT;
