-- 계정
CREATE TABLE users(
	id VARCHAR2(15) CONSTRAINT login_pk PRIMARY KEY,
	pw VARCHAR2(15),
	name VARCHAR2(15),
	phone VARCHAR2(20)
);

-- 현재 접속중인 유저
CREATE TABLE login_who(
	id VARCHAR2(15),
	pw VARCHAR2(15)
);

-- 게시판
CREATE TABLE boards(
	title VARCHAR2(100),
	content VARCHAR2(1000),
	time VARCHAR2(200),
	id VARCHAR2(15)
);