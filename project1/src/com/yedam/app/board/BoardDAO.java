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
						+ "VALUES (board_seq.nextval,?,?,?,?) "; 
																										// 입력안해도 무관
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoardStar()); 
			pstmt.setString(2, board.getBoardSubject());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setInt(4, board.getBoardCategory());

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

	//전체조회
	public List<Board> selectAll() {
		List<Board> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM board ORDER BY 1 DESC";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Board board = new Board();
				board.setBoardNum(rs.getInt("board_num"));
				board.setBoardSubject(rs.getString("board_subject"));
				board.setBoardContent(rs.getString("board_content"));
				board.setBoardStar(rs.getInt("board_star"));
				board.setBoardCategory(rs.getInt("board_category"));
				
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
