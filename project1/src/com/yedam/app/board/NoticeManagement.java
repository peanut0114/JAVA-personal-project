package com.yedam.app.board;

import java.util.List;

import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;

public class NoticeManagement extends Management {

	public NoticeManagement() {

		while (true) {
			// 1. 게시판 목록 출력
			allNoticePrint();
			// 2-1. 관리자 (1.게시글선택 2.공지등록 3.공지수정 9.홈)
			// 2-2. 회원, 비회원 (1.게시글선택 9.홈)
			menuPrint();
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
			} else if (menu == 4) {
				// 공지삭제
				deleteBoard();
			} else if (menu == 9) {
				// 홈
				break;
			} else {
				showInputError();
			}
			System.out.println();
		}
	}

	private void deleteBoard() {
		// 게시글 번호 선택
		System.out.print("게시글 번호 ");
		int num = menuSelect();
		// 등록된 글인지 확인
		if (!isBoardNumExist(num, 0))
			return;
		Board board = bDAO.selectOne(num, 0);

		System.out.println(num+"번 게시글을 정말 삭세하시겠습니까? 1.yes 2.no");
		int menu= menuSelect();
		if(menu==1) {
			bDAO.delete(num);
		}
	}

	private void allNoticePrint() {
		List<Board> list = bDAO.selectAll(0);
		System.out.println("\n---------------게시판-----------------\n");
		// 게시글이 존재하는지 확인
		if (!isBoardExist(0)) {
			System.out.println("\n------------------------------------");
			return;
		}
		// 존재할 경우
		for (Board board : list) {
			System.out.println("  " + board.getBoardNum() 
							+ ". " + board.getBoardSubject()
							+ "\t\t"+board.getBoardDate());
		}

		System.out.println("\n------------------------------------");
	}

	// 게시글 수정
	protected void updateBoard() {
		// 게시글 번호 선택
		System.out.print("게시글 번호 ");
		int num = menuSelect();
		// 등록된 글인지 확인
		if (!isBoardNumExist(num, 0))
			return;
		Board board = bDAO.selectOne(num, 0);

		// 작성자인지 확인
		if (!board.getBoardMId().equals(LoginControl.getLoginInof().getMemberId())) {
			System.out.println("작성자만 수정 가능합니다.");
			return;
		}
		// 수정
		System.out.println("제목: " + board.getBoardSubject());
		System.out.println("제목 수정 (수정 하지 않을 경우 0 입력) > ");
		String str = sc.nextLine();
		if (!str.equals("0")) {
			board.setBoardSubject(str);
		}
		System.out.println("내용: " + board.getBoardContent());
		System.out.println("내용 수정 (수정 하지 않을 경우 0 입력) > ");
		str = sc.nextLine();
		if (!str.equals("0")) {
			board.setBoardContent(str);
		}
		bDAO.updateInfo(board);
	}

	private void insertNotice() {
		Board notice = new Board();
		notice.setBoardMId(LoginControl.getLoginInof().getMemberId());
		notice.setBoardPwd(LoginControl.getLoginInof().getMemberPwd());
		System.out.println("제목 > ");
		notice.setBoardSubject(sc.nextLine());
		System.out.println("내용 > ");
		notice.setBoardContent(sc.nextLine());

		notice.setBoardStar(0);
		notice.setBoardCategory(0); // 0공지 1후기
		notice.setProductId(0);// 0 공지 상품참조키

		bDAO.insert(notice);
	}

	@Override
	protected void menuPrint() {
		if (selectRole() == 0) {
			System.out.println(" 1.게시글선택 2.공지등록 3.공지수정 4.공지삭제 9.홈");
			System.out.println("----------------------------------------");
		} else {
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
		if (!isBoardNumExist(num, 0))
			return;
		// 게시글 출력
		System.out.println("\n***********************************************");
		System.out.println("\n  " + bDAO.selectOne(num, 0));
		System.out.println("\n***********************************************");
		//9.뒤로가기
		System.out.println("9.뒤로가기");
		while (menuSelect() != 9) {
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
		if ((selectRole() != 0) && (menuNo > 1 && menuNo <9)) { // 메뉴판 메뉴 외 선택불가
			menuNo = 0;
		}
		return menuNo;
	}
}
