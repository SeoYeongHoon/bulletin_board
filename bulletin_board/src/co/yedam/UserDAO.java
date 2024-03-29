package co.yedam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Connection conn;

	// 로그인 중인 사용자의 id, pw을 DB에 저장
	public void insertUser(String id, String pw) {
		conn = DAO.getConn();

		try {
			String sql = "INSERT INTO login_who VALUES(?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.executeUpdate();
			System.out.println("로그인 유저 저장 성공");
		} catch (SQLException e) {
			System.out.println("로그인 유저 저장 실패");
		} finally {
			try {
				pstmt.close();
//				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 접속중인 사용자의 id를 출력
	public String selectLoginId() {
		conn = DAO.getConn();
		String connId = null;

		try {
			stmt = conn.createStatement();
			String sql = "SELECT id FROM login_who";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String id = rs.getString(1);
				connId = id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connId;
	}

	// 접속중인 사용자의 pw를 출력
	public String selectLoginPw() {
		conn = DAO.getConn();
		String connPw = null;

		try {
			stmt = conn.createStatement();
			String sql = "SELECT pw FROM login_who";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String pw = rs.getString(1);
				connPw = pw;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connPw;
	}
	
	// 접속중인 사용자의 이름 출력
	public String selectLoginName() {
		conn = DAO.getConn();
		String connName = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT u.name FROM login_who l JOIN users u ON l.id = u.id";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String name = rs.getString(1);
				connName = name;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connName;
	}
	
	// 접속중인 사용자의 연락처 출력
	public String selectLoginPhone() {
		conn = DAO.getConn();
		String connPhone = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT phone FROM users u JOIN login_who l ON u.id = l.id";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String phone = rs.getString(1);
				connPhone = phone;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connPhone;
	}
	
	// 가입된 계정 리스트
	public void users() {
		conn = DAO.getConn();
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT id FROM login";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String user = rs.getString(1);
				System.out.println(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 로그아웃 또는 종료 시 DB에서 삭제(초기화)
	public void deleteLogoutId() {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM login_who";
			stmt.executeUpdate(sql);
//			System.out.println("현재 로그인중인 유저 초기화");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
