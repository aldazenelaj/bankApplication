-- DROP SCHEMA usermanagement;

CREATE SCHEMA usermanagement AUTHORIZATION postgres;

-- DROP SEQUENCE usermanagement.role_id_seq;

CREATE SEQUENCE usermanagement.role_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE usermanagement.role_seq;

CREATE SEQUENCE usermanagement.role_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE usermanagement.users_id_seq;

CREATE SEQUENCE usermanagement.users_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE usermanagement.users_seq;

CREATE SEQUENCE usermanagement.users_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- usermanagement."role" definition

-- Drop table

-- DROP TABLE usermanagement."role";

CREATE TABLE usermanagement."role" (
	id serial4 NOT NULL,
	rolename varchar(255) NOT NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);


-- usermanagement.users definition

-- Drop table

-- DROP TABLE usermanagement.users;

CREATE TABLE usermanagement.users (
	id serial4 NOT NULL,
	username varchar(50) NOT NULL,
	"password" varchar(255) NOT NULL,
	creation_time timestamp NULL,
	"name" varchar(50) NULL,
	last_name varchar(50) NULL,
	id_role int4 NULL,
	email varchar NULL,
	CONSTRAINT const UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT user_role_fk FOREIGN KEY (id_role) REFERENCES usermanagement."role"(id)
);
