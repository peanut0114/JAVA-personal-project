package com.yedam.app.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

public class ProductDAO extends DAO {

	// 싱글톤
	private ProductDAO() {
	}

	public static ProductDAO productDAO = null;

	public static ProductDAO getInstance() {
		if (productDAO == null) {
			productDAO = new ProductDAO();
		}
		return productDAO;
	}

	// CRUD - 기본 : 등록/수정/삭제/단건조회/전체조회

	// 등록
	public void insert(Product product) {
		try {
			connect();
			String sql = "INSERT INTO products(product_id, product_name, product_price) "
					+ "VALUES(product_seq.nextval, ?, ?)"; // 줄바꿈시 공백주의
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getProductName());
			pstmt.setInt(2, product.getProductPrice());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("등록되었습니다.");
			} else {
				System.out.println("정상적으로 등록되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 수정 - 이름, 가격
	public void updateInfo(Product product) {
		try {
			connect();

			String sql = "UPDATE products " 
					+ "SET product_name=?, product_price=?, product_explain =? " 
					+ "WHERE product_id=?";
			// 둘 다 값을 넣어야하기 때문에 바꾸지 않는다면 원래 값을 그대로 반환하도록 짤 것

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getProductName());
			pstmt.setInt(2, product.getProductPrice());
			pstmt.setString(3, product.getProductExplain());
			pstmt.setInt(4, product.getProductId());
			

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

	// 삭제
	public void delete(int productId) {
		try {
			connect();
			String sql = "DELETE FROM products WHERE product_id=" + productId;
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

	// 단건조회 - 재고이름
	public Product selectOne(String productName) {
		Product product = null;
		try {
			connect();
			String sql = "SELECT * FROM products WHERE product_name =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductPrice(rs.getInt("product_price"));
				product.setProductExplain(rs.getString("product_explain"));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return product;
	}

	// 단건조회 - 재고이름
	public Product selectOne(int productId) {
		Product product = null;
		try {
			connect();
			String sql = "SELECT * FROM products WHERE product_id =?";
			// 이름은 중복이 가능 -> 한건만 조회되도록 쿼리문 주의해야함!(자바 코드가 단건조회용임)
			// 자바에서 컨트롤X 값을 들고오기만 함. 쿼리문에서 컨트롤해야함 (Developer에서 미리 돌려보자)
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();

			// 값이 있을때 {}안의 내용 실행
			// if를 쓰든 while을 쓰든 마지막 행으로 계속 덮어쓴다 (리스트가 아니기때문)
			if (rs.next()) {
				product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductPrice(rs.getInt("product_price"));
				product.setProductExplain(rs.getString("product_explain"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return product;
	}

	// 전체조회
	public List<Product> selectAll() {
		List<Product> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM products ORDER BY product_id";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductPrice(rs.getInt("product_price"));
				product.setProductExplain(rs.getString("product_explain"));

				list.add(product); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}
}
