package com.yedam.app.member;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	

public class Member {

	private String memberId;
	private String memberPwd;
	private String memberName;
	private Date memberBirth;
	private String memberPhone;
	private String memberAddr;
	private int memberRole; // 관리자0, 회원1
	private int memberOrderNum;

	@Override
	public String toString() {
		return " 이름 : " + memberName + "\n 아이디 : " + memberId 
				+ "\n 생년월일 : " + memberBirth 
				+ "\n 연락처 : " + memberPhone 
				+ "\n 주소 : " + memberAddr;
	}

}
