package com.yedam.app.design;

public class Table {
/*
	<TABLE>
	
	* 상품 테이블
	
	CREATE TABLE products (
	product_id NUMBER constraint product_id_pk primary key,
	product_name VARCHAR2(100) constraint product_name_nn NOT NULL,
	product_price number constraint product_price_nn NOT NULL,
	product_explain VARCHAR2(1000);


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
	member_pwd varchar2(100) constraint member_pwd_nn NOT NULL,
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
	board_star NUMBER(1),	--평가별점(5점만점)
	board_subject VARCHAR(200), 
	board_content VARCHAR2(1000), 
	board_category NUMBER(1));
	
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
