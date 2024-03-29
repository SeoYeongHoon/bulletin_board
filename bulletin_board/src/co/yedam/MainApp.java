package co.yedam;

public class MainApp {

	boolean isContinue, isContinue2, isContinue3;
	static int select1;
	String id, pw;

	public static void main(String[] args) {
		MainApp main = new MainApp();
		Login login = new Login();
		User user = new User();
		UserDAO userDAO = new UserDAO();

		// 프로그램 실행할 때마다 로그인되어 있는 데이터 삭제
		userDAO.deleteLogoutId();
		main.isContinue = true;

		while (main.isContinue) {
			login.loginPage();

			switch (select1) {
			case 1: // 로그인
				login.login();
				break;

			case 2: // 회원가입
				user.register();
				break;

			case 3: // 아이디 찾기
				user.findId();
				break;

			case 4: // 비밀번호 찾기
				user.findPw();
				break;
			
			case 5: // 계정 리스트
				user.userList();
				break;

			case 9:
				System.out.println("<<< 프로그램 종료 >>>");
				main.isContinue = false;
				break;
			}
		}
	}

}
