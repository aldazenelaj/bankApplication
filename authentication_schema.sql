-- DROP SCHEMA authentication;

CREATE SCHEMA authentication AUTHORIZATION postgres;

-- DROP SEQUENCE authentication.role_id_seq;

CREATE SEQUENCE authentication.role_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE authentication.users_id_seq;

CREATE SEQUENCE authentication.users_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- authentication."role" definition

-- Drop table

-- DROP TABLE authentication."role";

CREATE TABLE authentication."role" (
	id bigserial NOT NULL,
	rolename varchar(255) NOT NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);


-- authentication.users definition

-- Drop table

-- DROP TABLE authentication.users;

CREATE TABLE authentication.users (
	id bigserial NOT NULL,
	username varchar(50) NOT NULL,
	"password" varchar(255) NOT NULL,
	creation_time timestamp NULL,
	"name" varchar(50) NULL,
	last_name varchar(50) NULL,
	id_role int8 NULL,
	email varchar NULL,
	CONSTRAINT const UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT user_role_fk FOREIGN KEY (id_role) REFERENCES authentication."role"(id)
);
