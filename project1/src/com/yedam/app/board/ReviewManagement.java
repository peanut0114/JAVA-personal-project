package com.yedam.app.board;

import java.util.List;

import com.yedam.app.comment.Comment;
import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;
import com.yedam.app.order.OrderInfo;
import com.yedam.app.product.Product;

public class ReviewManagement extends Management {

	public ReviewManagement() {
		System.out.println();

		while (true) {
			// 리뷰출력
			reviewChartPrint();
			// 1.후기등록 2.후기수정 3.댓글작성 9.뒤로가기
			menuPrint();
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 후기등록
				isertReview();
			} else if (menuNo == 2) {
				// 후기 자세히 보기
				reviewContent();
			} else if (menuNo == 3) {
				// 댓글작성
				insertComment();
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
		System.out.println("----------------------------------");
		System.out.println(" 1.후기등록  2.후기 자세히 보기  9.뒤로가기");
		System.out.println("----------------------------------");
	}

	private void reviewContent() {

		while (true) {
			// 후기글 번호 선택
			int reviewNum = menuSelect();
			// 후기글+댓글 프린트
			reviewPrint(reviewNum);
			// 1.댓글작성 2.뒤로가기
			System.out.println("\n1.댓글작성 2.뒤로가기");
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 후기등록
				isertReview();
			} else if (menuNo == 2) {
				// 후기 자세히 보기
				reviewContent();
			} else if (menuNo == 3) {
				// 댓글작성
				insertComment();
			} else if (menuNo == 9) {
				// 뒤로가기
				break;
			} else {
				// 입력오류
				showInputError();
			}

		}
	}

	private void reviewPrint(int num) {
		Board board = bDAO.selectOne(num);
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

	private void insertComment() {
		// 로그인 확인
		if (!checkLogin())
			return;
		// 후기글 번호 선택
		int boardNo = menuSelect();

		Comment comment = new Comment();
		comment.setCommentMid(LoginControl.getLoginInof().getMemberId());
		comment.setCommentBnum(boardNo);
		comment.setCommentContent(writeReview());

		cDAO.insert(comment);
	}

	private String writeReview() {
		System.out.println("내용 > ");
		return sc.nextLine();
	}

	private boolean checkRealBuy(int productId) { // 상품 구매 이력 확인
		String memberId = LoginControl.getLoginInof().getMemberId();

		List<OrderInfo> list = oDAO.selectAll(memberId, productId);
		if (list.size() <= 0) {
			System.out.println("구매한 상품만 작성 가능합니다.");
			return false;
		}
		return true;
	}

	private void reviewChartPrint() { // 후기 출력
		List<Board> list = bDAO.selectAll();
		System.out.println("---------------------구매후기--------------------------");
		for (Board board : list) {
			String str = board.getBoardNum() + ". " + board.getBoardSubject() + " > 주문 :" + board.getProductName() + " "
					+ board.getStar();
			System.out.println(str);
		}
		System.out.println("----------------------------------------------------");
	}

	protected int cookieSelect() {
		System.out.println("번호 선택 > ");
		int productId = Integer.parseInt(sc.nextLine());
		Product product = pDAO.selectOne(productId);
		if (product == null) {
			System.out.println("해당 쿠키가 존재하지 않습니다.");
			return 0;
		}
		return productId;

	}

}
