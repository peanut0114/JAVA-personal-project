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
		while (true) {
			productPrint();
			menuPrint();
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 주문
				orderCookie();
			} else if (menuNo == 2) {
				// 후기
				new ReviewManagement();
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

	private void orderCookie() {
		// 로그인 확인
		if (!checkLogin())
			return;

		// 제품 번호와 주문량 입력
		Order info = inputAmount();
		// 수량 체크
		if(info.getDealAmount()==0||info.getDealAmount()>200) {
				System.out.println("수량은 1~200개 사이로 입력하세요.");
				return;
		}
		// 제품의 등록 여부 확인
		Product product = pDAO.selectOne(info.getProductId());

		if (product == null || product.getProductId()==0) {	//0은 공지글 참조키
			System.out.println("등록된 제품이 아닙니다.");
			return;
		}

		info.setOrdererId(LoginControl.getLoginInof().getMemberId());
		
		//order table에 등록
		oDAO.insert(info);
		//주문정보 출력
		orderPrint(info);

	}

	private void orderPrint(Order info) {
		Member member = mDAO.selectOne(info.getOrdererId());
		Product product = pDAO.selectOne(info.getProductId());
		System.out.println("\n<주문 정보>");
		System.out.println("주문자명 : "+member.getMemberName());
		System.out.println("주문 제품 : "+product.getProductName());
		System.out.println("수량 : "+info.getDealAmount()+"개");
		System.out.println("금액 : "+info.getDealAmount()*product.getProductPrice()+"원");
		System.out.println("\n★주문 완료되었습니다.★");
	}

	private Order inputAmount() { // 거래수량 입력

		Order info = new Order();
		System.out.println("\n<주문창>");
		// 제품이름
		System.out.print("제품번호 > ");
		info.setProductId(Integer.parseInt(sc.nextLine()));
		// 수량
		int amount=0;
		try {
			System.out.print("수량 > ");
			amount = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("입력오류");
		}
		info.setDealAmount(amount);
		return info;

	}

	private void productPrint() {
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++");
		List<Product> list = pDAO.selectAll();
		for (Product info : list) {
			System.out.println("\n  "+info);
		}
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++");
	}

	@Override
	protected void menuPrint() {
		System.out.println(" 1.주문 2.후기 9.홈");
		System.out.println("-----------------");
	}

}
