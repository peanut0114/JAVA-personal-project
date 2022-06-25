package com.yedam.app.comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

public class CommentDAO extends DAO {
	// 싱글톤
	public static CommentDAO cDAO = null;

	private CommentDAO() {
	}

	public static CommentDAO getInstance() {
		if (cDAO == null) {
			cDAO = new CommentDAO();
		}
		return cDAO;
	}

	// 입력
	public void insert(Comment comment) {
		try {
			connect();
			String sql = "INSERT INTO comments " + "VALUES (?,?,?,sysdate) ";
			// 입력안해도 무관
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getCommentBnum());
			pstmt.setString(2, comment.getCommentMid());
			pstmt.setString(3, comment.getCommentContent());

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
	
	// 특정 게시물의 대글 전체조회
	public List<Comment> selectAll(int boardNum) {
		List<Comment> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT comment_b_num, "
						+ "REPLACE(comment_m_id, SUBSTR(comment_m_id,2,3),'***') m_id, "
						+ "comment_content, comment_date "
						+ "FROM comments "
						+ "WHERE comment_b_num=" + boardNum
						+ " ORDER BY comment_date DESC";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentBnum(rs.getInt("comment_b_num"));
				comment.setCommentMid(rs.getString("m_id"));
				comment.setCommentContent(rs.getString("comment_content"));
				comment.setCommentDate(rs.getDate("comment_date"));

				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 아이디별 댓글 전체조회
	public List<Comment> selectAll(String memberId) {
		List<Comment> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM comments WHERE comment_m_id="+memberId 
						+ "ORDER BY comment_date DESC";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentBnum(rs.getInt("comment_b_num"));
				comment.setCommentMid(rs.getString("comment_m_id"));
				comment.setCommentContent(rs.getString("comment_content"));
				comment.setCommentDate(rs.getDate("comment_date"));

				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
}
