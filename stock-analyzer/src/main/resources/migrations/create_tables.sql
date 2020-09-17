create table companies
(
	id serial
		constraint companies_pk
			primary key,
	name varchar(200) not null,
	description text,
	establishment_date date
);

create table stocks_price
(
	id serial
		constraint stocks_price_pk
			primary key,
	company_id int references companies (id),
	date date not null,
	open_price int,
	close_price int
);
