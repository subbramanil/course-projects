create table Airport 
(
Airport_code char(10) not null, 
Name varchar(45), 
City varchar(45), 
State varchar(45), 
primary key(Airport_code)
);

create table flight
(
flight_number varchar(10) not null,
airline varchar(45),
weekdays varchar(45),
departure_airport_code varchar(45),
scheduled_departure_time time,
arrival_airport_code varchar(45),
scheduled_arrival_time time,:
primary key(flight_number)
);


create table airplane_type(
Airplane_type_name varchar(45) not null,
Max_seats int,
Company varchar(45),
primary key(airplane_type_name)
);


create table airplane(
Airplane_id int not null,
Total_number_of_seats int,
Airplane_type varchar(45),
primary key(airplane_id),
foreign key(airplane_type) references airplane_type(airplane_type_name)
);

create table fare(
Flight_number varchar(45) not null,
Fare_code varchar(45) not null,
Amount decimal(6,2),
Restrictions varchar(45),
primary key(flight_number, fare_code, Restrictions),
foreign key(flight_number) references flight(flight_number)
);

create table can_land
(
Airplane_type_name varchar(45) not null,
Airport_code char(10) not null,
primary key(Airplane_type_name, Airport_code),
foreign key(Airport_code) references Airport(Airport_code)
);

create table flight_instance(
Flight_number varchar(10) not null,
travelDate DATE not null,
Number_of_available_seats int,
Airplane_id int not null,
Departure_time time,
Arrival_time time,
primary key(Flight_number,travelDate),
foreign key(airplane_id) references airplane(airplane_id)
);

create table seat_reservation
(
Flight_number varchar(10) not null,
travelDate date not null,
Seat_number int not null,
Customer_name varchar(45),
Customer_phone decimal(11),
primary key(Flight_number,travelDate,Seat_number),
foreign key(flight_number) references flight(flight_number)
);


Load data infile 'AIRPORT.csv' into table airlinedb.airport FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

Load data infile 'AIRPLANE_TYPE.csv' into table airlinedb.airplane_type FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' IGNORE 1 ROWS;

Load data infile 'AIRPLANE.csv' into table airlinedb.airplane FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;

Load data infile 'CAN_LAND.csv' into table airlinedb.can_land FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 0 ROWS;

Load data infile 'fares.csv' into table airlinedb.fare FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 0 ROWS;

Load data infile 'flights.csv' into table airlinedb.flight FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 0 ROWS;

Load data infile 'flight_instance.csv' into table airlinedb.flight_instance FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 0 ROWS;


