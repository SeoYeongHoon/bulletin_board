package co.yedam;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 필드
@NoArgsConstructor // 기본 생성자
public class Employee {
	private int empNo;
	private String empName;
	private String email;
	private String phone;
	private Date hireDate;
	private int salary;
	private String department;
}
