package com.example.SBW8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SBW8.entity.Employee;
import com.example.SBW8.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	// get all employee Details
	
	// business logic
	
	@Autowired
	private EmployeeRepository repo;
	
	public List<Employee> getAllEmployees(){
		return repo.findAll();
	}

}
