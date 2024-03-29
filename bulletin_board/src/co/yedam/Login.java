package co.yedam;

import java.util.Scanner;

// 메인 페이지(로그인)
public class Login {
	Scanner sc = new Scanner(System.in);
	MainApp main = new MainApp();
	LoginDAO loginDao = new LoginDAO();
	UserDAO userDao = new UserDAO();
	BoardDAO boardDAO = new BoardDAO();
	Board board = new Board();
	User user = new User();

	public void loginPage() {
		main.isContinue = false;
		System.out.println("==================  메인페이지  ==================");
		System.out.println("1. 로그인  2. 회원가입  3. 아이디찾기  4. 비밀번호찾기  9. 종료");
		System.out.print("선택 >> ");
		MainApp.select1 = sc.nextInt();
	}

	public void login() {
		System.out.println("<<< 로그인 >>>");
		System.out.print("아이디 >> ");
		String id = sc.next();
		System.out.print("비밀번호 >> ");
		String pw = sc.next();
		if (loginDao.loginSelect(id, pw) == true) {
			userDao.insertUser(id, pw);
			main.isContinue2 = true;

			while (main.isContinue2) {
				System.out.println("============= " + userDao.selectLoginName() + "님 환영합니다.=============");
				System.out.println("1. 비밀번호 변경  2. 게시판  3. 내 정보  4. 회원 탈퇴  9. 로그아웃");
				System.out.print("선택 >> ");
				int select2 = sc.nextInt();

				switch (select2) {
				case 1: // 비밀번호 변경
					System.out.print("변경할 비밀번호 >> ");
					String modPw = sc.next(); // 변경할 비밀번호 입력
					loginDao.updatePw(id, modPw); // 비밀번호 업데이트
					main.isContinue2 = true; // 비밀번호 변경 후 로그인된 페이지로 이동
					break;

				case 2: // 게시글 페이지로 이동
					boolean isContinue3 = true;
					while (isContinue3) {
						System.out.println("==================  게시판  ==================");
						System.out.println("1. 글 작성  2. 내 게시글  3. 목록  4. 검색  5. 뒤로가기");
						System.out.print("선택 >> ");
						int select3 = sc.nextInt();
						sc.nextLine();

						switch (select3) {
						case 1: // 게시글 작성
							board.writing();
							break;
							
						case 2: // 내가 작성한 게시글
							board.checkMyList();
							break;

						case 3: // 게시글 목록
							boardDAO.show();
							break;

						case 4: // 게시글 검색
							board.searchStandard();
							break;

						case 5: // 로그인된 페이지로 이동
							System.out.println("<<< 회원정보 페이지로 이동합니다. >>>");
							isContinue3 = false;
							main.isContinue2 = true;
							break;
						}
					}
					break;
					
				case 3: // 내 정보
					user.info();
					break;

				case 4: // 회원 탈퇴
					user.quit();
					main.isContinue2 = false;
					break;

				case 9: // 로그아웃
					user.logout();
					main.isContinue2 = false;
					break;
				}
			}

		} else {
			System.out.println("!!!! 로그인 실패 !!!!");
			main.isContinue = true; // 메인 화면(로그인 화면)으로 이동
		}
	}
}
