package co.yedam;

import java.util.Scanner;

// DAO들로부터 기능을 가져와 실질적인 프로세스 메소드를 구현하는 클래스
public class Board {
	BoardDAO boardDAO = new BoardDAO();
	UserDAO userDAO = new UserDAO();
	Scanner sc = new Scanner(System.in);
	MainApp main = new MainApp();

	// 글 작성 메소드
	public void writing() {
		System.out.print("제목 >> ");
		String title = sc.nextLine();
		System.out.print("내용 >> ");
		String content = sc.nextLine();
		String time = boardDAO.timeNow();
		String id = userDAO.selectLoginId();
		boardDAO.write(title, content, time, id);
	}

	// 내가 쓴 글 확인. => 게시글 수정 및 삭제
	public void checkMyList() {
		boolean run = true;

		while (run) {
			System.out.println("======== " + userDAO.selectLoginName() + "님이 작성한 글 리스트 ========");
			boardDAO.myList();

			System.out.println("1. 글 수정   2. 글 삭제   3. 뒤로가기");
			System.out.print("입력 >> ");
			int select = sc.nextInt();
			sc.nextLine();

			switch (select) {
			case 1:
				try {
					System.out.print("수정할 게시글의 제목 입력 >> ");
					String orgTitle = sc.nextLine();
					String orgId = boardDAO.writeId(orgTitle);
					String id = userDAO.selectLoginId();

					if (orgId.equals(id)) {
						System.out.print("수정할 제목 입력 >> ");
						String modTitle = sc.nextLine();
						System.out.print("수정할 내용 입력 >> ");
						String modContent = sc.nextLine();
						String modTime = boardDAO.timeNow();
						boardDAO.update(orgTitle, modTitle, modContent, modTime);
					}
				} catch (NullPointerException e) {
					System.out.println("해당하는 게시글이 없습니다.");
				}
				break;

			case 2: // 내가 쓴 게시글 삭제
				try {
					System.out.print("삭제할 게시글의 제목 입력 >> ");
					String delTitle = sc.nextLine();
					String delId = boardDAO.writeId(delTitle);
					String id = userDAO.selectLoginId();
					String pw = userDAO.selectLoginPw();

					if (id.equals(delId)) {
						System.out.print("본인 확인을 위한 비밀번호 입력 >> ");
						String delPw = sc.next();

						// 작성자의 비밀번호 확인
						if (pw.equals(delPw)) {
							boardDAO.delete(id, delTitle);
						} else {
							System.out.println("비밀번호가 올바르지 않습니다.");
						}
					} else {
						System.out.println("해당되지 않는 유저입니다.");
					}
				} catch (NullPointerException e) {
					System.out.println("해당하는 게시글이 없습니다.");
				}
				break;

			case 3:
				run = false;
				break;
			}
		}

	}

	// 게시글 검색 기준
	public void searchStandard() {
		System.out.println("1. 제목으로 검색   2. 내용으로 검색");
		System.out.print("선택 >> ");
		int select = sc.nextInt();
		sc.nextLine();
		System.out.print("검색어 입력 >> ");
		String word = sc.nextLine();
		if (select == 1) {
			boardDAO.searchTitle(word);
		} else if (select == 2) {
			boardDAO.searchContent(word);
		}
	}

}
