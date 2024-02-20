package com.yedam;

// 사원정보 선언) 사번, 이름, 전화번호, 입사일, 급여
public class Employee {
	private String empNum;
	private String empName;
	private String empPhone;
	private String empJoin;
	private String empSal;

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getEmpJoin() {
		return empJoin;
	}

	public void setEmpJoin(String empJoin) {
		this.empJoin = empJoin;
	}

	public String getEmpSal() {
		return empSal;
	}

	public void setEmpSal(String empSal) {
		this.empSal = empSal;
	}

	// 목록 출력
	public void showInfo() {
		System.out.println(empNum + " " + empName + " " + empPhone);
	}

	// 조회 출력
	public void checkInfo() {
		System.out.println(empNum + " " + empName + " " + empJoin);
	}

	// 급여 수정
	public void changeSal(String empSal) {
		this.empSal = empSal;
	}
}
