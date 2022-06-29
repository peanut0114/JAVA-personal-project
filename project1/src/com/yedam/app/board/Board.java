package com.yedam.app.board;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
	private int boardNum;//기본키
	private int productId;//상품번호(fk-products)
	private String productName;//상품이름(product테이블에서 갖고오는 값)
	private String boardMId;//작성자 아이디 (fk-members)
	private String boardPwd;//작성자 비번
	private int boardStar; 	// 평가별점 5점만점
	private String star;	//★로 출력
	private String boardSubject; // 제목
	private String boardContent; // 내용
	private Date boardDate;//작성날짜
	private int boardCategory; // 0:공지게시판, 1:후기게시판

	@Override
	public String toString() {
		String str = "";
		if (boardCategory==1) {
			str = boardNum + ". 제품명 : " + productName
					+" (평점 " + star + ")" + "\t"+boardDate
					+"\n   제목 : " + boardSubject + "\t작성자: "+boardMId
					+"\n   내용 : " + boardContent;
		} else {
			str = boardNum + ". " + boardSubject+"\t"+boardDate
					+ "\n\n  " + boardContent;
		}
		return str;
	}

}
