package com.yedam.app.board;

import java.util.List;

import com.yedam.app.comment.Comment;
import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;
import com.yedam.app.order.Order;

public class ReviewManagement extends Management {

	public ReviewManagement() {
		System.out.println();

		while (true) {
			// 리뷰출력
			allReviewPrint();
			// 1.후기자세히보기 2.후기등록 9.뒤로가기
			menuPrint();
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 후기선택
				reviewPrint();
			} else if (menuNo == 2) {
				// 후기등록
				isertReview();
			} else if (menuNo == 9) {
				// 뒤로가기
				break;
			} else {
				// 입력오류
				showInputError();
			}
			System.out.println();
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println(" 1.후기 자세히보기  2.후기등록  9.뒤로가기");
		System.out.println("----------------------------------");
	}


	private void reviewPrint() {	//2. 후기 자세히 보기
			// 게시글이 하나도 없을 경우 break
			if(!isBoardExist(1)) return;
			// 게시글 번호 선택 
			System.out.print("게시글 번호 ");
			int boardNum = menuSelect();
			// 존재하는 글인지 확인
			if (!isBoardNumExist(boardNum,1)) return;
			// 후기글+댓글 프린트
			System.out.println();
			reviewPrint(boardNum);
			// 1.댓글작성 2.후기수정 9.뒤로가기 메뉴출력
			System.out.println("\n1.댓글작성 2.후기수정 9.뒤로가기");
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 1.댓글작성
				insertComment(boardNum);
			} else if (menuNo == 2) {
				// 2. 후기 수정
				updateBoard(boardNum);
			}else if (menuNo == 9) {
				// 9. 뒤로가기
				return;
			} else {
				// 입력오류
				showInputError();
			}
		
	}

	// 후기글+댓글 프린트
	private void reviewPrint(int num) {
		Board board = bDAO.selectOne(num,1);
		List<Comment> list = cDAO.selectAll(board.getBoardNum());
		System.out.println("----------------------------------------------------");
		System.out.println(board);
		System.out.println("  - - - - - - - - - - - - - -");
		for (Comment comment : list) {
			System.out.println(comment);
		}
		System.out.println("----------------------------------------------------");

	}

	private void isertReview() {
		// 로그인 확인
		if (!checkLogin())
			return;
		// 상품 번호 선택
		System.out.printf("후기 작성 제품 ");
		int productNo = menuSelect();
		// 구매여부 확인
		if (!checkRealBuy(productNo))
			return;
		// 구매 내역 있을시 작성
		Board review = new Board();
		review.setBoardMId(LoginControl.getLoginInof().getMemberId());
		review.setBoardPwd(LoginControl.getLoginInof().getMemberPwd());
		System.out.print("별점 (1~5) > ");
		review.setBoardStar(Integer.parseInt(sc.nextLine()));
		System.out.print("제목 > ");
		review.setBoardSubject(sc.nextLine());
		System.out.print("내용 > ");
		review.setBoardContent(sc.nextLine());
		review.setBoardCategory(1); // 0공지 1후기
		review.setProductId(productNo);

		bDAO.insert(review);
	}

	private void insertComment(int boardNum) {
		// 로그인 확인
		if (!checkLogin())
			return;
		
		Comment comment = new Comment();
		comment.setCommentMid(LoginControl.getLoginInof().getMemberId());
		comment.setCommentBnum(boardNum);
		comment.setCommentContent(writeReview());

		cDAO.insert(comment);
	}

	private String writeReview() {
		System.out.println("내용 > ");
		return sc.nextLine();
	}

	private boolean checkRealBuy(int productId) { // 상품 구매 이력 확인
		String memberId = LoginControl.getLoginInof().getMemberId();

		List<Order> list = oDAO.selectAll(memberId, productId);
		if (list.size() <= 0) {
			System.out.println("구매한 상품만 작성 가능합니다.");
			return false;
		}
		return true;
	}

	private void allReviewPrint() { // 후기 제목 출력
		List<Board> list = bDAO.selectAll(1);
		System.out.println("+++++++++++++++++++구매후기+++++++++++++++++++++\n");
		for (Board board : list) {
			String str = " "+board.getBoardNum() + " " +board.getStar() +" "
			+ board.getBoardSubject() + " | 주문 :" + board.getProductName() ;
			System.out.println(str);
		}
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++");
	}


	// 게시글 수정
	protected void updateBoard(int boardNum) {
		//로그인 상태 확인
		if(!checkLogin()) return;
		// 작성자인지 확인
		Board board = bDAO.selectOne(boardNum,1);
		if (!board.getBoardMId().equals(LoginControl.getLoginInof().getMemberId())) {
			System.out.println("작성자만 수정 가능합니다.");
			return;
		}
		// 수정
		System.out.println("현재 제목: " + board.getBoardSubject());
		System.out.println("수정할 제목 (수정 하지 않을 경우 0 입력) > ");
		String str = sc.nextLine();
		if (!str.equals("0")) {
			board.setBoardSubject(str);
		}
		System.out.println("현재 내용: " + board.getBoardContent());
		System.out.println("수정할 내용 (수정 하지 않을 경우 0 입력) > ");
		str = sc.nextLine();
		if (!str.equals("0")) {
			board.setBoardContent(str);
		}
		bDAO.updateInfo(board);
	}
}
