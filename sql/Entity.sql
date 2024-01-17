
create sequence seq_motor;
CREATE  TABLE motor ( 
	idmotor             varchar(100)  default 'MTR'||NEXTVAL('seq_motor'),
	name                  varchar(40)    ,
    state int default  1,
	CONSTRAINT pk_motor PRIMARY KEY ( idmotor )
 );
INSERT INTO motor (name)
VALUES
    ('Essence'),
    ('Diesel'),
    ('Electric');

create sequence seq_category;
CREATE  TABLE category ( 
	idcategory             varchar(100)  default 'CTG'||NEXTVAL('seq_category'),
	name                  varchar(40)    ,
    state int default  1,
	CONSTRAINT pk_category PRIMARY KEY ( idcategory )
 );
INSERT INTO category (name)
VALUES
    ('brake'),
    ('rim');
 create sequence seq_specifite;
CREATE  TABLE specifite ( 
	idspecifite             varchar(100)  default 'SPF'||NEXTVAL('seq_specifite'),
	name                  varchar(100)    ,
    state int default  1,
    idcategory             varchar(100) ,
    CONSTRAINT fk_specifite_category FOREIGN KEY ( idcategory ) REFERENCES category( idcategory ) ,
	CONSTRAINT pk_specifite PRIMARY KEY ( idspecifite )
 );
INSERT INTO specifite (idcategory,name)
VALUES
    ('CTG1','disk'),
    ('CTG1','drum'),
    ('CTG1','Hydraulic'),
    ('CTG1','Electromagnetic'),
    ('CTG1','Carbon Ceramic'),
    ('CTG1','Regenerative'),
    ('CTG2','Alloy Wheels'),
    ('CTG2','Steel Wheels'),
    ('CTG2','Spoke Wheels'),
    ('CTG2','Chrome Wheels'),
    ('CTG2','Forged Wheels'),
    ('CTG2','Black Wheels'),
     ('CTG2','Multi-Spoke Wheels'),
     ('CTG2','Split-Spoke Wheels'),
     ('CTG2','Off-Road Wheels'),
     ('CTG2','Wire Wheels');


create sequence seq_country;
CREATE  TABLE country ( 
	idcountry             varchar(100)  default 'CTR'||NEXTVAL('seq_country'),
	name                  varchar(40)    ,
     state int default  1,
	CONSTRAINT pk_country PRIMARY KEY ( idcountry )
 );
 INSERT INTO country (name)
VALUES
    ('United States'),('Brazil'),('United Kingdom'),('India'),('Australia'),('Saudi Arabia'),('Japan'),('Madagascar'),('france');

create sequence seq_devise;
CREATE  TABLE devise ( 
	iddevise             varchar(100)  default 'DEV'||NEXTVAL('seq_devise'),
	name                  varchar(40)    ,
    state int default  1,
    idcountry VARCHAR(100),
	CONSTRAINT pk_devise PRIMARY KEY ( iddevise ),
    CONSTRAINT fk_devise_country FOREIGN KEY ( idcountry ) REFERENCES country( idcountry ) 

 );

 insert INTO devise (name,idcountry) values ('ariary','CTR9'),('dollar','CTR1'),('BRL','CTR2'),('GBP','CTR3'),('rupee','CTR4'),('AUD','CTR5'),('SAR','CTR7'),('yen','CTR8'),('euro','CTR10');
 create sequence seq_type;
CREATE  TABLE type ( 
	idtype             varchar(100)  default 'TPE'||NEXTVAL('seq_type'),
	name                  varchar(40)    ,
     state int default  1,
	CONSTRAINT pk_type PRIMARY KEY ( idtype )
 );
 INSERT INTO type (name) VALUES 
  ('sport car'),
  ('sedan'),
  ('SUV'),
  ('truck'),
  ('convertible'),
  ('electric car');
   create sequence seq_make;
CREATE  TABLE make ( 
	idmake             varchar(100)  default 'MKE'||NEXTVAL('seq_make'),
     state int default  1,
	name                  varchar(40)    ,
	CONSTRAINT pk_make PRIMARY KEY ( idmake )
 );
 INSERT INTO make (name) VALUES
    ('Toyota'),
    ('Ford'),
    ('Honda'),
    ('Chevrolet'),
    ('Mercedes-Benz'),
    ('BMW'),
    ('Audi'),
    ('Nissan'),
    ('Tesla'),
    ('Jeep'),
    ('Volkswagen'),
    ('Hyundai'),
    ('Porsche'),
    ('Lexus'),
    ('Maserati'),
    ('Subaru'),
    ('Volvo'),
    ('Ram'),
    ('Mazda'),
    ('Jaguar'),
     ('Citroën'),
  (' Range Rover');
 create sequence seq_model;
CREATE  TABLE model ( 
	idmodel             varchar(100)  default 'MDL'||NEXTVAL('seq_model'),
	name                  varchar(40)    ,
    state int default  1,
    release_date date ,
    places_Number int,
    doors_number int,
    idtype VARCHAR(100),
    idmake VARCHAR(100),

	CONSTRAINT pk_model PRIMARY KEY ( idmodel ),
    CONSTRAINT fk_type FOREIGN KEY ( idtype ) REFERENCES type( idtype ) ,
    CONSTRAINT fk_make FOREIGN KEY ( idmake ) REFERENCES make( idmake ) 
 );
INSERT INTO model (name, release_date, places_number, doors_number, idtype, idmake)
VALUES
    ('Citroën C3', '2022-01-01', 5, 4, 'TPE1', 'MKE21'),
    ('Citroën C4', '2021-11-15', 5, 4, 'TPE2', 'MKE21'),
    ('Citroën C5 Aircross', '2023-03-20', 5, 4, 'TPE3', 'MKE21'),
    ('Ford Mustang', '2022-02-10', 4, 2, 'TPE1', 'MKE2'),
    ('Toyota Camry', '2022-05-25', 5, 4, 'TPE2', 'MKE1'),
    ('Jeep Grand Cherokee', '2021-12-12', 5, 4, 'TPE3', 'MKE10'),
    ('Chevrolet Silverado', '2023-01-08', 5, 4, 'TPE4', 'MKE4'),
    ('Mazda MX-5', '2022-04-18', 2, 2, 'TPE5', 'MKE19'),
    ('Tesla Model S', '2022-03-05', 5, 4, 'TPE6', 'MKE9'),
    ('Range Rover Evoque', '2023-06-15', 5, 4, 'TPE3', 'MKE22'),
    ('Honda Accord', '2022-07-20', 5, 4, 'TPE2', 'MKE3'),
    ('BMW 3 Series', '2021-09-30', 5, 4, 'TPE1', 'MKE6'),
    ('Audi Q5', '2023-04-12', 5, 4, 'TPE3', 'MKE7'),
    ('Nissan Frontier', '2022-08-08', 5, 4, 'TPE4', 'MKE8'),
    ('Porsche 911', '2022-11-28', 2, 2, 'TPE1', 'MKE13'),
    ('Ford Explorer', '2021-10-15', 7, 4, 'TPE3', 'MKE2'),
    ('Volkswagen Golf', '2023-02-22', 5, 4, 'TPE2', 'MKE11'),
    ('Hyundai Kona Electric', '2022-12-05', 5, 4, 'TPE6', 'MKE12'),
    ('Jeep Wrangler', '2022-06-10', 4, 2, 'TPE3', 'MKE10'),
    ('Mercedes-Benz C-Class', '2022-09-18', 5, 4, 'TPE2', 'MKE5'),
    ('Ram 1500', '2023-03-01', 5, 4, 'TPE4', 'MKE18');


   create sequence seq_maintains;
CREATE  TABLE maintains ( 
	idmaintains             varchar(100)  default 'MTN'||NEXTVAL('seq_maintains'),
	name                  varchar(100)    ,
    state int default  1,
	CONSTRAINT pk_maintains PRIMARY KEY ( idmaintains )
 );
 INSERT INTO maintains (name) VALUES
    ('Oil Change'),
    ('Brake Inspection'),
    ('Battery Replacement'),
    ('Spark Plug Replacement'),
    ('Wheel Alignment'),
    ('Air Filter Replacement'),
    ('Transmission Fluid Change'),
    ('Coolant Flush'),
    ('Windshield Wiper Replacement'),
    ('Exhaust System Check'),
    ('Fuel System Cleaning'),
    ('Suspension Inspection'),
    ('Engine Diagnostic'),
    ('Electrical System Check'),
    ('Power Steering Fluid Flush'),
    ('Timing Belt Replacement'),
    ('Fuel Injector Cleaning');




/** 
 * Paste one or more documents here
 */
{
  "_id": {
    "$oid": "65a821866c93603c7a354a84"
  },
  "make": {
    "id": "1",
    "name": "1"
  },
  "caracteristic": [
    {
      "id": "1",
      "name": "1"
    },
    {
      "id": "1",
      "name": "1"
    }
  ],
  "year": "2020-13-12",
  "kilometre": 90,
  "consommation": 20,
  "maintenance": [
    {
      "id": "1",
      "name": "1"
    },
    {
      "id": "1",
      "name": "1"
    }
  ],
  "localisation": "tana",
  "stock": 1,
  "vendeur": {
    "id": "1",
    "name": "aa",
    "proprietaire": 1
  },
  "commission": 20000,
  "picture": "qsnxiochoh",
  "motor": {
    "id": "1",
    "name": "1"
  },
  "note": 4
}
