package com.yedam.app.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
	private int boardNum;
	private int productId;
	private String productName;
	private String boardMId;
	private String boardPwd;
	private int boardStar; // 평가별점 5점만점
	private String star;
	private String boardSubject; // 제목
	private String boardContent; // 내용
	private int boardCategory; // 0:공지게시판, 1:후기게시판

	@Override
	public String toString() {
		String str = "";
		if (boardCategory==1) {
			str = boardNum + ". 제품명 : "+productName+" (평점 " + star + ")"
					+"\n   제목 : " + boardSubject + "\t작성자: "+boardMId
					+"\n   내용 : " + boardContent;
		} else {
			str = boardNum + ". " + boardSubject 
					+ "\\n   내용 : " + boardContent;
		}
		return str;
	}

}
