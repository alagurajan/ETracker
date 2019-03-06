package com.source.etracker.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.source.etracker.entity.Employee;
import com.source.etracker.service.EmployeeService;

@Controller
public class DefaultController {
	@Autowired
	private EmployeeService employeeService;
	
	Map<Integer, String> PdfMap	=	new HashMap<Integer, String>();
	
	
	@PostConstruct
	public void DefaultController() {
		PdfMap.put(6, "Java-8-Features");
		PdfMap.put(7, "Java-Abstraction");
		PdfMap.put(8, "Java-Annotations-Tutorial");
		PdfMap.put(9, "Multithreading-and-Concurrency-Questions");
	}
	
	@GetMapping("/")
    public String home1(@RequestParam(value="PdfId" , required = false) Integer theId, Model theModel) {
		Employee theEmployee	=	new Employee();
		theModel.addAttribute("PdfFile", PdfMap.get(theId));
		theModel.addAttribute("pageTitle", "Add Employee");
		theModel.addAttribute("employee", theEmployee);
        return "/home";
    }

    @GetMapping("/home")
    public String home(@RequestParam(value="PdfId" , required = false) Integer theId, Model theModel) {
    	theModel.addAttribute("PdfFile", PdfMap.get(theId));
    	theModel.addAttribute("pageTitle", "This is the Home of Etracker");
    	theModel.addAttribute("employees", employeeService.findAll());
        return "/home";
    }

    @GetMapping("/admin")
    public String admin(Model theModel) {
    	theModel.addAttribute("pageTitle", "Administrator");
        return "/admin";
    }

    @GetMapping("/user")
    public String user(Model theModel) {
    	theModel.addAttribute("pageTitle", "User");
        return "/user";
    }

    @GetMapping("/about")
    public String about(Model theModel) {
    	theModel.addAttribute("pageTitle", "About Us");
        return "/about";
    }

    @GetMapping("/login")
    public String login(Model theModel) {
    	theModel.addAttribute("pageTitle", "E-Tracker Login");
        return "/login";
    }

    @GetMapping("/403")
    public String error403(Model theModel) {
    	theModel.addAttribute("pageTitle", "Error 403");
        return "/error/403";
    }
}
