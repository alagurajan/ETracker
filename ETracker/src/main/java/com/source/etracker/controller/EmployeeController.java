package com.source.etracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.source.etracker.entity.Employee;
import com.source.etracker.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/list")
	public String listEmployees(Model theModel){
		theModel.addAttribute("pageTitle", "Employee Directory");
		theModel.addAttribute("employees", employeeService.findAll());
		return "employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel){
		Employee theEmployee	=	new Employee();
		theModel.addAttribute("pageTitle", "Add Employee");
		theModel.addAttribute("employee", theEmployee);
		
		return "/employees/employee-form";
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
		employeeService.save(theEmployee);
		return "redirect:/employees/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel){
		Employee theEmployee	=	employeeService.findbyId(theId);
		theModel.addAttribute("employee", theEmployee);
		
		return "/employees/employee-form";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") int theId){
		employeeService.deleteById(theId);
		return "redirect:/employees/list";
	}
	
	@GetMapping("/filtering-list")
	public List<Employee> getEmpList(){
		return employeeService.findAll();
	}
	
}

