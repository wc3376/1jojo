create table useraccount (
id varchar2(30) primary key,
pass varchar2(30)
);


create sequence useraccount
start with 1
increment by 1
nocache;


insert into USERACCOUNT values ("sss", "1111");

select * from tab;
select * from USERACCOUNT;
drop table useraccount purge;
