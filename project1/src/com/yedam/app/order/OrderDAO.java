package com.yedam.app.order;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.yedam.app.common.DAO;

public class OrderDAO extends DAO{

	public static OrderDAO tDAO = null;
	private OrderDAO() {}
	public static OrderDAO getInstance() {
		if (tDAO == null) {
			tDAO = new OrderDAO();
		}
		return tDAO;
	}

	// 등록
	public void insert(Order info) {
		try {
			connect();
			String sql = "INSERT INTO orders "
						+ "(product_id, deal_amount, orderer_id, shipment_date) " 
						+ "VALUES (?, ?, ?, sysdate+5) "; 
																										// 입력안해도 무관
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info.getProductId());
			pstmt.setInt(2, info.getDealAmount());
			pstmt.setString(3, info.getOrdererId());

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

	// 단건조회 - 상품 주문수량 확인
	public int selectAmount(int productId) {
		int amount = 0;
		try {
			connect();
			String sql = "SELECT NVL(SUM(deal_amount),0) as sum " 
						+ "FROM orders " 
						+ "WHERE product_id = "+ productId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				amount = rs.getInt("sum");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return amount;
	}
	
	// 단건조회 - 주문수량 확인
	public int selectAmount(String memberId) {
		int amount = 0;
		try {
			connect();
			String sql = "SELECT NVL(SUM(deal_amount),0) as sum " 
						+ "FROM orders " 
						+ "WHERE orderer_id = '"+memberId+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				amount = rs.getInt("sum");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return amount;
	}
	
	// 전체조회 - 현재까지 출고된 내역
	public List<Order> selectAll() {
		List<Order> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT o.deal_date, o.product_id" 
							+ ", p.product_name, o.orderer_id, o.deal_amount"
							+ ", o.shipment_date "
						+ "FROM products p JOIN orders o " 
						+ "ON p.product_id = o.product_id "
						+ "ORDER BY o.deal_date";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order info = new Order();
				info.setDealDate(rs.getDate("deal_date"));
				info.setProductId(rs.getInt("product_id"));
				info.setProductName(rs.getString("product_name"));
				info.setOrdererId(rs.getString("orderer_id"));
				info.setDealAmount(rs.getInt("deal_amount"));
				info.setShipmentDate(rs.getDate("shipment_date"));

				list.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 전체조회 - 해당 날짜에 출고된 내역
	public List<Order> selectAll(Date dealDate) {
		List<Order> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT t.deal_date, t.product_id" 
							+ ", p.product_name, t.deal_amount "
						+ "FROM products p JOIN orders t " 
						+ "ON p.product_id = t.product_id "
						+ "WHERE deal_date = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, dealDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order info = new Order();
				info.setDealDate(rs.getDate("deal_date"));
				info.setProductId(rs.getInt("product_id"));
				info.setProductName(rs.getString("product_name"));
				info.setDealAmount(rs.getInt("deal_amount"));

				list.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	// 전체조회 - 회원아이디
		public List<Order> selectAll(String memberId) {
			List<Order> list = new ArrayList<>();

			try {
				connect();
				String sql = "SELECT o.deal_date, o.product_id" 
								+ ", p.product_name, o.orderer_id, o.deal_amount"
								+ ", o.shipment_date "
							+ "FROM products p JOIN orders o " 
							+ "ON p.product_id = o.product_id "
							+ "WHERE o.orderer_id = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,memberId);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Order info = new Order();
					info.setDealDate(rs.getDate("deal_date"));
					info.setProductId(rs.getInt("product_id"));
					info.setProductName(rs.getString("product_name"));
					info.setDealAmount(rs.getInt("deal_amount"));
					info.setOrdererId(rs.getString("orderer_id"));
					info.setShipmentDate(rs.getDate("shipment_date"));
					list.add(info);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return list;
		}
		// 전체조회 - 회원아이디, 상품번호 이용
		public List<Order> selectAll(String memberId, int productId) {
			List<Order> list = new ArrayList<>();

			try {
				connect();
				String sql = "SELECT o.deal_date, o.product_id, p.product_name" 
							+ ", o.orderer_id, o.deal_amount, o.shipment_date "
							+ "FROM products p JOIN orders o " 
							+ "ON p.product_id = o.product_id "
							+ "WHERE o.orderer_id = ? "
							+ "AND o.product_id = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,memberId);
				pstmt.setInt(2, productId);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Order info = new Order();
					info.setDealDate(rs.getDate("deal_date"));
					info.setProductId(rs.getInt("product_id"));
					info.setProductName(rs.getString("product_name"));
					info.setDealAmount(rs.getInt("deal_amount"));
					info.setOrdererId(rs.getString("orderer_id"));
					info.setShipmentDate(rs.getDate("shipment_date"));
					list.add(info);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return list;
		}
}
