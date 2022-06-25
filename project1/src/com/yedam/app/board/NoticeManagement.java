package com.yedam.app.board;

import java.util.List;

import com.yedam.app.common.Management;

public class NoticeManagement extends Management {

	public NoticeManagement() {
		//1. 권한 확인 (0관리자 1회원 2비회원)
		
		//2. 게시판 목록 출력
		List<Board> list = bDAO.selectAll(0);
		for(Board board : list) {
			System.out.println(board);
		}
		
		while(true) {
			menuPrint();
			int menu = menuSelect();
		}
		//3. 관리자 (1.게시글선택 2.게시글수정 9.홈)
		
		//4. 회원, 비회원 (1.게시글선택 9.홈)
	}

	@Override
	protected void menuPrint() {
		if(selectRole()==0) {
			System.out.println("--------------------------");
			System.out.println(" 1.게시글선택 2.게시글수정 9.홈");
			System.out.println("--------------------------");
		}else {
			System.out.println("----------------");
			System.out.println(" 1.게시글선택 9.홈");
			System.out.println("----------------");
		}
	}
	
	@Override
	protected int menuSelect() {
		System.out.print("선택 > ");
		int menuNo=0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
		if(selectRole()!=0 && menuNo==2) { //관리자외 2.수정 선택불가
			menuNo=3;	
		}
		return menuNo;
	}
}
