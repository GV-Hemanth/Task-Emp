package com.example.service;

import java.util.List;

import com.example.entity.Employee;

public interface EmpService {

	public Employee saveEmp(Employee emp);

	public List<Employee> getAllEmp(String sortby, String order);

	public Employee getEmpById(int id);

	public boolean deleteEmp(int id);
	
	
	
	public List<Employee> searchEmp(String searchTerm);
	
	public String getAverageSalaryByDepartment(String department);

}