package com.yedam.app.design;

public class Q {
/*
 <수제쿠키 전문점 - 상품 리뷰 게시판>
1. 전체 상품 2. 공지사항 3. 마이페이지(로그인) 4.판매자 정보(단순출력)  9. 종료
 (리뷰- 아이디뜨게)

1-1 상품목록 프린트 (번호+이름+가격) -> 9.홈
1-2 상품선택시 상세설명(1.주문 2.후기 3.뒤로가기 9.홈) 
	1-2-1. 주문 : 개수(나에게 주문: 시간남으면  타인 주소, 폰번호, 이름 입력받기)
	1-2-2. 후기 출력 - 댓글 : 로그인한 사람 대상
		1. 리뷰작성(회원,관리자) 2. 리뷰등록(회원, 구매이력확인) 3. 뒤로가기

2. 공지게시판 : 관리자만 작성가능
	
3-1. 1.로그인 (로그인 상태일시 넘어감)
        2. 회원가입(관리자0, 디폴트 회원1) : 아이디, 비번, 이름, 생년월일, 폰번호, 주소

3-2. 관리자 : 1.상품관리(상품별 입출고내역) 2. 회원관리(주문조회, 회원정보 삭제)

3-3. 회원 : 1. 회원정보조회 2. 회원정보수정 3. 주문내역확인	 9.홈
	3-2-1. 이름, 아이디, 주소, 폰번호, 생일 쿠폰 유무 출력 (생일과 5일 차이동안 생성)
	3-2-2. 주소, 폰번호 수정
	3-2-3. 상품이름, 구매량, 주소, 폰번호, 배송예정일 출력

4. 판매자 정보 : 회사위치 상호명.. 등 
	1. 뒤로가기 9. 종료

5. 시간남으면 쿠키 이야기 익명게시판 만들기

TALBLE
상품정보product (num, name, price, content)
입고량receiving_goods(num, amount, orderer, date)
출고량take_out_goods(num, amount, orderer, date, shipping_schedule(발송예정일))
회원member(id, pwd, name, ordernum) 0:관리자, 1:회원
게시판board(id, subject, content, category) 0:공지게시판, 1:후기게시판
댓글comment(id, boardnum, content)

PACKAGE
app
	main
app.common
	DAO, Manager, LoginControl
app.deal
	dealInfo, productStockManagement, RdceivingDAO, TakeOutDAO
app.members
	member, memberDAO
app.product
	product, prodcutDAO, productInfoManagement
app.board
	board, boardDAO, boardManagement
app.comment
	comment, commentDAO, commentManagement

 
 --------------------------
 문제 해결 내역
 
 1. 부모 메소드 A를 사용할때 A 안에서 불러오는 또다른 메소드 B만 자식 클래스에서 오버라이딩해서 쓸 수 없다!
 ->> 부모클래스에선 자식클래스의 메소드에 접근 불가 
 
 
 */
}
