create table USER
(
	ID INTEGER  auto_increment not null ,
	ACCOUNTID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMTCREATE BIGINT,
	GMTMODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);