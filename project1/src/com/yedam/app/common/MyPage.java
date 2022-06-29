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
			// 0관리자 : 1.회원주문조회 2.상품목록 9.홈
			// 1회원 : 1.회원정보조회 2.회원정보수정 9.홈
			menuPrint();
			int menu = menuSelect();
			if (menu == 1) {
				// 0 관리자 : 전체주문조회
				if (selectRole() == 0)
					allOrderPrint();
				// 1 회원 : 회원정보조회
				else
					memberInfo();
			} else if (menu == 2) {
				// 0 관리자 : 상품목록조회
				if (selectRole() == 0)
					allProductPrint();
				// 1 회원 : 회원정보수정
				else
					updateMember();
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
			System.out.println("1.전체주문조회 2.상품목록조회 9.홈");

		} else {
			System.out.println("1.회원정보조회 2.회원정보수정 9.홈");
		}
		System.out.println("---------------------------------------------");
	}

	// 모든 이용자의 주문 내역 출력
	private void allOrderPrint() {
		int currentPage = 1;
		while (true) {
			int totalPage = (int) Math.ceil(bDAO.selectCount(0) / 5.0);
			orderPagePrint(currentPage, totalPage);

			System.out.println(" 1.이전페이지 2.다음페이지 3.배송상태 변경 9.뒤로가기");
			System.out.println("-----------------------------------------");

			int menu = menuSelect();
			if (menu == 1) {
				if (currentPage == 1) {
					System.out.println("첫번째 페이지입니다.");
				} else {
					currentPage--;
				}
			} else if (menu == 2) {
				if (currentPage == totalPage) {
					System.out.println("마지막 페이지입니다.");
				} else {
					currentPage++;
				}
			} else if (menu == 3) {
				if (totalPage == 0) {
					System.out.println(" 증록된 주문 내역이 없습니다.");
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

	

	// 모든 주문 내역
	private void orderPagePrint(int currentPage, int totalPage) {
		System.out.println("\n******************** 주문 목록 *******************");

		if (totalPage == 0) {
			System.out.println(" 주문 내역이 없습니다.");
		} else {
			List<Order> list = oDAO.selectAll(currentPage);
			System.out.println(" (○:배송준비중, ●:발송완료)");
			for (Order info : list) {
				System.out.println("\n " + info + " 주문자: " + info.getOrdererId());
			}
		}

		// 페이지 블록 + 현재 페이지 프린트
		System.out.print("\n [ ");
		for (int i = 1; i <= totalPage; i++) {
			System.out.print(i + " ");
		}
		System.out.println("] " + currentPage + "페이지");
		System.out.println("\n***********************************************");

	}

	// 주문상태 변경
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

	// 상품출력 - 페이징
	private void allProductPrint() {
		int currentPage = 1;

		while (true) {
			int totalPage = (int) Math.ceil(pDAO.selectCount() / 5.0);
			// 상품출력
			ProductPagePrint(currentPage, totalPage);

			int menuNo = 0;
			System.out.println("1.이전페이지 2.다음페이지 3.등록 4.수정 5.삭제 9.홈");
			menuNo = menuSelect();

			if (menuNo == 1) {
				if (currentPage == 1) {
					System.out.println("첫번째 페이지입니다.");
				} else {
					currentPage--;
				}
			} else if (menuNo == 2) {
				if (currentPage == totalPage) {
					System.out.println("마지막 페이지입니다.");
				} else {
					currentPage++;
				}
			} else if (menuNo == 3) {
				// 상품등록
				insertProduct();
			} else if (menuNo == 4) {
				// 상품정보 수정
				updatdProduct();
			} else if (menuNo == 5) {
				// 상품삭제
				deleteProduct();
			} else if (menuNo == 9) {
				// 뒤로가기
				break;
			} else {
				// 입력오류
				showInputError();
			}
		}
	}

	// 상품 목록 출력
	private void ProductPagePrint(int currentPage, int totalPage) {
		System.out.println("\n+++++++++++++++++<상품목록>+++++++++++++++++\n");
		// 내용이 없는 경우
		if (totalPage == 0) {
			System.out.println(" 등록된 상품이 없습니다.");
		}
		// 페이지의 목록 출력
		List<Product> list = pDAO.selectAll(currentPage);
		for (Product info : list) {
			System.out.println(info);
		}
		// 페이지 블록 + 현재 페이지 프린트
		System.out.print("\n [ ");
		for (int i = 1; i <= totalPage; i++) {
			System.out.print(i + " ");
		}
		System.out.println("] " + currentPage + "페이지");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++");

	}

	// 회원정보 출력
	private void memberInfo() {
		System.out.println("\n++++++++++++++++++회원정보+++++++++++++++++\n");
		// 기본 회원 정보
		Member member = mDAO.selectOne(LoginControl.getLoginInof().getMemberId());
		System.out.println(member);
		System.out.println("\n------------------주문내역-----------------\n");
		// 주문내역
		int currentPage = 1;
		while (true) {
			int totalPage = (int) Math.ceil(oDAO.selectAmount(member.getMemberId()) / 5.0);
			// 상품출력
			ordererPagePrint(currentPage, totalPage);

			int menuNo = 0;
			System.out.println("1.이전페이지 2.다음페이지 9.뒤로가기");
			menuNo = menuSelect();

			if (menuNo == 1) {
				if (currentPage == 1) {
					System.out.println("첫번째 페이지입니다.");
				} else {
					currentPage--;
				}
			} else if (menuNo == 2) {
				if (currentPage == totalPage) {
					System.out.println("마지막 페이지입니다.");
				} else {
					currentPage++;
				}
			} else if (menuNo == 9) {
				// 뒤로가기
				break;
			} else {
				// 입력오류
				showInputError();
			}
		}

	}

	// 개인 주문 내역 - 페이징
		private void ordererPagePrint(int currentPage, int totalPage) {

			if (totalPage == 0) {
				System.out.println(" 주문 내역이 없습니다.");
			} else {
				List<Order> list = oDAO.selectAll(currentPage, LoginControl.getLoginInof().getMemberId());
				System.out.println(" (○:배송준비중, ●:발송완료)");
				for (Order info : list) {
					System.out.println("\n " + info);
				}
			}

			// 페이지 블록 + 현재 페이지 프린트
			System.out.print("\n [ ");
			for (int i = 1; i <= totalPage; i++) {
				System.out.print(i + " ");
			}
			System.out.println("] " + currentPage + "페이지");
			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++");

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

	// 상품등록
	private void insertProduct() {
		Product product = new Product();
		try {
			System.out.print("제품명 > ");
			product.setProductName(sc.nextLine());
			System.out.print("가격 > ");
			product.setProductPrice(Integer.parseInt(sc.nextLine()));
			System.out.print("제품 설명 > ");
			product.setProductExplain(sc.nextLine());

			pDAO.insert(product);
		} catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}

	// 상품정보 수정
	private void updatdProduct() {
		try {
			System.out.print("제품 번호 선택 > ");
			int num = Integer.parseInt(sc.nextLine());
			Product product = pDAO.selectOne(num);
			if (product == null) {
				System.out.println("존재하지 않는 제품입니다.");
				return;
			}
			System.out.println("현재 상품명 : " + product.getProductName());
			System.out.print("수정할 이름 (수정 하지 않을 경우 0 입력) > ");
			String str = sc.nextLine();
			if (!str.equals("0")) {
				product.setProductName(str);
			}
			System.out.println("현재 가격 : " + product.getProductPrice());
			System.out.print("수정할 가격 (수정 하지 않을 경우 0 입력) > ");
			int price = Integer.parseInt(sc.nextLine());
			if (price != 0) {
				product.setProductPrice(price);
			}
			System.out.println("현재 상품설명 : " + product.getProductExplain());
			System.out.print("수정할 설명 (수정 하지 않을 경우 0 입력) > ");
			str = sc.nextLine();
			if (!str.equals("0")) {
				product.setProductExplain(str);
			}
			pDAO.updateInfo(product);
		} catch (NumberFormatException e) {
			System.out.println("수정할 제품의 번호를 입력하세요.");
		}
	}

	// 상품삭제
	private void deleteProduct() {
		try {
			System.out.print("제품 번호 선택 > ");
			int num = Integer.parseInt(sc.nextLine());
			Product product = pDAO.selectOne(num);
			if (product == null) {
				System.out.println("존재하지 않는 제품입니다.");
				return;
			}
			if (oDAO.selectAmount(num) > 0) {
				System.out.println("주문 내역이 있는 상품은 삭제할 수 없습니다.");
				return;
			}
			System.out.println(product.getProductName() + "를 정말 삭제하시겠습니까? 1.YES 2.NO");
			int menu = menuSelect();
			if (menu == 1)
				pDAO.delete(num);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
	}

}
