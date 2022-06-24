package com.yedam.app.member;

import java.sql.Date;
import java.util.Scanner;

public class SignUp {

	protected MemberDAO mDAO = MemberDAO.getInstance();
	private Scanner sc = new Scanner(System.in);
	public SignUp() {

		System.out.println("\n정보를 입력해주십시오.");

		System.out.print("아이디 > ");
		String id = sc.nextLine();
		Member member = mDAO.selectOne(id);
		if (member != null) {
			System.out.println("아이디가 중복됩니다.");
			return;
		}
		
		member = new Member();
		member.setMemberId(id);
		
		System.out.print("비밀번호 > ");
		member.setMemberPwd(sc.nextLine());
		System.out.print("이름 > ");
		member.setMemberName(sc.nextLine());
		System.out.print("생년월일(YYYY-MM-DD)>>");
		member.setMemberBirth(Date.valueOf(sc.nextLine()));
		System.out.print("연락처 > ");
		member.setMemberPhone(sc.nextLine());
		System.out.print("주소 > ");
		member.setMemberAddr(sc.nextLine());
		
		mDAO.insert(member);
	}
}
