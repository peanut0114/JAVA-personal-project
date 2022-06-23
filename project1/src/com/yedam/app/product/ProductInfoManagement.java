package com.yedam.app.product;

import java.util.List;

import com.yedam.app.board.ReviewManagement;
import com.yedam.app.common.Management;
import com.yedam.app.order.OrderInfo;

public class ProductInfoManagement extends Management {

	public ProductInfoManagement() {
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
			} else if (menuNo == 3) {
				// 뒤로가기
				break;
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
		// 제품 이름과 출고수량 입력
		OrderInfo info = inputAmount();

		// 제품의 등록 여부 확인
		Product product = pDAO.selectOne(info.getProductName());

		if (product == null) {
			System.out.println("등록된 제품이 아닙니다.");
			return;
		}

		info.setProductId(product.getProductId());
		
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
		info.setDealAmount(Integer.parseInt(sc.nextLine()));

		return info;
	}
	private void productPrint() {
		System.out.println("**************************");
		List<Product> list = pDAO.selectAll();
		for (Product info : list) {
			System.out.println(info);
		}
		System.out.println("**************************");
	}

	@Override
	protected void menuPrint() {
		System.out.println("--------------------------");
		System.out.println(" 1.주문 2.후기 3.뒤로가기 9.홈");
		System.out.println("--------------------------");
	}

}
