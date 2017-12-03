select * from tab;

CREATE TABLE search_list(
	search_com_No VARCHAR2(12),
	com_qual VARCHAR2(1000),
	com_preex VARCHAR2(1000),
	com_name VARCHAR2(100),
	com_link VARCHAR2(500),
	PRIMARY KEY(com_link)
);

create sequence search_list_seq
start with 1
increment by 1
nocache;

drop table search_list purge;
select * from tab;
select * from search_list;





select * from tab;

CREATE TABLE search_qual(
	search_com_No VARCHAR2(12),
	No NUMBER,
	com_qual VARCHAR2(1000),
	com_preex VARCHAR2(1000),
	com_frequency NUMBER,
	PRIMARY KEY(search_com_No)
);

drop table search_qual purge;
select * from tab;
select * from search_qual;

