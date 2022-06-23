package com.yedam.app.common;

import java.util.Scanner;

import com.yedam.app.order.OrderDAO;
import com.yedam.app.product.ProductDAO;
import com.yedam.app.product.ProductInfoManagement;

public class Management {

	//필드
	protected Scanner sc = new Scanner(System.in); 
	protected ProductDAO pDAO = ProductDAO.getInstance();
	protected OrderDAO oDAO = OrderDAO.getInstance();	 
	
	public void run() {
		while(true) {
			menuPrint();
			int menuNo = menuSelect();
			
			if(menuNo== 1) {
				//1.전체상품
				new ProductInfoManagement();
			}else if(menuNo==2) {
				//2.공지사항
//				new BoardManagement();
			}else if(menuNo==3) {
				//3.로그인
				new LoginControl();
			}else if(menuNo==4) {
				//4.판매자정보
				shopInfoPrint();
			}else if(menuNo==9) {
				//9.종료
				exit();
				break;
			}else {
				//입력오류
				showInputError();
			}
			System.out.println();
		}
	}
	
//	protected boolean selectRole() {	//role이 두가지 이상일땐 불린X : 하위클래스에서 사용할 메소드
//		int memberRole = LoginControl.getLoginInof().getMemberRole();
//		if (memberRole == 0) {	//관리자 0
//			return true;
//		}else {					//회원 1
//			return false;
//		}
//	}

	//메소드 
	protected void menuPrint() {
		System.out.println("=========================================");
		System.out.println(" 1.전체상품 2.공지사항 3.로그인 4.판매자 정보 9.종료");
		System.out.println("=========================================");
	}
	
	protected int menuSelect() {
		System.out.print("선택 > ");
		int menuNo=0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
		return menuNo;
	}
	
	protected void exit() {
		System.out.println("프로그램을 종료합니다.");
	}
	
	protected void showInputError() {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}
	
	private void shopInfoPrint() {
		System.out.println("\n**바삭바삭*촉촉*딜리셔스*");
		System.out.println("맛있는 쿠키~ 사가쉐요~&\n");
		
	}

}
