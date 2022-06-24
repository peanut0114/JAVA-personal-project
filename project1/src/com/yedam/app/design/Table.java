package com.yedam.app.design;

public class Table {
/*
	<TABLE>
	
	* 상품 테이블
	
	CREATE TABLE products (
	product_id NUMBER constraint product_id_pk primary key,
	product_name VARCHAR2(100) constraint product_name_nn NOT NULL,
	product_price number constraint product_price_nn NOT NULL,
	product_explain VARCHAR2(1000));

	insert into products values (500,	'피넛쿠키',	3200,	'고소한 땅콩을 갈아넣어 만든 쿠키');
	insert into products values (501,	'화이트마카다미아쿠키',	3800,	'은경언니가 추천하는 최애쿠키 츄라이츄라이');

    * 주문 테이블
    
	CREATE table orders(
    deal_date DATE DEFAULT sysdate,
    product_id NUMBER CONSTRAINT pro_fk_take_out
                      REFERENCES products(product_id),
    deal_amount NUMBER,
    orderer_id VARCHAR2(100) CONSTRAINT orderer_id_fk
                             REFERENCES members(member_id),
    shipment_date DATE,
    condition number(1) default 0); --0이면 준비중, 1이면 배송완료
     
     
    * 회원 테이블
    
	CREATE TABLE members (
	member_id VARCHAR2(20) constraint member_id_pk primary key,
	member_pwd varchar2(30) constraint member_pwd_nn NOT NULL,
	member_name varchar2(100) constraint member_name_nn NOT NULL,
	member_birth date,
	member_phone varchar2(20),
	member_addr varchar2(500),
	member_role number(1) default 1, --0관리자, 1회원
	member_order_num number default 0);

	INSERT INTO members(member_id, member_pwd, member_name, member_role) 
	VALUES ('admin','admin','manager',0); --관리자 정보 입력
	
	
	* 게시판 테이블
	
	CREATE TABLE board(
	board_num NUMBER constraint bord_num_pk primary key,
	board_product number constraint b_product_fk 
						REFERENCES products(product_id),
	board_m_id VARCHAR2(20) CONSTRAINT board_m_id_fk
                             REFERENCES members(member_id),
    board_pwd VARCHAR2(20),
	board_star NUMBER(1),	--평가별점(5점만점)
	board_subject VARCHAR(200), 
	board_content VARCHAR2(1000), 
	board_category NUMBER(1)); --0 공지, 1후기
	
	
	* 댓글 테이블
	
	CREATE TABLE comments(
	comment_b_num NUMBER CONSTRAINT comm_bnum_fk
                        REFERENCES board(board_num), --게시판 번호 참조
	comment_m_id VARCHAR2(20) CONSTRAINT comm_mid_fk
                        REFERENCES members(member_id), --회원 아이디 참조
	comment_content VARCHAR2(500),
	comment_date DATE default sysdate);
	
	
	<SEQUENCE>
	CREATE SEQUENCE product_seq 
	increment by 1
	START WITH 1
	nocache
	nocycle;
	
	CREATE SEQUENCE board_seq 
	increment by 1
	START WITH 1
	nocache
	nocycle;

 */
}
