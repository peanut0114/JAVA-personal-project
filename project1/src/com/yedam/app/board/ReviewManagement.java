package com.yedam.app.board;

import java.util.List;

import com.yedam.app.comment.Comment;
import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;
import com.yedam.app.order.Order;

public class ReviewManagement extends Management {

	public ReviewManagement() {

		int currentPage = 1;
		while (true) {
			int totalPage = (int) Math.ceil(bDAO.selectCount(1) / 5.0);
			int menuNo = 0;
			// 후기출력
			boardPagePrint(currentPage,totalPage);
			// 1.이전페이지 2.다음페이지 3.자세히보기 4.등록  9.뒤로가기
			menuPrint();
			menuNo = menuSelect();

			if (menuNo == 1) {
				if(currentPage==1) {
					System.out.println("첫번째 페이지입니다.");
				}else {
					currentPage--;
				}
			} else if (menuNo == 2) {
				if(currentPage==totalPage) {
					System.out.println("마지막 페이지입니다.");
				}else {
					currentPage++;
				}
			} else if (menuNo == 3) {
				// 자세히보기
				reviewPrint();
			} else if (menuNo == 4) {
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
		System.out.println("1.이전페이지 2.다음페이지 3.자세히보기 4.등록  9.뒤로가기");
		System.out.println("---------------------------------------------");
	}
	
	// 후기 자세히 보기
	private void reviewPrint() {
		// 게시글이 하나도 없을 경우 break
		if (!isBoardExist(1))
			return;
		// 게시글 번호 선택
		System.out.print("게시글 번호 ");
		int boardNum = menuSelect();
		// 존재하는 글인지 확인
		if (!isBoardNumExist(boardNum, 1))
			return;
		while (true) {
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
			} else if (menuNo == 9) {
				// 9. 뒤로가기
				break;
			} else {
				// 입력오류
				showInputError();
			}
		}
	}

	// 후기글+댓글 프린트
	private void reviewPrint(int num) {
		Board board = bDAO.selectOne(num, 1);
		List<Comment> list = cDAO.selectAll(board.getBoardNum());
		System.out.println("----------------------------------------------------");
		System.out.println(board);
		System.out.println("  - - - - - - - - - - - - - -");
		for (Comment comment : list) {
			System.out.println(comment);
		}
		System.out.println("----------------------------------------------------");

	}

	// 후기입력
	private void isertReview() {
		// 로그인 확인
		if (!checkLogin())
			return;
		// 구매내역 확인
		String id = LoginControl.getLoginInof().getMemberId();
		if (oDAO.selectAmount(id) == 0) {
			System.out.println("구매 내역이 없습니다.");
			return;
		}
		//구매내역 출력
		System.out.println("구매 내역이 있는 제품 : ");
		List<Order> list = oDAO.selectAll(id);
		for (Order info : list) {
			System.out.println(info.getDealDate()+" "+ info.getProductId() + "." + info.getProductName() + " ");
		}
		// 쿠키 번호 선택
		System.out.printf("\n후기를 작성할 쿠키 번호 ");
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

	// 댓글입력
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
		System.out.print("내용 > ");
		return sc.nextLine();
	}

	// 상품 구매 이력 확인
	private boolean checkRealBuy(int productId) {
		String memberId = LoginControl.getLoginInof().getMemberId();

		List<Order> list = oDAO.selectAll(memberId, productId);
		if (list.size() <= 0) {
			System.out.println("구매한 상품만 작성 가능합니다.");
			return false;
		}
		return true;
	}

	//페이지 출력
	protected void boardPagePrint(int currentPage, int totalPage) {
		System.out.println("\n+++++++++++++++++++구매후기+++++++++++++++++++++\n");
		//내용이 없는 경우
		if (totalPage == 0) {
			System.out.println(" 구매후기가 없습니다.");
		}
		//페이지의 목록 출력
		List<Board> list = bDAO.selectAll(currentPage, 1);
		for (Board board : list) {
			String str = " " + board.getBoardNum() + " " + board.getStar() 
						+ " " + board.getBoardSubject() 
						+ " | 주문 :"+ board.getProductName();
			System.out.println(str);
		}
		//페이지 블록 + 현재 페이지 프린트
		System.out.print("\n [ ");
		for (int i = 1; i <= totalPage; i++) {
			System.out.print(i + " ");
		}
		System.out.println("] "+currentPage+"페이지");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
	}


	// 게시글 수정
	protected void updateBoard(int boardNum) {
		// 로그인 상태 확인
		if (!checkLogin())
			return;
		// 작성자인지 확인
		Board board = bDAO.selectOne(boardNum, 1);
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
