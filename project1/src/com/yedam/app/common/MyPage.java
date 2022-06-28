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
				if (selectRole() == 0)
					allOrderPrint();
				// 1 회원 : 회원정보조회
				else
					memberInfo();
			} else if (menu == 2) {
				// 0 관리자 : 상품조회
				if (selectRole() == 0)
					allProductPrint();
				// 1 회원 : 회원정보수정
				else
					updateMember();
			} else if (menu == 3) {
				// 0 관리자 : 상품등록
				if (selectRole() == 0)
					insertProduct();
				else
					showInputError();
			} else if (menu == 4) {
				// 0 관리자 : 상품삭제
				if (selectRole() == 0)
					deleteProduct();
				else
					showInputError();
			} else if (menu == 9) {
				break;
			} else {
				showInputError();
			}
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println("\n---------------------------------------------");
		if (selectRole() == 0) {
			System.out.println("1.주문조회 2.상품목록조회 3.상품등록 4.상품삭제 9.홈");

		} else {
			System.out.println("1.회원정보조회 2.회원정보수정 9.홈");
		}
		System.out.println("---------------------------------------------");
	}

	// 모든 주문정보 출력
	private void allOrderPrint() {
		while (true) {
			List<Order> list = oDAO.selectAll();
			
			// 주문 리스트 출력
			System.out.println("\n**************** 주문 목록 ***************");
			if (list.size() == 0) {
				System.out.println(" 주문 내역이 없습니다.");
			}else {
				System.out.println(" (○:배송준비중, ●:발송완료)\n");
				for (Order info : list) {
					System.out.println(" " + info + " 주문자: " + info.getOrdererId());
				}
			}
			System.out.println("\n****************************************");
			
			String menuPrint = " 1.배송상태 변경 9.뒤로가기";
			if (list.size() == 0) {
				menuPrint = " 9.뒤로가기";
			}
			System.out.println(menuPrint);
			System.out.println("-----------------------");
			int menu = menuSelect();
			if (menu == 1) {
				if (list.size() == 0) {
					showInputError();
				} else {
					changeCondition();
				}
			} else if (menu == 9) {
				break;
			} else {
				showInputError();
			}
		}
	}

	private void changeCondition() {
		// 주문 번호 입력
		System.out.print("주문 번호 ");
		int id = menuSelect();
		// 존재하는 주문정보인지 확인
		Order order = oDAO.selectOne(id);
		if (order == null) {
			System.out.println("주문 번호를 다시 확인해주세요.");
			return;
		}
		System.out.println(id + "번 주문의 발송상태를 변경하시겠습니까? 1.YES 2.NO");
		int menu = menuSelect();
		if (menu == 1) {
			if (order.getCondition() == 0) {
				oDAO.update(id, 1);
			} else {
				oDAO.update(id, 0);
			}

		} else if (menu == 2) {
			System.out.println("취소되었습니다.");
		} else {
			showInputError();
		}
	}

	// 상품출력
	private void allProductPrint() {
		System.out.println("\n<상품목록>");
		List<Product> list = pDAO.selectAll();
		for (Product info : list) {
			System.out.println(info);
		}
		System.out.println();
	}

	// 회원정보 출력
	private void memberInfo() {
		System.out.println("\n++++++++++++++++++회원정보+++++++++++++++++\n");
		// 기본 회원 정보
		Member member = mDAO.selectOne(LoginControl.getLoginInof().getMemberId());
		System.out.println(member);
		System.out.println("\n------------------주문내역-----------------\n");
		// 주문내역
		List<Order> list = oDAO.selectAll(member.getMemberId());
		if (list.size() == 0) {
			System.out.println(" 주문 내역이 없습니다.");
		}else {
			System.out.println(" (○:배송준비중, ●:발송완료)\n");
			for (Order info : list) {
				System.out.println(" " + info);
			}
		}
		System.out.println("\n\t║▌│█║▌║ █║▌│█│║▌║");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++");

	}

	// 상품등록
	private void insertProduct() {
		Product product = new Product();
		try {
		System.out.println("제품명 > ");
		product.setProductName(sc.nextLine());
		System.out.println("가격 > ");
		product.setProductPrice(Integer.parseInt(sc.nextLine()));
		System.out.println("제품 설명 > ");
		product.setProductExplain(sc.nextLine());

		pDAO.insert(product);
		}catch(NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		}
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
	
	//삭제
	private void deleteProduct() {
		try {
		System.out.print("제품 번호 선택 > ");
		int num = Integer.parseInt(sc.nextLine());
		Product product = pDAO.selectOne(num);
		if (product == null) {
			System.out.println("존재하지 않는 제품입니다.");
			return;
		}
		if(oDAO.selectAmount(num)>0) {
			System.out.println("주문 내역이 있는 상품은 삭제할 수 없습니다.");
			return;
		}
		System.out.println(product.getProductName() + "를 정말 삭제하시겠습니까? 1.YES 2.NO");
		int menu = menuSelect();
		if (menu == 1)
			pDAO.delete(num);
		}catch(NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
	}

}
