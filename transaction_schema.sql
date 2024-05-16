-- DROP SCHEMA "transaction";

CREATE SCHEMA "transaction" AUTHORIZATION postgres;

-- DROP SEQUENCE "transaction".bank_account_application_id_seq;

CREATE SEQUENCE "transaction".bank_account_application_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE "transaction".bank_account_id_seq;

CREATE SEQUENCE "transaction".bank_account_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE "transaction".bank_account_type_id_seq;

CREATE SEQUENCE "transaction".bank_account_type_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE "transaction".bank_id_seq;

CREATE SEQUENCE "transaction".bank_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE "transaction".card_application_id_seq;

CREATE SEQUENCE "transaction".card_application_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE "transaction".card_type_id_seq;

CREATE SEQUENCE "transaction".card_type_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE "transaction".country_id_seq;

CREATE SEQUENCE "transaction".country_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE "transaction".currency_id_seq;

CREATE SEQUENCE "transaction".currency_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- "transaction".bank_account_type definition

-- Drop table

-- DROP TABLE "transaction".bank_account_type;

CREATE TABLE "transaction".bank_account_type (
	id serial4 NOT NULL,
	"type" varchar NULL,
	CONSTRAINT bank_account_type_pkey PRIMARY KEY (id)
);


-- "transaction".card_type definition

-- Drop table

-- DROP TABLE "transaction".card_type;

CREATE TABLE "transaction".card_type (
	id serial4 NOT NULL,
	"type" varchar NULL,
	CONSTRAINT card_type_pkey PRIMARY KEY (id)
);


-- "transaction".country definition

-- Drop table

-- DROP TABLE "transaction".country;

CREATE TABLE "transaction".country (
	id bigserial NOT NULL,
	country_code varchar NULL,
	country varchar NULL,
	CONSTRAINT country_pkey PRIMARY KEY (id)
);


-- "transaction".currency definition

-- Drop table

-- DROP TABLE "transaction".currency;

CREATE TABLE "transaction".currency (
	id serial4 NOT NULL,
	currency varchar NULL,
	CONSTRAINT currency_pkey PRIMARY KEY (id)
);


-- "transaction".bank definition

-- Drop table

-- DROP TABLE "transaction".bank;

CREATE TABLE "transaction".bank (
	id bigserial NOT NULL,
	bank_code varchar NULL,
	country_id int4 NULL,
	CONSTRAINT bank_pkey PRIMARY KEY (id),
	CONSTRAINT country_id_fk FOREIGN KEY (country_id) REFERENCES "transaction".country(id)
);


-- "transaction".bank_account definition

-- Drop table

-- DROP TABLE "transaction".bank_account;

CREATE TABLE "transaction".bank_account (
	id serial4 NOT NULL,
	user_id int4 NULL,
	account_type_id int4 NULL,
	currency_id int4 NULL,
	iban varchar NULL,
	interes float8 NULL,
	balance float8 NULL,
	status varchar NULL,
	create_date timestamp NULL,
	bank_id int4 NULL,
	CONSTRAINT bank_account_iban_key UNIQUE (iban),
	CONSTRAINT bank_account_pkey PRIMARY KEY (id),
	CONSTRAINT bank_account_type_fk FOREIGN KEY (account_type_id) REFERENCES "transaction".bank_account_type(id),
	CONSTRAINT bank_currency_type_fk FOREIGN KEY (currency_id) REFERENCES "transaction".currency(id),
	CONSTRAINT bank_id_fk FOREIGN KEY (bank_id) REFERENCES "transaction".bank(id)
);


-- "transaction".bank_account_application definition

-- Drop table

-- DROP TABLE "transaction".bank_account_application;

CREATE TABLE "transaction".bank_account_application (
	id serial4 NOT NULL,
	user_id int4 NULL,
	account_type_id int4 NULL,
	currency_id int4 NULL,
	status varchar NULL,
	create_date timestamp NULL,
	bank_id int4 NULL,
	CONSTRAINT bank_account_application_pkey PRIMARY KEY (id),
	CONSTRAINT account_type_fk FOREIGN KEY (account_type_id) REFERENCES "transaction".bank_account_type(id),
	CONSTRAINT bank_id_fk FOREIGN KEY (bank_id) REFERENCES "transaction".bank(id),
	CONSTRAINT currency_type_fk FOREIGN KEY (currency_id) REFERENCES "transaction".currency(id)
);


-- "transaction".card_application definition

-- Drop table

-- DROP TABLE "transaction".card_application;

CREATE TABLE "transaction".card_application (
	id serial4 NOT NULL,
	user_id int4 NULL,
	status varchar NULL,
	create_date timestamp NULL,
	bank_account_id int4 NULL,
	card_type_id int4 NULL,
	monthly_salary float8 NULL,
	CONSTRAINT card_application_pkey PRIMARY KEY (id),
	CONSTRAINT bankaccount_id_fk FOREIGN KEY (bank_account_id) REFERENCES "transaction".bank_account(id),
	CONSTRAINT card_type_id_fk FOREIGN KEY (card_type_id) REFERENCES "transaction".card_type(id)
);
