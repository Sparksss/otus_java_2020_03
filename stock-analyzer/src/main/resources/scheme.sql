CREATE TABLE IF NOT EXISTS companies
(
	id serial
		constraint companies_pk
			primary key,
	name varchar(200) not null,
	symbol varchar(10) not null unique,
	description text
);

CREATE TABLE IF NOT EXISTS stocks_price
(
	id serial
		constraint stocks_price_pk
			primary key,
	company_id int references companies (id),
	date_price date not null,
	open double,
	close double,
	high double,
	low double,
	volume int
);
