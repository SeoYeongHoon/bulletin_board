package co.yedam;

import java.util.Scanner;

//DAO들로부터 기능을 가져와 실질적인 프로세스 메소드를 구현하는 클래스
public class User {
	LoginDAO loginDAO = new LoginDAO();
	UserDAO userDAO = new UserDAO();
	MainApp main = new MainApp();
	Scanner sc = new Scanner(System.in);

	// 회원 탈퇴
	public void quit() {
		String id = userDAO.selectLoginId();
		String pw = userDAO.selectLoginPw();
		System.out.print("본인 확인을 위한 비밀번호 입력 >> ");
		String conPw = sc.nextLine();

		if (pw.equals(conPw)) {
			loginDAO.delete(id, conPw);
		} else {
			System.out.println("!!!! 비밀번호 인증 실패 !!!!");
			main.isContinue2 = true; // 회원 화면으로 이동
		}
	}

	// 로그아웃
	public void logout() {
		userDAO.deleteLogoutId();
		System.out.println("<<< 로그아웃 완료 >>>");
	}

	// 내 정보
	public void info() {
		System.out.println(userDAO.selectLoginId() + "님의 정보");
		System.out.println("==========================");
		System.out.println("이름: " + userDAO.selectLoginName());
		System.out.println("연락처: " + userDAO.selectLoginPhone());
	}

	// 회원가입
	public void register() {
		System.out.print("사용할 아이디 >> ");
		String newId = sc.nextLine();
		System.out.print("사용할 비밀번호 >> ");
		String newPw = sc.nextLine();
		System.out.print("이름 >> ");
		String newName = sc.nextLine();
		System.out.print("연락처 >> ");
		String newPhone = sc.nextLine();

		loginDAO.insert(newId, newPw, newName, newPhone);
		main.isContinue = true; // 로그인 페이지로 이동
	}

	// 아이디 찾기
	public void findId() {
		System.out.print("이름 입력 >> ");
		String fId = sc.nextLine();
		System.out.print("연락처 입력 >> ");
		String fPhone = sc.nextLine();

		loginDAO.searchId(fId, fPhone);
		main.isContinue = true;
	}

	// 비밀번호 찾기
	public void findPw() {
		System.out.print("아이디 입력 >> ");
		String fId = sc.nextLine();
		System.out.print("이름 입력 >> ");
		String fName = sc.nextLine();
		System.out.print("연락처 입력 >> ");
		String fPhone = sc.nextLine();

		loginDAO.searchPw(fId, fName, fPhone);
		main.isContinue = true;
	}

	// 계정 리스트
	public void userList() {
		System.out.println("========= 가입되어 있는 계정 리스트 =========");
		userDAO.users();
	}
}
