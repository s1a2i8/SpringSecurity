package com.example.SBW8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SBW8.entity.Employee;
import com.example.SBW8.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private  EmployeeService service;
	

	
	@GetMapping("/employees")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String GetEmployees() {
		return "List of Employees";
	}
	@GetMapping("/home")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String Welcome() {
		return "This is Home Page";
	}
	
	@GetMapping("/employees/all")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<Employee> getAllEmployees(){
		
		return service.getAllEmployees();
	}

}
