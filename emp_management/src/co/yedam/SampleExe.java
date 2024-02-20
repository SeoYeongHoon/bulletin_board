package co.yedam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SampleExe {
	
	// 등록
	static void insertEmp(Connection conn) throws SQLException {
		String sql = "INSERT INTO employee (emp_no,"
				   + "                      emp_name,"
				   + "                      email,"
				   + "                      phone,"
				   + "                      salary,"
				   + "                      department)"
				   + "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setInt(1, 778);
		psmt.setString(2, "서길동");
		psmt.setString(3, "seo@email.com");
		psmt.setString(4, "335-4163");
		psmt.setInt(5, 500);
		psmt.setString(6, "법무");
		
		int r = psmt.executeUpdate();
		// insert, update, delete 실행 메소드
		
		System.out.println("처리된 건수: " + r);
		if (r > 0) {
			conn.commit();
			System.out.println("정상적으로 입력되었습니다.");
		} else {
			conn.rollback();
		}
	}

	public static void main(String[] args) {
		// Connection 객체 가져오기
		Connection conn = DAO.getConn();
		
		// 쿼리
		String dept = "인사"; // SELECT * FROM employee WHERE department = '인사';
		String sql = "SELECT * FROM employee WHERE department = ? AND emp_no = ? ";
		// ?: 숫자열이면 숫자열 반환, 문자열이면 문자열 반환

		// 쿼리 해석, 처리된 결과를 반환
		try {
			conn.setAutoCommit(false); // 자동 커밋 해제
			insertEmp(conn);
			
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, dept);
			psmt.setInt(2, 101);
			ResultSet rs = psmt.executeQuery(); // 조회
			System.out.println("사원번호     사원명");
			System.out.println("===============");
			while(rs.next()) {
				System.out.println("사원번호: " + rs.getInt("emp_no") + 
						" 사원명: " + rs.getString("emp_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("===============");
	}

}
