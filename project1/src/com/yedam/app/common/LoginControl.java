package com.yedam.app.common;

import java.util.Scanner;

import com.yedam.app.member.Member;
import com.yedam.app.member.MemberDAO;
import com.yedam.app.member.SignUp;


public class LoginControl extends Management{
		//필드 - 종료 전까지 로그인 정보를 저장
		private static Member loginInfo = null	;
		public static Member getLoginInof() {	
			return loginInfo;					
		}	
		private Scanner sc = new Scanner(System.in);
		
		//생성자
		public LoginControl() {
			
			while(true) {
				menuPrint();
				int menuNo = menuSelect();
				if(menuNo==1) {
					//로그인
					login();
					//성공할 경우 프로그램 실행
					if(selectRole()!=2) break;
				}else if(menuNo==2) {
					//회원가입
					new SignUp();
				}else if(menuNo==9) {
					//종료
					new Management().run();
				}else {
					showInputError();
				}
				System.out.println();
			}
		}

		//메소드
		@Override
		protected void menuPrint() {
			System.out.println("**********************");
			System.out.println(" 1.로그인 2.회원가입 9.홈");	//로그인 하지 않고 이용하는 서비스 있을시 여기 추가
			System.out.println("**********************");
		}
		
		//로그인
		private void login() {
			//아이디와 비밀번호 입력
			Member inputInfo = inputMember();
			//로그인 시도
			loginInfo = MemberDAO.getInstance().selectOne(inputInfo);	
			//실패할 경우 그대로 종료
			if(loginInfo ==null) {
				System.out.println();
				return;
			}
			System.out.println("-로그인 성공-");
		}
		

		private Member inputMember() {
			Member info = new Member();
			
			System.out.print("아이디 > ");
			info.setMemberId(sc.nextLine());
			System.out.print("비밀번호 > ");
			info.setMemberPwd(sc.nextLine());
			
			return info;
		}
}
