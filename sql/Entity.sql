
create sequence seq_motor;
CREATE  TABLE motor ( 
	idmotor             varchar(100)  default 'MTR'||NEXTVAL('seq_motor'),
	name                  varchar(40)    ,
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
    idcategory             varchar(100) ,
    CONSTRAINT fk_specifite_category FOREIGN KEY ( idcategory ) REFERENCES category( idcategory ) ,
	CONSTRAINT pk_specifite PRIMARY KEY ( idspecifite )
 );
INSERT INTO category (idcategory,name)
VALUES
    ('CTG1','disk'),('CTG1','drum'),('CTG1','Hydraulic'),('CTG1','Electromagnetic'),('CTG1','Carbon Ceramic'),('CTG1','Regenerative'),
    ('CTG2','Alloy Wheels'),('CTG2','Steel Wheels'),('CTG2','Spoke Wheels'),('CTG2','Chrome Wheels'),('CTG2','Forged Wheels')
    ('CTG2','Black Wheels'), ('CTG2','Multi-Spoke Wheels'),('CTG2','Split-Spoke Wheels'),('CTG2','Off-Road Wheels'),,('CTG2','Wire Wheels');


create sequence seq_country;
CREATE  TABLE country ( 
	idcountry             varchar(100)  default 'NTL'||NEXTVAL('seq_country'),
	name                  varchar(40)    ,
	CONSTRAINT pk_country PRIMARY KEY ( idcountry )
 );
 INSERT INTO motor (name)
VALUES
    ('United States'),('Brazil'),('United Kingdom'),('India'),('Australia'),('Australia'),('Saudi Arabia'),('Japan'),('Madagascar'),('france'),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),
    ('');