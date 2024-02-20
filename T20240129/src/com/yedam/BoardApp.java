package com.yedam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BoardApp {
	Scanner sc = new Scanner(System.in);
	private File dataFile = new File("c:/temp/boardList.dat");

	List<Board> storage = new ArrayList<>();

	private static BoardApp instance;
	SimpleDateFormat sdf = new SimpleDateFormat("yy년 MM월 dd일 HH시 mm분 ss초");

	private BoardApp() {
		readFromFile();
	}


	public static BoardApp getInstance() {
		if (instance == null) {
			instance = new BoardApp();
		}
		return instance;
	}

	// 추가
	public void register() {
		System.out.println("====== 게시글 작성 ======");
		System.out.println("게시글 번호 >>> ");
		String num = sc.nextLine();
		System.out.println("제목 >>> ");
		String title = sc.nextLine();
		System.out.println("내용 >>> ");
		String content = sc.nextLine();
		System.out.println("작성자 >>> ");
		String user = sc.nextLine();

		Date date = new Date();

		Board board = null;
		board = new Board(num, title, content, user, date);
		
		if (storage.add(board)) {
			System.out.println("게시글 등록 완료");
		} else {
			System.out.println("등록 실패");
		}
	}

	// 수정
	public void modify() {
		System.out.println("수정할 게시글 번호 >>> ");
		String num = sc.nextLine();
		
		Iterator<Board> iter = storage.iterator();
		while (iter.hasNext()) {
			Board board = iter.next();

			if (board.getNum().equals(num)) {
				System.out.println("게시글을 수정 >>> ");
				String modContext = sc.nextLine();
				board.setContent(modContext);
				board.setDate(new Date());
				return;
			}
		}
		System.out.println("검색한 게시글이 없음");
	}

	// 단건 조회
	public void search() {
		System.out.println("검색할 게시글 번호 >>> ");
		String num = sc.nextLine();

		Iterator<Board> iter = storage.iterator();
		while (iter.hasNext()) {
			Board board = iter.next();

			if (board.getNum().equals(num)) {
				board.showDetail();
				return;
			}
		}
		System.out.println("검색한 게시글이 없음");
	}

	// 삭제
	public void remove() {
		System.out.println("삭제할 게시글 번호 >>> ");
		String num = sc.nextLine();

		Iterator<Board> iter = storage.iterator();
		while (iter.hasNext()) {
			Board board = iter.next();

			if (board.getNum().equals(num)) {
				iter.remove();
				System.out.println("삭제완료");
				return;
			}
		}
		System.out.println("삭제할 게시글이 없음");
	}

	// 목록
	public void list() {
		System.out.println("====== 게시글 목록 ======");
		for (Board board : storage) {
			board.showList();
		}
	}

	// 초기화
	public void readFromFile() {
		if (dataFile.exists() == false)
			return;

		try {
			FileReader fr = new FileReader(dataFile);
			BufferedReader br = new BufferedReader(fr);
			String str = "";

			while ((str = br.readLine()) != null) {
				String[] boards = str.split(",");
				storage.add(new Board(
						boards[0], 
						boards[1], 
						boards[2], 
						boards[3],
						sdf.parse(boards[4])));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 저장
	public void storeToFile() {
		try {
			FileWriter fw = new FileWriter(dataFile);
			Iterator<Board> iter = storage.iterator();

			while (iter.hasNext()) {
				Board board = iter.next();

				// toString 활용
				fw.write(board.toString() + "\n");
			}
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
