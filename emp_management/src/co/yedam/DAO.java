package co.yedam;

import java.sql.Connection;
import java.sql.DriverManager;

// Connection 객체: DB연결
public class DAO {
	public static Connection conn;
	
	public static Connection getConn() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "yedam", "yedam");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
