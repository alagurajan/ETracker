package com.source.etracker.service;

import java.util.List;

import com.source.etracker.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> findAll();
	
	public Employee findbyId(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

}
