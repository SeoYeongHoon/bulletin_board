package com.yedam;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Board {
	private String num;
	private String title;
	private String content;
	private String user;
	private Date date = new Date();

	SimpleDateFormat sdf = new SimpleDateFormat("yy년 MM월 dd일 HH시 mm분 ss초");

	public void setContent(String content) {
		this.content = content;
	}

	public String getNum() {
		return num;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Board(String num, String title, String content, String user, Date date) {
		this.num = num;
		this.title = title;
		this.content = content;
		this.user = user;
		this.date = date;
	}
	
	public void showList() {
		System.out.println("게시글 번호: " + num);
		System.out.println("제목: " + title);
		System.out.println("작성자: " + user);
		System.out.println("작성날짜: " + sdf.format(date));
		System.out.println();
	}
	
	public void showDetail() {
		System.out.println("게시글 번호: " + num);
		System.out.println("제목: " + title);
		System.out.println("내용: " + content);
		System.out.println("작성자: " + user);
		System.out.println("작성날짜: " + sdf.format(date));
		System.out.println();
	}
	
	@Override
	public String toString() {
		return num + "," + title + "," + content + "," + user + "," + sdf.format(date);
	}
}
