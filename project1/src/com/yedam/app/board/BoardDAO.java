package com.yedam.app.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;
import com.yedam.app.order.OrderInfo;
import com.yedam.app.product.Product;

public class BoardDAO extends DAO{
	//싱글톤
	public static BoardDAO bDAO = null;
	private BoardDAO() {}
	public static BoardDAO getInstance() {
		if (bDAO == null) {
			bDAO = new BoardDAO();
		}
		return bDAO;
	}
	
	//입력
	public void insert(Board board) {
		try {
			connect();
			String sql = "INSERT INTO board "
						+ "VALUES (board_seq.nextval,?,?,?,?,?,?,?) "; 
																										// 입력안해도 무관
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getProductId());
			pstmt.setString(2, board.getBoardMId());
			pstmt.setString(3, board.getBoardPwd());
			pstmt.setInt(4, board.getBoardStar()); 
			pstmt.setString(5, board.getBoardSubject());
			pstmt.setString(6, board.getBoardContent());
			pstmt.setInt(7, board.getBoardCategory());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println(result + "건 등록되었습니다.");
			} else {
				System.out.println("정상적으로 등록되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	//수정 - 제목, 평점, 내용
	public void updateInfo(Board board) {
		try {
			connect();

			String sql = "UPDATE board " 
					+ "SET board_subject=?, board_content=?, board_star=? "
					+ "WHERE board_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getBoardSubject());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardStar());
			pstmt.setInt(4, board.getBoardNum());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println(result + "건이 정상적으로 수정되었습니다.");
			} else {
				System.out.println("정상적으로 수정되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	//삭제
	public void delete(int boardNum) {
		try {
			connect();
			String sql = "DELETE FROM board WHERE board_num=" + boardNum;
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println(result + "건 삭제되었습니다.");
			} else {
				System.out.println("정상적으로 삭제되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	//단건조회
	public Board selectOne(int boardNum) {
		Board board = new Board();
		try {
			connect();
			String sql = "SELECT b.board_num, p.product_name, b.board_subject, "
					+ "RPAD(SUBSTR('★★★★★',1,b.board_star),5,'☆') star, b.board_star, "
					+ "b.board_m_id, b.board_content, b.board_category "
				    + "FROM products p JOIN board b "
				    + "ON (p.product_id = b.board_product) "
				    + "WHERE board_num="+boardNum;
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				board.setBoardNum(rs.getInt("b.board_num"));
				board.setProductName(rs.getString("p.product_name"));
				board.setBoardSubject(rs.getString("b.board_m_id"));
				board.setStar(rs.getString("star"));
				board.setBoardStar(rs.getInt("b.board_star"));				
				board.setBoardMId(rs.getString("b.board_m_id"));
				board.setBoardContent(rs.getString("b.board_content"));
				board.setBoardCategory(rs.getInt("b.board_category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return board;
	}
	
	//전체조회
	public List<Board> selectAll() {
		List<Board> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT b.board_num num, p.product_name pname, b.board_subject s, "
					+ "RPAD(SUBSTR('★★★★★',1,b.board_star),5,'☆') star, b.board_m_id mid, b.board_content cont, b.board_category c "
				    + "FROM products p JOIN board b "
				    + "ON (p.product_id = b.board_product) "
				    + "ORDER BY b.board_num";
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Board board = new Board();
				board.setBoardNum(rs.getInt("num"));
				board.setProductName(rs.getString("pname"));
				board.setBoardSubject(rs.getString("s"));
				board.setStar(rs.getString("star"));
				board.setBoardMId(rs.getString("mid"));
				board.setBoardContent(rs.getString("cont"));
				board.setBoardCategory(rs.getInt("c"));
				
				list.add(board); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	//게시판별 전체조회
		public List<Board> selectAll(int category) {
			List<Board> list = new ArrayList<>();

			try {
				connect();
				String sql = "SELECT b.board_num num, p.product_name pname, b.board_subject s, "
						+ "RPAD(SUBSTR('★★★★★',1,b.board_star),5,'☆') star, b.board_m_id mid, b.board_content cont "
					    + "FROM products p JOIN board b "
					    + "ON (p.product_id = b.board_product) "
					    + "WHERE b.board_category="+category
					    + " ORDER BY b.board_num";
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Board board = new Board();
					board.setBoardNum(rs.getInt("num"));
					board.setProductName(rs.getString("pname"));
					board.setBoardSubject(rs.getString("s"));
					board.setStar(rs.getString("star"));
					board.setBoardMId(rs.getString("mid"));
					board.setBoardContent(rs.getString("cont"));
					
					list.add(board); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return list;
		}
}
