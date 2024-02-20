package com.yedam;

public class EmpDAO {
	private Employee[] emps;

	public EmpDAO() {
		emps = new Employee[10];
	}

	// 1.등록
	public boolean addEmp(Employee emp) {
		for (int i = 0; i < emps.length; i++) {
			if (emps[i] == null) {
				emps[i] = emp;
				return true;
			}
		}
		return false;
	}

	// 2.목록
	public Employee[] empList() {
		return emps;
	}

	// 사번으로 조회
	public Employee getNum(String empNum) {
		for (int i = 0; i < emps.length; i++) {
			if (emps[i] != null) {
				if (emps[i].getEmpNum().equals(empNum)) {
					return emps[i];
				}
			}
		}
		return null;
	}

	// 3.수정(급여)

	public boolean modEmp(String empNum, String empSal) {
		for (int i = 0; i < emps.length; i++) {
			if (emps[i] != null) {
				if (emps[i].getEmpNum().equals(empNum)) {
					emps[i].changeSal(empSal);
					return true;
				}
			}
		}
		return false;
	}

	// 4.삭제
	public boolean removeEmp(String empNum) {
		for (int i = 0; i < emps.length; i++) {
			if (emps[i] != null) {
				if (emps[i].getEmpNum().equals(empNum)) {
					emps[i] = null;
					return true;
				}
			}
		}
		return false;
	}

	// 5.조회(조건: 입사일자)
	public Employee getJoin(String empJoin) {
		for (int i = 0; i < emps.length; i++) {
			if (emps[i] != null) {
				if (emps[i].getEmpJoin().equals(empJoin)) {
					return emps[i];
				}
			}
		}
		return null;
	}

}