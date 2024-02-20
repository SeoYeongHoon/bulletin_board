package com.yedam;

import java.util.Scanner;

public class EmpApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		boolean run = true;
		EmpDAO empApp = new EmpDAO();

		// while start
		while (run) {
			System.out.println("1.등록  2.목록  3.수정(급여)  4.삭제  5.조회(조건:입사일자)  6.종료");

			// menu select
			int menu = Integer.parseInt(sc.nextLine());

			switch (menu) {

			// 등록
			case 1:
				Employee emp = null;
				emp = new Employee();

				System.out.println("사번입력>> ");
				String empNum = sc.nextLine();
				emp.setEmpNum(empNum);

				System.out.println("이름입력>> ");
				String empName = sc.nextLine();
				emp.setEmpName(empName);

				System.out.println("전화번호입력>> ");
				String empPhone = sc.nextLine();
				emp.setEmpPhone(empPhone);

				System.out.println("입사일입력>> ");
				String empJoin = sc.nextLine();
				emp.setEmpJoin(empJoin);

				System.out.println("급여입력>> ");
				String empSal = sc.nextLine();
				emp.setEmpSal(empSal);

				if (empApp.addEmp(emp)) {
					System.out.println("<<< 등록 완료 >>>");
				} else {
					System.out.println("!!! 등록 실패 !!!");
				}
				break;

			// 목록
			case 2:
				System.out.println("사번   이름   전화번호");
				Employee[] empList = empApp.empList();
				for (int i = 0; i < empList.length; i++) {
					if (empList[i] != null) {
						empList[i].showInfo();
					}
				}
				break;

			// 수정
			case 3:
				System.out.println("사번 >> ");
				empNum = sc.nextLine();

				Employee modEmp = empApp.getNum(empNum);
				String info = "";

				if (modEmp instanceof Employee) {
					// 수정할 급여
					System.out.println("급여 >> ");
					info = sc.nextLine();
				}

				if (empApp.modEmp(empNum, info)) {
					System.out.println("<<< 수정 완료 >>>");
				} else {
					System.out.println("<<< 수정 실패 >>>");
				}
				break;

			// 삭제
			case 4:
				System.out.println("사번 >> ");
				empNum = sc.nextLine();

				if (empApp.removeEmp(empNum)) {
					System.out.println("<<< 삭제 완료 >>>");
				} else {
					System.out.println("<<< 삭제 실패 >>>");
				}
				break;

			// 입사일로 조회
			case 5:
				System.out.println("입사일자>> ");
				String empJoinDate = sc.nextLine();
				Employee empIdx = empApp.getJoin(empJoinDate);

				if (empIdx != null) {
					System.out.println("사번   이름   입사일자");
					empIdx.checkInfo();
				} else {
					System.out.println("잘못된 입사일 입니다.");
				}
				break;

			// 종료
			case 6:
				run = false;
				System.out.println("<<< 종료 >>>");
				break;
			}
		}
		// while end
		System.out.println("<<< END of PROGRAM >>>");
	}

}
