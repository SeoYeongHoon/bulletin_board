package co.yedam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDAO {
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Connection conn;

	// 로그인
	public boolean loginSelect(String id, String pw) {
		boolean success = false;
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM users WHERE id='" + id + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				// 비밀번호 맞을 시
				if (pw.equals(rs.getString(2))) {
					System.out.println("<<< 로그인 성공 >>>");
					success = true;
				} else {
					// 비밀번호 틀릴 시
					System.out.println("!!!! 비밀번호가 다릅니다 !!!!");
				}
			}
		} catch (SQLException e) {
			// 존재하지 않는 데이터
			System.out.println("존재하지 않는 계정입니다.");
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	// 회원가입 시 id, pw를 DB(users 테이블)에 저장
	public void insert(String id, String pw, String name, String phone) {
		conn = DAO.getConn();

		try {
			String sql = "INSERT INTO users VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, phone);
			pstmt.executeUpdate();
			System.out.println("<<< 회원가입 성공 >>>");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("!!!! 회원가입 실패 !!!!");
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 비밀번호 변경
	public void updatePw(String id, String pw) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "UPDATE users SET pw='" + pw + "' WHERE id='" + id + "'";
			stmt.executeUpdate(sql);
			System.out.println("<<< 비밀번호 변경 완료 >>>");
		} catch (SQLException e) {
			System.out.println("!!!! 변경 실패 !!!!");
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 회원탈퇴
	public void delete(String id, String pw) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM users WHERE id='" + id + "' AND pw='" + pw + "'";
			stmt.executeUpdate(sql);
			System.out.println("<<< 회원 탈퇴 완료 >>>");
		} catch (SQLException e) {
			System.out.println("!!!! 탈퇴 실패 !!!!");
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 아이디 찾기
	public void searchId(String name, String phone) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "SELECT id FROM users WHERE name='" + name + "' AND phone='" + phone + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String id = rs.getString(1);
				System.out.println("<<< 아이디는 " + id + " 입니다 >>>");
				try {
					stmt.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
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
		System.out.println("!!!! 일치하는 정보가 없습니다 !!!!");
	}

	// 비밀번호 찾기
	public void searchPw(String id, String name, String phone) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "SELECT pw FROM users WHERE id='" + id + "' AND name='" + name + "' AND phone='" + phone + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String pw = rs.getString(1);
				System.out.println("<<< 비밀번호는 " + pw + " 입니다 >>>");
				try {
					stmt.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
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
		System.out.println("!!!! 일치하는 정보가 없습니다 !!!!");
	}

}
