package com.yedam.app.board;

import java.util.List;

import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;

public class NoticeManagement extends Management {

	public NoticeManagement() {

		// 1. 게시판 목록 출력
		List<Board> list = bDAO.selectAll(0);
		for (Board board : list) {
			System.out.println(board);
		}

		while (true) {
			menuPrint();
			// 2. 관리자 (1.게시글선택 2.공지등록 3.공지수정 9.홈)
			// 3. 회원, 비회원 (1.게시글선택 9.홈)
			int menu = menuSelect();

			if (menu == 1) {
				// 게시글선택 - selectOne
				boardPrint();
			} else if (menu == 2) {
				// 공지등록
				insertNotice();
			} else if (menu == 3) {
				// 공지수정
				updateBoard();
			} else if (menu == 9) {
				// 홈
				break;
			} else {
				showInputError();
			}
		}
	}

	// 게시글 수정
	protected void updateBoard() {
		// 게시글 번호 선택
		System.out.print("게시글 번호 ");
		int num = menuSelect();
		// 등록된 글인지 확인
		if (!isBoardNumExist(num))
			return;
		Board board = bDAO.selectOne(num);

		// 작성자인지 확인
		if (board.getBoardMId() != LoginControl.getLoginInof().getMemberId())
			return;
		// 수정
		System.out.println("제목: " + board.getBoardSubject());
		System.out.println("제목 수정 (수정 하지 않을 경우 0 입력) > ");
		String str = sc.nextLine();
		if (str != "0") {
			board.setBoardSubject(str);
		}
		System.out.println("내용: " + board.getBoardContent());
		System.out.println("내용 수정 (수정 하지 않을 경우 0 입력) > ");
		str = sc.nextLine();
		if (str != "0") {
			board.setBoardContent(str);
		}
		bDAO.insert(board);
	}

	private void insertNotice() {
		Board notice = new Board();
		notice.setBoardMId(LoginControl.getLoginInof().getMemberId());
		notice.setBoardPwd(LoginControl.getLoginInof().getMemberPwd());
		System.out.println("제목 > ");
		notice.setBoardSubject(sc.nextLine());
		System.out.println("내용 > ");
		notice.setBoardContent(sc.nextLine());
		notice.setBoardCategory(0); // 0공지 1후기

		bDAO.insert(notice);
	}

	@Override
	protected void menuPrint() {
		if (selectRole() == 0) {
			System.out.println("--------------------------");
			System.out.println(" 1.게시글선택 2.게시글수정 9.홈");
			System.out.println("--------------------------");
		} else {
			System.out.println("----------------");
			System.out.println(" 1.게시글선택 9.홈");
			System.out.println("----------------");
		}
	}

	private void boardPrint() {
		// 게시글이 있는지 선택
		if (!isBoardExist(0))
			return;
		// 게시글 번호 선택
		System.out.print("게시글 번호 ");
		int num = menuSelect();
		// 존재하는 글인지 확인
		if (!isBoardNumExist(num))
			return;
		// 게시글 출력
		System.out.println(bDAO.selectOne(num));
		// 9. 뒤로가기
		while (menuSelect() != 9) {
			System.out.println("9.뒤로가기");
			return;
		}
	}

	@Override
	protected int menuSelect() {
		System.out.print("선택 > ");
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
		if (selectRole() != 0 && (menuNo == 2 || menuNo == 3)) { // 관리자외 2.수정 선택불가
			menuNo = 3;
		}
		return menuNo;
	}
}
