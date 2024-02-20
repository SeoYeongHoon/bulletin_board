package com.yedam;

import java.util.Scanner;

public class BoardMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		BoardApp app = BoardApp.getInstance();
		
		boolean run = true;
		
		while (run) {
			System.out.println("====== 게시판 ======");
			
			int menu = 0;
			
			while (true) {
				try {
					System.out.println("1.추가  2.수정  3.단건조회  4.삭제  5.목록조회  9.종료");
					menu = Integer.parseInt(sc.nextLine());
					break;
				} catch (NumberFormatException e) {
					System.out.println("숫자를 입력하세요");
				}
			}
			
			switch (menu) {
			case 1:
				app.register();
				break;
			case 2:
				app.modify();
				break;
			case 3:
				app.search();
				break;
			case 4:
				app.remove();
				break;
			case 5:
				app.list();
				break;
			case 9:
				System.out.println("종료");
				app.storeToFile();
				run = false;
			}
		}
	}

}
