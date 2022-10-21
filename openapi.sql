select nextval("SEQ_OPENAPI"); 

select * from "UserVo" ;

select SEQ_OPENAPI;

select seq, token , username, password, authorities from "UserVo" where token = 'e4aee890-bbca-40aa-8c2f-cfd2616d1282';

select * from "billdata";

SELECT column_name, data_type, character_maximum_length
FROM INFORMATION_SCHEMA.COLUMNS
WHERE table_name = 'billdata'
order by ordinal_position
;

select 
"seq"
,"aptcd"
,"dongho"
,"billym"
,"custnm"
,"distspace"
,"paidmethod"
,"monthfee"
,"bfoverfee"
,"bfoverduefee"
,"bfduedtfee"
from  "billdata"
where "aptcd" = '12345' and "billym" = 202210
;



insert into "billdata" values (1, '12345', '10010101', 202210, '이지스엔터프라이즈', 1000, '003', 10000, 0, 0, 0);
insert into "billdata" values (2, '12345', '10010102', 202210, '이지스엔터프라이즈', 1000, '003', 11000, 0, 0, 0);
insert into "billdata" values (3, '12345', '10010103', 202210, '이지스엔터프라이즈', 1000, '003', 12000, 0, 0, 0);
insert into "billdata" values (4, '12345', '10010104', 202210, '이지스엔터프라이즈', 1000, '003', 13000, 0, 0, 0);
insert into "billdata" values (5, '12345', '10010105', 202210, '이지스엔터프라이즈', 1000, '003', 14000, 0, 0, 0);
insert into "billdata" values (6, '12345', '10010106', 202210, '이지스엔터프라이즈', 1000, '003', 15000, 0, 0, 0);
insert into "billdata" values (7, '12345', '10010107', 202210, '이지스엔터프라이즈', 1000, '003', 16000, 0, 0, 0);
insert into "billdata" values (8, '12345', '10010108', 202210, '이지스엔터프라이즈', 1000, '003', 17000, 0, 0, 0);
insert into "billdata" values (9, '12345', '10010109', 202210, '이지스엔터프라이즈', 1000, '003', 18000, 0, 0, 0);
commit;