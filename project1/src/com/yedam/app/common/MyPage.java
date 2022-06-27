package com.yedam.app.common;

import java.util.List;

import com.yedam.app.member.Member;
import com.yedam.app.member.MemberDAO;
import com.yedam.app.order.Order;
import com.yedam.app.product.Product;
import com.yedam.app.product.ProductDAO;

public class MyPage extends Management {

	protected MemberDAO mDAO = MemberDAO.getInstance();
	protected ProductDAO pDAO = ProductDAO.getInstance();

	public MyPage() {
		while (true) {
			// 0관리자 : 1.회원주문조회 2.상품목록 3.상품등록 3.상품삭제 9.홈
			// 1회원 : 1.회원정보조회 2.회원정보수정 9.홈
			menuPrint();
			int menu = menuSelect();
			if (menu == 1) {
				// 0 관리자 : 회원관리(주문조회)
				if (selectRole() == 0) allOrderPrint();
				// 1 회원 : 회원정보조회
				else memberInfo();
			} else if (menu == 2) {
				// 0 관리자 : 상품조회
				if (selectRole() == 0) productPrint();
				// 1 회원 : 회원정보수정
				else updateMember();
			} else if (menu == 3) {
				// 0 관리자 : 상품등록
				if (selectRole() == 0) insertProduct();
				else showInputError();
			} else if (menu == 4) {
				// 0 관리자 : 상품삭제
				if (selectRole() == 0) deleteProduct();
				else showInputError();
			} else if (menu == 9) {
				break;
			} else {
				showInputError();
			}
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println("---------------------------------------------");
		if (selectRole() == 0) {
			System.out.println("1.주문조회 2.상품목록조회 3.상품등록 4.상품삭제 9.홈");

		} else {
			System.out.println("1.회원정보조회 2.회원정보수정 9.홈");
		}
		System.out.println("---------------------------------------------");
	}

	//모든 주문정보 출력
	private void allOrderPrint() {
		List<Order> list = oDAO.selectAll();
		// 주문 리스트 출력
		System.out.println("*******************************");
		for (Order info : list) {
			System.out.println(" " + info+ " 주문자: " + info.getOrdererId());
		}
		System.out.println("*******************************");
	}

	// 회원정보 출력
	private void memberInfo() {
		System.out.println("\n++++++++++++++++회원정보+++++++++++++++\n");
		// 기본 회원 정보
		Member member = mDAO.selectOne(LoginControl.getLoginInof().getMemberId());
		System.out.println(member);
		System.out.println("\n  --------------주문내역--------------\n");
		// 주문내역
		List<Order> list = oDAO.selectAll(member.getMemberId());
		for (Order info : list) {
			System.out.println(" " + info);
		}
		System.out.println("\n\t║▌│█║▌║ █║▌│█│║▌║");
		System.out.println("++++++++++++++++++++++++++++++++++++");

	}

	// 상품출력
	private void productPrint() {
		System.out.println("\n<상품목록>");
		List<Product> list = pDAO.selectAll();
		for (Product info : list) {
			System.out.printf("");
		}
		System.out.println();
	}

	// 상품등록
	private void insertProduct() {
		Product product = new Product();
		System.out.println("제품명 > ");
		product.setProductName(sc.nextLine());
		System.out.println("가격 > ");
		product.setProductPrice(Integer.parseInt(sc.nextLine()));
		System.out.println("제품 설명 > ");
		product.setProductExplain(sc.nextLine());

		pDAO.insert(product);
	}

	// 회원정보 수정
	private void updateMember() {
		Member member = mDAO.selectOne(LoginControl.getLoginInof().getMemberId());

		System.out.println("\n< 회원정보 수정 | 비밀번호, 연락처, 주소 >");
		// 비밀번호 확ㅇ니
		System.out.print("기존 비밀번호 입력 > ");
		String info = sc.nextLine();
		if (!info.equals(member.getMemberPwd())) {
			System.out.println("정보가 일치하지 않습니다.");
			return;
		}
		System.out.print("수정할 비밀번호 입력 (수정 하지 않을 경우 0) > ");
		info = sc.nextLine();
		if (!info.equals("0")) {
			member.setMemberPwd(info);
		}
		System.out.println("연락처: " + member.getMemberPhone());
		System.out.print("수정 정보 입력 (수정 하지 않을 경우 0) > ");
		info = sc.nextLine();
		if (!info.equals("0")) {
			member.setMemberPhone(info);
		}
		System.out.println("주소: " + member.getMemberAddr());
		System.out.print("수정 정보 입력 (수정 하지 않을 경우 0) > ");
		info = sc.nextLine();
		if (!info.equals("0")) {
			member.setMemberAddr(info);
		}
		System.out.println();
		mDAO.updateInfo(member);

	}

	private void deleteProduct() {
		System.out.print("제품 번호 ");
		int num = menuSelect();
		Product product = pDAO.selectOne(num);
		if (product == null) {
			System.out.println("존재하지 않는 제품입니다.");
			return;
		}
		System.out.println(product.getProductName() + "를 정말 삭제하시겠습니까? 1.YES 2.NO");
		int menu = menuSelect();
		if (menu == 1)
			pDAO.delete(num);
	}

}
