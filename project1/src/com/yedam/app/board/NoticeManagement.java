package com.yedam.app.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;

public class NoticeManagement extends Management {

	InputStream is = System.in;
	Reader reader = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(reader); // 라인단위로 읽을것

	public NoticeManagement() {

		int currentPage = 1;
		while (true) {
			// 게시글 등록시 토탈 바뀔수있음 -> while문 안으로!
			int totalPage = (int) Math.ceil(bDAO.selectCount(0) / 5.0);
			// 1. 게시판 목록 출력
			boardPagePrint(currentPage, totalPage);
			// 2-1. 관리자 (1.이번페이지 2.다음페이지 3.게시글선택 4.등록 5.수정 6.삭제 9.홈)
			// 2-2. 회원, 비회원 (1.이번페이지 2.다음페이지 3.게시글선택 9.홈)
			menuPrint();
			int menu = menuSelect();

			if (menu == 1) {
				if (currentPage == 1) {
					System.out.println("첫번째 페이지입니다.");
				} else {
					currentPage--;
				}
			} else if (menu == 2) {
				if (currentPage == totalPage) {
					System.out.println("마지막 페이지입니다.");
				} else {
					currentPage++;
				}
			} else if (menu == 3) {
				// 게시글선택 - selectOne
				boardPrint();
			} else if (menu == 4) {
				if (selectRole() != 0)
					showInputError(); // 관리자가 아닐 경우 선택불가
				// 공지등록
				else
					insertNotice();
			} else if (menu == 5) {
				if (selectRole() != 0)
					showInputError();
				// 공지수정
				else
					updateBoard();
			} else if (menu == 6) {
				if (selectRole() != 0)
					showInputError();
				// 공지삭제
				else
					deleteBoard();
			} else if (menu == 9) {
				// 홈
				break;
			} else {
				showInputError();
			}
		}
		System.out.println();
	}

	private void deleteBoard() {
		// 게시글 번호 선택
		System.out.print("게시글 번호 ");
		int num = menuSelect();
		// 등록된 글인지 확인
		if (!isBoardNumExist(num, 0))
			return;
		Board board = bDAO.selectOne(num, 0);

		System.out.println(num + "번 게시글을 정말 삭세하시겠습니까? 1.yes 2.no");
		int menu = menuSelect();
		if (menu == 1) {
			bDAO.delete(num);
		}
	}

	// 공지 목록 출력 - 페이징
	protected void boardPagePrint(int currentPage, int totalPage) {

		System.out.println("\n+++++++++++++++++++++++공지게시판++++++++++++++++++++++\n");
		// 내용이 없는 경우
		if (totalPage == 0) {
			System.out.println(" 구매후기가 없습니다.");
		}
		// 페이지의 목록 출력
		List<Board> list = bDAO.selectAll(currentPage, 0);
		for (Board board : list) {
			System.out.println(
					"  " + board.getBoardNum() + ". " + board.getBoardSubject() + "    " + board.getBoardDate());
		}
		// 페이지 블록 + 현재 페이지 프린트
		System.out.print("\n  [ ");
		for (int i = 1; i <= totalPage; i++) {
			System.out.print(i + " ");
		}
		System.out.println("] " + currentPage + "페이지");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
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
		str="";
		try {
			System.out.println("수정할 내용 (q입력시 입력종료) > ");
			String lineStr;
			while (true) {
				lineStr = br.readLine();
				if (lineStr.equals("q") || lineStr.equals("quit"))
					break;
				str+=lineStr+"\n  ";
			}
			//br.close(); //닫으면 sc도 닫혀서 오류 생김...
		} catch (IOException e) {
			e.printStackTrace();
		} 

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
		String str="";
		try {
			System.out.println("내용 (q입력시 입력종료) > ");
			String lineStr;
			while (true) {
				lineStr = br.readLine();
				if (lineStr.equals("q") || lineStr.equals("quit"))
					break;
				str+=lineStr+"\n  ";
			}
			//br.close(); //닫으면 sc도 닫혀서 오류 생김...
		} catch (IOException e) {
			e.printStackTrace();
		} 

		notice.setBoardContent(str);
		notice.setBoardStar(0);
		notice.setBoardCategory(0); // 0공지 1후기
		notice.setProductId(0);// 0 공지 상품참조키

		bDAO.insert(notice);
	}

	@Override
	protected void menuPrint() {
		if (selectRole() == 0) {
			System.out.println(" 1.이번페이지 2.다음페이지 3.게시글선택 4.등록 5.수정 6.삭제 9.홈");
			System.out.println("----------------------------------------------------");
		} else {
			System.out.println(" 1.이전페이지 2.다음페이지 3.게시글선택 9.홈");
			System.out.println("-----------------------------------");
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
		System.out.println("\n*********************************************************");
		System.out.println("\n  " + bDAO.selectOne(num, 0));
		System.out.println("\n*********************************************************");
		// 9.뒤로가기
		System.out.println("9.뒤로가기");
		while (menuSelect() != 9) {

		}
	}

}
