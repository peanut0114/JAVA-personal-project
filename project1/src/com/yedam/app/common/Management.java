package com.yedam.app.common;

import java.util.List;
import java.util.Scanner;

import com.yedam.app.board.Board;
import com.yedam.app.board.BoardDAO;
import com.yedam.app.board.NoticeManagement;
import com.yedam.app.comment.Comment;
import com.yedam.app.comment.CommentDAO;
import com.yedam.app.member.MemberDAO;
import com.yedam.app.order.OrderDAO;
import com.yedam.app.product.ProductDAO;
import com.yedam.app.product.ProductPrint;

public class Management {

	// 필드
	protected Scanner sc = new Scanner(System.in);
	protected ProductDAO pDAO = ProductDAO.getInstance();
	protected OrderDAO oDAO = OrderDAO.getInstance();
	protected BoardDAO bDAO = BoardDAO.getInstance();
	protected CommentDAO cDAO = CommentDAO.getInstance();
	protected MemberDAO mDAO = MemberDAO.getInstance();

	public void run() {
		while (true) {
			menuPrint();
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 1.전체상품
				new ProductPrint().run();
			} else if (menuNo == 2) {
				// 2.공지사항
				new NoticeManagement();
			} else if (menuNo == 3) {
				// 4.로그인
				new LoginControl();
			} else if (menuNo == 4) {
				// 5.마이페이지
				// -1.로그인 확인
				if (checkLogin())
				// -2.회원등급체크
				new MyPage();

			}else if (menuNo == 9) {
				// 9.종료
				exit();
				break;
			} else {
				// 입력오류
				showInputError();
			}
			System.out.println();
		}
	}

	// 메소드
	protected void menuPrint() {
		System.out.println("========================================");
		System.out.println(" 1.전체상품 2.공지사항 3.로그인 4.마이페이지 9.종료");
		System.out.println("========================================");
	}

	// 권환확인 - 반환값 : 관리자 0 회원1 비회원2
	protected int selectRole() {
		if (LoginControl.getLoginInof() == null) {
			return 2;
		}
		return LoginControl.getLoginInof().getMemberRole();
	}

	// 로그인 상태 확인
	protected boolean checkLogin() {
		if (selectRole() == 2) { // 관리자0 회원1 비회원2
			System.out.println("\n로그인 후 이용 가능합니다.");
			System.out.print("로그인하시겠습니까? 1.네 2.아니오 > ");
			int n = Integer.parseInt(sc.nextLine());
			if (n == 1) {
				new LoginControl();
			}
			return false;
		}
		return true;
	}

	protected int menuSelect() {
		System.out.print("선택 > ");
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
		return menuNo;
	}

	protected void exit() {
		System.out.println("프로그램을 종료합니다.");
	}

	protected void showInputError() {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}

	// 카테고리별 게시글이 존재하는지 확인
	protected boolean isBoardExist(int category) {
		List<Board> list = bDAO.selectAll(category);
		if (list.size() <= 0) {
			System.out.println("게시글이 존재하지 않습니다.");
			return false;
		}
		return true;
	}

	// 입력받은 번호의 게시글이 존재하는지 확인
	protected boolean isBoardNumExist(int boardNum, int category) {
		Board board = bDAO.selectOne(boardNum,category);
		if (board.getBoardMId()==null) {
			System.out.println("존재하지 않는 게시글입니다.");
			return false;
		}
		return true;
	}

}
