package com.yedam.app.member;

import java.util.List;

import com.yedam.app.common.LoginControl;
import com.yedam.app.common.Management;
import com.yedam.app.order.OrderInfo;
import com.yedam.app.product.ProductDAO;

public class MyPage extends Management{

	protected MemberDAO mDAO = MemberDAO.getInstance();
	protected ProductDAO pDAO = ProductDAO.getInstance();
	
	public MyPage() {
		
		menuPrint();
		int menu = menuSelect();
		if(menu==1) {
			memberInfo();
			
		}else if(menu==2) {
			orderInfo();
		}else if(menu==9) {
			break;
		}else {
			showInputError();
		}
	}
	
	private void orderInfo() {
		if(selectRole()==0) {
			List<OrderInfo> list = oDAO.selectAll();
			//주문 리스트 출력
			System.out.println("*******************************");
			for(OrderInfo info : list) {
				System.out.println(info);
			}
			System.out.println("*******************************");
			System.out.println("9. 뒤로가기");
			int num = menuSelect();
			
		}else {
			String memberId = LoginControl.getLoginInof().getMemberId();
			System.out.println(oDAO.selectAll(memberId));
		}
		
	}


	private void memberInfo() {
		if(selectRole()==0) {
			allMemberPrint();
			System.out.println("9. 뒤로가기");
			int num = menuSelect();
			
		}else {
			Member member = mDAO.getInstance().selectOne(LoginControl.getLoginInof().getMemberId());
			System.out.println(member);
		}
	}

	private void allMemberPrint() {
		List<Member> list = mDAO.selectAll();
		int n=0;
		//회원 리스트 출력
		System.out.println("*******************************");
		for(Member info : list) {
			System.out.println(n+". "+info.getMemberId()
							+" 주문 건수 :\t"+oDAO.selectAmount(info.getMemberId()));
			n++;
		}
		System.out.println("*******************************");
	}

	@Override
	protected void menuPrint() {
	
			System.out.println("1.회원정보 2.주문내역확인 3.홈");
		
	}
}
