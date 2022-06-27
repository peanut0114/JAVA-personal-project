package com.yedam.app.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

public class MemberDAO extends DAO{

	//싱글톤
		private static MemberDAO mDAO = null;
		private MemberDAO() {}
		public static MemberDAO getInstance() {
			if(mDAO == null) {
				mDAO = new MemberDAO();
			}
			return mDAO;
		}
		
		//DRUD
		//회원가입
		public void insert(Member member) {
			try {
				connect();	
				String sql = "INSERT INTO members(member_id,member_pwd,member_name"
						+ ",member_birth,member_phone,member_addr,member_role)"
							+ "VALUES(?,?,?,?,?,?,1)";	
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberId());
				pstmt.setString(2, member.getMemberPwd());
				pstmt.setString(3, member.getMemberName());
				pstmt.setDate(4, member.getMemberBirth());
				pstmt.setString(5, member.getMemberPhone());
				pstmt.setString(6, member.getMemberAddr());
				
				int result = pstmt.executeUpdate();
				if(result>0) {
					System.out.println("등록되었습니다.");
				}else {
					System.out.println("※정상적으로 등록되지 않았습니다.※");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally{
				disconnect();
			}
		}
		
		//로그인
		public Member selectOne(Member member) {	
			Member loginInfo = null;
			try {
				connect();
				//로그인이 안 되는 이유가 id 때문인지 pwd 때문인지 구분
				String sql = "SELECT * FROM members WHERE member_id='"+member.getMemberId()+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				if(rs.next()) {	//id 존재
					//가져온 셀렉문의 pw와 입력받은(매개변수) pw 값이 같은지 확인
					if(rs.getString("member_pwd").equals(member.getMemberPwd())) {
						//비밀번호 일치 -> 로그인 성공
						loginInfo = new Member();	//일치시에만 정보입력 
						loginInfo.setMemberId(rs.getString("member_id"));
						loginInfo.setMemberPwd(rs.getString("member_pwd"));
						loginInfo.setMemberRole(rs.getInt("member_role"));
						
					}else {
						System.out.println("비밀번호가 일치하지 않습니다.");
					}
				}else {
					System.out.println("아이디가 존재하지 않습니다.");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				disconnect();
			}
			return loginInfo;
		}
		
		//회원정보 수정 - 비밀번호,주소, 연락처
		public void updateInfo(Member member) {
			try {
				connect();
				
				String sql = "UPDATE members "
							+ "SET member_phone=?, member_addr=? "
							+ ",member_pwd=?"
							+ "WHERE member_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberPhone());
				pstmt.setString(2, member.getMemberAddr());
				pstmt.setString(3, member.getMemberPwd());
				pstmt.setString(4, member.getMemberId());
				
				int result = pstmt.executeUpdate();
				if(result>0) {
					System.out.println(result+"건이 정상적으로 수정되었습니다.");
				}else {
					System.out.println("정상적으로 수정되지 않았습니다.");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				disconnect();
			}
		}
		
		//단건조회
		public Member selectOne(String memberId) {	
			Member member = null;
			try {
				connect();
				String sql = "SELECT * FROM members WHERE member_id =?";
							
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					member = new Member();
					member.setMemberId(rs.getString("member_id"));
					member.setMemberPwd(rs.getString("member_pwd"));
					member.setMemberName(rs.getString("member_name"));
					member.setMemberBirth(rs.getDate("member_birth"));
					member.setMemberPhone(rs.getString("member_phone"));
					member.setMemberAddr(rs.getString("member_addr"));
					member.setMemberRole(rs.getInt("member_role"));
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally{
				disconnect();
			}
			return member;
		}
		//전체조회
		public List<Member> selectAll(){
			List<Member> list = new ArrayList<>();
			
			try {
				connect();
				String sql = "SELECT * FROM members ORDER BY 1";
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					Member member = new Member();
					member.setMemberId(rs.getString("member_id"));
					member.setMemberPwd(rs.getString("member_pwd"));
					member.setMemberName(rs.getString("member_name"));
					member.setMemberBirth(rs.getDate("member_birth"));
					member.setMemberPhone(rs.getString("member_phone"));
					member.setMemberAddr(rs.getString("member_addr"));
					member.setMemberRole(rs.getInt("member_role"));
					
					list.add(member);	//리스트에 넣어야함!!
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				disconnect();
			}
			
			return list;
		}
		//회원정보 제거
		public void delete(Member member) {
			try {
				connect();
				String sql = "DELETE FROM members "
							+ "WHERE member_id='"+member.getMemberId()+"'";
				stmt = conn.createStatement();
				int result = stmt.executeUpdate(sql);
				if(result>0) {
					System.out.println(result+"건 삭제되었습니다.");
				}else {
					System.out.println("정상적으로 삭제되지 않았습니다.");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally{
				disconnect();
			}
			
		}
}
