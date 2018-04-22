CREATE DOMAIN car_part_type AS VARCHAR(15)
CHECK ( VALUE IN ('tire', 'ignition system', 'steering wheel', 'car seat', 
'power steering', 'shock absorber', 'airbag', 'dashboard','window',
'battery', 'clutch', 'breaks', 'air suspension', 'door', 'tie rod',
'tie rod joints', 'rack', 'pinion', 'steering shaft', 'steering column'));

CREATE TABLE car_model(
	id SERIAL,
	model VARCHAR(15) UNIQUE
);

CREATE TABLE car (
	chassis_number CHAR(17) PRIMARY KEY, 
	model VARCHAR(15) REFERENCES car_model(model) NOT NULL,
	weight DECIMAL(4, 2) NOT NULL 
);

CREATE TABLE car_part (
	id SERIAL PRIMARY KEY,
	chassis_number CHAR(17) REFERENCES car(chassis_number) NOT NULL,
	weight DECIMAL(4, 2) NOT NULL, 
	type CAR_PART_TYPE NOT NULL 
);

CREATE TABLE pallet (
	id SERIAL PRIMARY KEY,
	type CAR_PART_TYPE NOT NULL,
	capacity INTEGER NOT NULL
);

CREATE TABLE part_belongs_to_pallet (
	part_id INTEGER REFERENCES car_part(id) NOT NULL,
	pallet_id INTEGER REFERENCES pallet(id) NOT NULL
);

CREATE TABLE product (
	id SERIAL PRIMARY KEY,
	product_type VARCHAR(10) CHECK (product_type IN ('package', 'system')) NOT NULL,
	car_parts_type CAR_PART_TYPE,
	from_model VARCHAR(15) REFERENCES car_model(model) NOT NULL,
	numer_of_parts INTEGER
);

INSERT INTO car_model (id, model) VALUES(1, 'Mercedes');

INSERT INTO car (chassisno, model, weight) VALUES('123456789AD', 'Mercedes', 1243.12);
INSERT INTO car (chassisno, model, weight) VALUES('234567GR', 'Mercedes', 67343.12);

INSERT INTO car_part (chassisNo, weight, type) VALUES('123456789AD', 23.30, 'tire');
INSERT INTO car_part (chassisNo, weight, type) VALUES('234567GR', 23.30, 'door');
INSERT INTO car_part (chassisno, weight, type) VALUES('123456789AD', 23.30, 'door');

INSERT INTO pallet (type, capacity) VALUES('door', 4238);
INSERT INTO pallet (type, capacity) VALUES('tire', 4169);
