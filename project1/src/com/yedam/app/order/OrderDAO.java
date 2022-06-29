package com.yedam.app.order;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.yedam.app.common.DAO;
import com.yedam.app.product.Product;

public class OrderDAO extends DAO {

	public static OrderDAO tDAO = null;

	private OrderDAO() {
	}

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
			String sql = "INSERT INTO orders " + "(order_num,product_id, deal_amount, orderer_id, order_price) "
					+ "VALUES (order_seq.nextval, ?, ?, ?, ?) ";
			// 입력안해도 무관
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info.getProductId());
			pstmt.setInt(2, info.getDealAmount());
			pstmt.setString(3, info.getOrdererId());
			pstmt.setInt(4, info.getOrderPrice());

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

	// 수정 - 발송상태
	public void update(int orderNum, int condition) {
		try {
			connect();

			String sql = "UPDATE orders " + "SET condition =? " + "WHERE order_num=?";
			// 둘 다 값을 넣어야하기 때문에 바꾸지 않는다면 원래 값을 그대로 반환하도록 짤 것

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, condition);
			pstmt.setInt(2, orderNum);

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

	// 단건조회 - 주문번호
	public Order selectOne(int orderNum) {
		Order order = null;
		try {
			connect();
			String sql = "SELECT * FROM orders " + "WHERE order_num=" + orderNum;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				order = new Order();
				order.setOrderNum(rs.getInt("order_num"));
				order.setDealDate(rs.getDate("deal_date"));
				order.setOrdererId(rs.getString("orderer_id"));
				order.setProductId(rs.getInt("product_id"));
				order.setDealAmount(rs.getInt("deal_amount"));
				order.setShipmentDate(rs.getDate("shipment_date"));
				order.setCondition(rs.getInt("condition"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return order;
	}

	// 단건조회 - 상품 주문수량 확인
	public int selectAmount(int productId) {
		int amount = 0;
		try {
			connect();
			String sql = "SELECT NVL(SUM(deal_amount),0) as sum FROM orders " 
						+ "WHERE product_id = " + productId;
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

	// 단건조회 - 주문 건수 확인
	public int selectAmount(String memberId) {
		int amount = 0;
		try {
			connect();
			String sql = "SELECT COUNT(*) as sum FROM orders " 
						+ "WHERE orderer_id = '" + memberId + "'";
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

	// 전체조회 - 현재까지 출고된 내역 (페이징)
	public List<Order> selectAll(int page) {
		List<Order> list = new ArrayList<>();
		int start = 1 + (page - 1) * 5; // 1, 6, 11, 16...
		int end = 5 * page; // 5, 10, 15,...

		try {
			connect();
			String sql = "SELECT * FROM (" 
					+ "SELECT ROWNUM NUM, N.* FROM("
					+ "SELECT o.order_num, o.deal_date, o.product_id" + ", p.product_name, o.orderer_id, o.deal_amount"
					+ ", o.order_price, o.shipment_date, o.condition " + "FROM products p JOIN orders o "
					+ "ON p.product_id = o.product_id " + "ORDER BY o.deal_date DESC) N)" + "WHERE NUM BETWEEN ? AND ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order info = new Order();
				info.setOrderNum(rs.getInt("order_num"));
				info.setDealDate(rs.getDate("deal_date"));
				info.setProductId(rs.getInt("product_id"));
				info.setProductName(rs.getString("product_name"));
				info.setOrdererId(rs.getString("orderer_id"));
				info.setDealAmount(rs.getInt("deal_amount"));
				info.setOrderPrice(rs.getInt("order_price"));
				info.setShipmentDate(rs.getDate("shipment_date"));
				info.setCondition(rs.getInt("condition"));

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
			String sql = "SELECT o.order_num, o.deal_date, o.product_id" 
					+ ", p.product_name, o.orderer_id, o.deal_amount"
					+ ", o.order_price, o.shipment_date " 
					+ "FROM products p JOIN orders o "
					+ "ON p.product_id = o.product_id " 
					+ "WHERE o.orderer_id = ? " 
					+ "ORDER BY o.deal_date DESC";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order info = new Order();
				info.setOrderNum(rs.getInt("order_num"));
				info.setDealDate(rs.getDate("deal_date"));
				info.setProductId(rs.getInt("product_id"));
				info.setProductName(rs.getString("product_name"));
				info.setOrdererId(rs.getString("orderer_id"));
				info.setDealAmount(rs.getInt("deal_amount"));
				info.setOrderPrice(rs.getInt("order_price"));
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

	// 전체조회 - 회원아이디 (페이징)
	public List<Order> selectAll(int page, String memberId) {
		List<Order> list = new ArrayList<>();
		int start = 1 + (page - 1) * 5; // 1, 6, 11, 16...
		int end = 5 * page; // 5, 10, 15,...

		try {
			connect();
			String sql = "SELECT * FROM (" + "SELECT ROWNUM NUM, N.* FROM("
					+ "SELECT o.order_num, o.deal_date, o.product_id" + ", p.product_name, o.orderer_id, o.deal_amount"
					+ ", o.order_price, o.shipment_date " + "FROM products p JOIN orders o "
					+ "ON p.product_id = o.product_id " + "WHERE o.orderer_id = ? " + "ORDER BY o.deal_date DESC) N)"
					+ "WHERE NUM BETWEEN ? AND ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order info = new Order();
				info.setOrderNum(rs.getInt("order_num"));
				info.setDealDate(rs.getDate("deal_date"));
				info.setProductId(rs.getInt("product_id"));
				info.setProductName(rs.getString("product_name"));
				info.setOrdererId(rs.getString("orderer_id"));
				info.setDealAmount(rs.getInt("deal_amount"));
				info.setOrderPrice(rs.getInt("order_price"));
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
			String sql = "SELECT o.order_num, o.deal_date, o.product_id, p.product_name"
					+ ", o.orderer_id, o.deal_amount, o.order_price, o.shipment_date "
					+ "FROM products p JOIN orders o " + "ON p.product_id = o.product_id " 
					+ "WHERE o.orderer_id = ? "
					+ "AND o.product_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, productId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order info = new Order();
				info.setOrderNum(rs.getInt("order_num"));
				info.setDealDate(rs.getDate("deal_date"));
				info.setProductId(rs.getInt("product_id"));
				info.setProductName(rs.getString("product_name"));
				info.setDealAmount(rs.getInt("deal_amount"));
				info.setOrderPrice(rs.getInt("order_price"));
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
