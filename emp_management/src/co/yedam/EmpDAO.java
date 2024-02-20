package co.yedam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// 데이터베이스 처리 기능
public class EmpDAO {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql;
	
	void disconn() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 입력
	public boolean insertEmp(Employee emp) {
		conn = DAO.getConn(); // Connection 객체 생성
		
		// hire_date 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 쿼리
		sql = "INSERT INTO employee (emp_no," 
				+ "emp_name," 
				+ "email,"
				+ "phone," 
				+ "hire_date," 
				+ "salary,"
				+ "department)" 
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, emp.getEmpNo());
			psmt.setString(2, emp.getEmpName());
			psmt.setString(3, emp.getEmail());
			psmt.setString(4, emp.getPhone());
			psmt.setString(5, sdf.format(emp.getHireDate()));
			psmt.setInt(6, emp.getSalary());
			psmt.setString(7, emp.getDepartment());

			int r = psmt.executeUpdate(); // 데이터 반영
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 목록
	public List<Employee> getList(String dept, int page) {
		conn = DAO.getConn(); // Connection 객체 생성
		
		List<Employee> list = new ArrayList<>();
		
		sql = "select emp_no,\r\n"
				+ "       emp_name,\r\n"
				+ "       email,\r\n"
				+ "                     phone,\r\n"
				+ "                     hire_date,\r\n"
				+ "                     salary,\r\n"
				+ "                     department\r\n"
				+ "from (select rownum rn, a.*\r\n"
				+ "from (select *\r\n"
				+ "      from employee\r\n"
				+ "      where department = nvl(?, department)\r\n"
				+ "      order by emp_no) a ) \r\n"
				+ "where rn > (? - 1) * 5 and rn <= (?) * 5 ";

//		sql = "SELECT emp_no, "
//				+ "emp_name, "
//				+ "email, "
//				+ "phone, "
//				+ "hire_date, "
//				+ "salary, "
//				+ "department "
//				+ "FROM employee ";
//		if (!dept.equals("")) {
//			sql += " WHERE department = NVL(?, department) ";
//		}
//		
//		sql += "ORDER BY emp_no";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dept);
			psmt.setInt(2, page);
			psmt.setInt(3, page);
//			psmt.
			
			rs = psmt.executeQuery();

			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmpNo(rs.getInt("emp_no"));
				emp.setEmpName(rs.getString("emp_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhone(rs.getString("phone"));
				emp.setHireDate( rs.getDate("hire_date"));
				emp.setSalary(rs.getInt("salary"));
				emp.setDepartment(rs.getString("department"));
				
				list.add(emp); // DB -> Collection에 담기
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

		return list;
	}
	
	
	// 수정
	public boolean updateEmp(Employee emp) {
		conn = DAO.getConn();
		sql = "UPDATE employee "
				+ "SET department = ? ";
		if (!emp.getPhone().equals("")) {
			sql += ", phone = ? ";
		}
		sql += " WHERE emp_no = ? ";
		
		int p = 1;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(p++, emp.getDepartment());
			if (emp.getPhone() != null) {
				psmt.setString(p++, emp.getEmpName());
			}
			psmt.setInt(p++, emp.getEmpNo());
			
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return false;
	}
	
	
	// 삭제
	public boolean deleteEmp(Employee emp) {
		conn = DAO.getConn();
		sql = "DELETE FROM employee WHERE emp_no = ? ";

		int p = 1;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(p++, emp.getDepartment());
			if (emp.getPhone() != null) {
				psmt.setString(p++, emp.getEmpName());
			}
			psmt.setInt(p++, emp.getEmpNo());
			
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return false;
	}
	
	
	// 부서별 인원
	public int getTotalCnt(String dept) {
		conn = DAO.getConn();
		sql = "SELECT COUNT(*) FROM employee WHERE department = NVL(?, department)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dept);
			rs = psmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return -1;
	}
}