package com.source.etracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.source.etracker.dao.EmployeeRepository;
import com.source.etracker.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Employee findbyId(int theId) {
		Optional<Employee>	result	=	 employeeRepository.findById(theId);
		Employee theEmployee	=	null;
		if(result.isPresent())
			theEmployee	=	result.get();
		return theEmployee;
	}

	@Override
	@Transactional
	public void save(Employee theEmployee) {
		employeeRepository.save(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}

}
