package com.yedam.app.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {

	//Oracle DB 정보
		private String jdbcDriver="oracle.jdbc.driver.OracleDriver";
		private String oracleUrl="jdbc:oracle:thin:@localhost:1521:xe";
		private String connectedId="cyl";
		private String connectedPwd="cyl";
		
		//공통으로 사용되는 필드 (자식클래스에서 쓰기 위해 protected)
		protected Connection conn;
		protected Statement stmt;
		protected PreparedStatement pstmt;
		protected ResultSet rs;
		
		public DAO(){
//			dbConfig();	//DB정보저장 메소드
		}
		
		//DB에 접속하는 메소드
		public void connect() {
			try {
				Class.forName(jdbcDriver); //드라이버 로딩
				
				conn = DriverManager.getConnection(oracleUrl, connectedId, connectedPwd);
				
			}catch(ClassNotFoundException e) {	
				System.out.println("jdbc driver 로딩 실패");
			}catch(SQLException e) {
				System.out.println("DB 연결 실패");
			}
			
		}
		
		
//		//DB 정보를 properties에서 가져오는 메소드 
//		private void dbConfig() {
//			String resource = "config/db.properties";
//			Properties properties = new Properties(); 
//			
//			try {
//				String filePath = ClassLoader.getSystemClassLoader().getResource(resource).getPath();
//				properties.load(new FileInputStream(filePath)); 
//						
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			jdbcDriver = properties.getProperty("driver");
//			oracleUrl = properties.getProperty("url");
//			connectedId = properties.getProperty("id");
//			connectedPwd = properties.getProperty("pwd");
//		}
		
		//DB 접속을 해제하는 메소드
		public void disconnect() {
			try {
				if(rs != null) rs.close();	
				if(pstmt != null) pstmt.close();	
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			}catch(SQLException e) {	
				e.printStackTrace();	
			}
		}
}
