package com.yedam.app.product;

import java.util.List;

import com.yedam.app.board.ReviewManagement;
import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;
import com.yedam.app.order.OrderInfo;

public class ProductInfoManagement extends Management {

	public ProductInfoManagement() {
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
		OrderInfo info = inputAmount();

		// 제품의 등록 여부 확인
		Product product = pDAO.selectOne(info.getProductId());

		if (product == null) {
			System.out.println("등록된 제품이 아닙니다.");
			return;
		}

		info.setOrdererId(LoginControl.getLoginInof().getMemberId());

		oDAO.insert(info);
		// 제품의 재고량을 수정

	}

	private OrderInfo inputAmount() { // 거래수량 입력

		OrderInfo info = new OrderInfo();
		// 제품이름
		System.out.print("제품번호 > ");
		info.setProductId(Integer.parseInt(sc.nextLine()));
		// 수량
		System.out.print("수량 > ");
		int amount=0;
		try {
			amount = Integer.parseInt(sc.nextLine());
			if (amount > 200) {
				System.out.println("200개까지 주문 가능합니다.\n수량 > ");
				amount = Integer.parseInt(sc.nextLine());
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
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
