package co.yedam;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

// 사용자의 입출력 결과 반환
public class EmpApp {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		EmpDAO edao = new EmpDAO();
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		int menu = 0;
		
		while (run) {
			System.out.println("1.등록 2.목록 9.종료");
			System.out.println("선택 >> ");
			menu = sc.nextInt();
			sc.nextLine();
			
			if (menu == 1) {
				System.out.println("사원번호 >> ");
				int no = sc.nextInt();sc.nextLine();
				System.out.println("사원명 >> ");
				String name = sc.nextLine();
				System.out.println("이메일 >> ");
				String mail = sc.nextLine();
				System.out.println("연락처 >> ");
				String phone = sc.nextLine();
				System.out.println("입사일 >> ");
				String hire = sc.nextLine();
				System.out.println("급여 >> ");
				String sal = sc.nextLine();
				System.out.println("부서정보 >> ");
				String dept = sc.nextLine();
				
				try {
					Employee emp = new Employee(no, name, mail, phone, sdf.parse(hire), Integer.parseInt(sal), dept);
					
					if (edao.insertEmp(emp)) {
						System.out.println("정상적으로 등록되었습니다.");
					} else {
						System.out.println("====== 등록 실패 ======");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (menu == 2) {
				System.out.println("부서정보 >> ");
				String dept = sc.nextLine();
				
				int page = 1;
				
				while (true) {
					List<Employee> list = edao.getList(dept, page);
					for (Employee emp : list) {
						System.out.println(emp.getEmpNo() + " " + emp.getEmpName() + " " + emp.getPhone() + " " + emp.getDepartment());
					}
					
					int totalCnt = edao.getTotalCnt(dept);
					int lastPage = (int) Math.ceil(totalCnt / 5.0);
					for (int i = 1; i <= lastPage; i++) {
						System.out.printf("%3d", i);
					}
					System.out.println();
					System.out.println("페이지 >> ");
					page = sc.nextInt();sc.nextLine();
					
					if (page == -1) {
						break;
					}
				}
			} else if (menu == 3) {
				System.out.println("사원번호 >> ");
				String no = sc.nextLine();
				System.out.println("수정할 부서 >> ");
				String dept = sc.nextLine();
				System.out.println("수정할 연락처 >> ");
				String phone = sc.nextLine();
				
				Employee emp = new Employee();
				emp.setEmpNo(Integer.parseInt(no));
				emp.setDepartment(dept);
				emp.setPhone(phone);
				
				if (edao.updateEmp(emp)) {
					System.out.println("정상적으로 수정되었습니다.");
				} else {
					System.out.println("====== 수정 실패 ======");
				}
			} else if (menu == 4) {
				// 삭제: 사원번호로 처리 => deleteEmp(사원번호)
				System.out.println("삭제할 사원번호 >> ");
				String no = sc.nextLine();
				
				Employee emp = new Employee();
				emp.setEmpNo(Integer.parseInt(no));
				
				if (edao.deleteEmp(emp)) {
					System.out.println("정상적으로 삭제되었습니다.");
				} else {
					System.out.println("====== 삭제 실패 ======");
				}
			} else if (menu == 9) {
				System.out.println("종료합니다.");
				run = false;
			}
			
		}
	}
}
