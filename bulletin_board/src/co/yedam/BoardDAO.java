package co.yedam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// boards 테이블을 이용해서 기능 메소드 구현
public class BoardDAO {
	Scanner sc = new Scanner(System.in);
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Connection conn;
	UserDAO userDAO = new UserDAO();

	// 작성 시간(현재 시간) 메소드
	public String timeNow() {
		conn = DAO.getConn();
		String time = null;

		try {
			stmt = conn.createStatement();
			String sql = "SELECT TO_CHAR(SYSDATE, 'YY-MM-DD') FROM dual";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				time = rs.getString(1);
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
		return time;
	}

	// 글 작성 메소드
	public void write(String title, String content, String time, String id) {
		conn = DAO.getConn();

		id = userDAO.selectLoginId();

		try {
			String sql = "INSERT INTO boards VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, time);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
//			timeNow();
			System.out.println("<<< 게시글 작성 완료 >>>");
		} catch (SQLException e) {
			System.out.println("!!!! 게시글 작성 실패 !!!!");
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 내가 쓴 글 리스트
	public void myList() {
		conn = DAO.getConn();
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT title, "
					+ "          content, "
					+ "          time "
					+ "   FROM boards b JOIN login_who u ON (b.id = u.id)";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String myTitle = rs.getString(1);
				String myContent = rs.getString(2);
				String myTime = rs.getString(3);
				System.out.println(myTitle + " | " + myContent + " | " + myTime);
				System.out.println("-----------------------------------");
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
		return;
	}

	// 목록 리스트에 담기 및 페이징을 위한 로우넘버링
	public List<BoardElem> getList(int page) {
		conn = DAO.getConn();
		
		List<BoardElem> list = new ArrayList<>();
		
		String sql = "SELECT title, id, time \r\n"
				+ "   FROM (SELECT ROWNUM rn, a.*\r\n"
				+ "         FROM (SELECT *\r\n"
				+ "               FROM boards\r\n"
				+ "               ORDER BY id) a )\r\n"
				+ "   WHERE rn > (? - 1) * 5 AND rn <= (?) * 5 ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			pstmt.setInt(2, page);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardElem elem = new BoardElem();
				elem.setTitle(rs.getString("title"));
				elem.setId(rs.getString("id"));
				elem.setTime(rs.getString("time"));
				
				list.add(elem);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	// 글 목록 보여주는 메소드
	public void show() {
		int page = 1;

		while (true) {
			System.out.println("<<< 목록 " + page + "페이지 >>>");
			System.out.println("제목  |  작성자  |  작성일  ");
			System.out.println("===========================");
			
			List<BoardElem> list = getList(page);
			
			for (BoardElem elem : list) {
				System.out.println(elem.getTitle() + " | " + elem.getId() + " | " + elem.getTime());
			}
			
			int totalCnt = getTotalCnt();
			int lastPage = (int) Math.ceil(totalCnt / 5.0);
			
			for (int i = 1; i <= lastPage; i++) {
				System.out.printf("%3d", i);
			}
			System.out.println();
			System.out.println("페이지(뒤로가기는 -1 ) >> ");
			page = sc.nextInt();sc.nextLine();
			
			if (page == -1) {
				break;
			}
		}
	}

	// 글 목록 개수
	public int getTotalCnt() {
		conn = DAO.getConn();
		
		String sql = "SELECT COUNT(*) FROM boards";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return -1;
	}

	// 글의 작성자 확인 메소드(수정 및 삭제할 게시물의 작성자가 로그인한 유저와 동일한지 확인)
	public String writeId(String text) {
		conn = DAO.getConn();

		String id = null;

		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM boards WHERE title='" + text + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// id만 가져오기
				id = rs.getString(4);
			}
		} catch (SQLException e) {
			System.out.println("해당하는 게시글이 없습니다.");
		} finally {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	// 글 수정 메소드
	public void update(String orgTitle, String modTitle, String modContent, String time) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "UPDATE boards SET title = '" + modTitle + "', content = '" + modContent + "'"
					+ " WHERE title = '" + orgTitle + "'";
			
			conn.setAutoCommit(false);
			
			stmt.executeQuery(sql);

			System.out.println("<<< 글 수정 완료 >>>");
		} catch (SQLException e) {
			System.out.println("해당하는 게시글이 없습니다.");
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 글 삭제 메소드
	public void delete(String id, String title) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			
			// id와 제목 일치하면 삭제
			String sql = "DELETE FROM boards WHERE id='" + id + "' AND title ='" + title + "'";
			stmt.executeUpdate(sql);
			System.out.println("<<< 삭제 완료 >>>");
		} catch (SQLException e) {
			System.out.println("!!!! 삭제 실패 !!!!");
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 제목 검색 메소드. 키워드 검색 시 키워드가 들어간 제목 가져옴
	public void searchTitle(String word) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "SELECT title, content, id FROM boards WHERE title LIKE '%" + word + "%'";
			rs = stmt.executeQuery(sql);
			System.out.println("<<< 검색 결과 >>>");

			while (rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				String id = rs.getString(3);
				System.out.println("제목: " + title);
				System.out.println("내용: " + content);
				System.out.println("작성자: " + id);
				System.out.println("-----------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 내용 검색 메소드. 키워드 검색 시 키워드가 들어간 내용 가져옴
	public void searchContent(String word) {
		conn = DAO.getConn();

		try {
			stmt = conn.createStatement();
			String sql = "SELECT title, content, id FROM boards WHERE content LIKE '%" + word + "%'";
			rs = stmt.executeQuery(sql);
			System.out.println("<<< 검색 결과 >>>");
			while (rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				String id = rs.getString(3);
				System.out.println("제목: " + title);
				System.out.println("내용: " + content);
				System.out.println("작성자: " + id);
				System.out.println("-----------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
