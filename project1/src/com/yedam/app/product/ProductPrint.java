package com.yedam.app.product;

import java.util.List;

import com.yedam.app.board.ReviewManagement;
import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;
import com.yedam.app.member.Member;
import com.yedam.app.order.Order;

public class ProductPrint extends Management {

	public ProductPrint() {
	}

	public void run() {
		int currentPage = 1;
		while (true) {
			int totalPage = (int) Math.ceil(bDAO.selectCount(1) / 5.0);
			int menuNo = 0;

			productPagePrint(currentPage, totalPage);
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
				// 주문 - 성공시 홈으로
				if (orderCookie()) {
					break;
				}
			} else if (menuNo == 4) {
				// 후기
				new ReviewManagement();
			} else if (menuNo == 9) {
				// 뒤로가기
				break;
			} else {
				// 입력오류
				showInputError();
			}
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println(" 1.이전페이지 2.다음페이지 3.주문 4.후기 9.홈");
		System.out.println("------------------------------------");
	}

	private boolean orderCookie() {
		// 로그인 확인
		if (!checkLogin())
			return false;

		// 제품 번호와 주문량 입력
		Order info = inputAmount();
		// 제품의 등록 여부 확인
		Product product = pDAO.selectOne(info.getProductId());

		if (product == null || product.getProductId() == 0) { // 0은 공지글 참조키
			System.out.println("제품 번호를 확인해주세요.");
			return false;
		}
		// 수량 체크
		if (info.getDealAmount() == 0 || info.getDealAmount() > 200) {
			System.out.println("수량은 1~200개 사이로 입력하세요.");
			return false;
		}

		info.setOrdererId(LoginControl.getLoginInof().getMemberId());

		// order table에 등록
		oDAO.insert(info);

		// 주문정보 출력
		orderPrint(info);
		return true;
	}

	private void orderPrint(Order info) {
		Member member = mDAO.selectOne(info.getOrdererId());
		Product product = pDAO.selectOne(info.getProductId());
		System.out.println("\n<주문 정보>");
		System.out.println("주문자명 : " + member.getMemberName());
		System.out.println("주문 제품 : " + product.getProductName());
		System.out.println("수량 : " + info.getDealAmount() + "개");
		System.out.println("금액 : " + info.getDealAmount() * product.getProductPrice() + "원");
		System.out.println("\n★주문 완료되었습니다.★");
	}

	private Order inputAmount() { // 거래수량 입력

		Order info = new Order();
		System.out.println("\n<주문창>");
		int amount = 0;
		// 제품이름
		try {
			System.out.print("제품번호 > ");
			info.setProductId(Integer.parseInt(sc.nextLine()));
			// 수량
			System.out.print("수량 > ");
			amount = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.print("잘못된 입력입니다. ");
		}
		info.setDealAmount(amount);
		return info;

	}

	// 상품 목록 출력 - 페이징
	protected void productPagePrint(int currentPage, int totalPage) {

		System.out.println("\n+++++++++++++++++++ 맛있는 쿠키 ++++++++++++++++++\n");
		// 내용이 없는 경우
		if (totalPage == 0) {
			System.out.println(" 구매후기가 없습니다.");
		}
		// 페이지의 목록 출력
		List<Product> list = pDAO.selectAll(currentPage);

		for (Product info : list) {
			System.out.println(" " + info+"\n");
		}
		// 페이지 블록 + 현재 페이지 프린트
		System.out.print(" [ ");
		for (int i = 1; i <= totalPage; i++) {
			System.out.print(i + " ");
		}
		System.out.println("] " + currentPage + "페이지");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");

	}

//	private void productPrint() {
//		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++");
//		List<Product> list = pDAO.selectAll();
//		for (Product info : list) {
//			System.out.println("\n  " + info);
//		}
//		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++");
//	}

	
}
