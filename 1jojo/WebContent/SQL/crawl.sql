select * from tab;

CREATE TABLE search_re_com_list(
	search_com_No NUMBER not null,
	com_qual VARCHAR2(1000),
	com_preex VARCHAR2(1000),
	com_name VARCHAR2(100),
	com_link VARCHAR2(500),
	PRIMARY KEY(search_com_No)
);


drop table search_re_com_list purge;
select * from tab;
select * from search_re_com_list;





select * from tab;

CREATE TABLE search_re_com_qual(
	search_com_No NUMBER not null,
	com_qual VARCHAR2(1000),
	com_preex VARCHAR2(1000),
	com_frequency NUMBER,
	No NUMBER,
	PRIMARY KEY(search_com_No)
);


drop table search_re_com_qual purge;
select * from tab;
select * from search_re_com_qual;

