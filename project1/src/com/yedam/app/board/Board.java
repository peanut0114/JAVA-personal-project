package com.yedam.app.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
	private int boardNum;
	private int boardStar; // 평가별점 5점만점
	private String boardSubject; // 제목
	private String boardContent; // 내용
	private int boardCategory; // 0:공지게시판, 1:후기게시판

	@Override
	public String toString() {
		String str = "";
		if (boardCategory==1) {
			str = boardNum + ". " + boardSubject 
				+ " 평점 : " + boardStar + "/5\n" 
				+ "내용 : " + boardContent;
		} else {
			str = boardNum + ". " + boardSubject 
					+ "내용 : " + boardContent;
		}
		return str;
	}

}
