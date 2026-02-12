package com.example.SBW8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SBW8.dto.LoginDTO;
import com.example.SBW8.dto.RegisterDTO;
import com.example.SBW8.entity.Employee;
import com.example.SBW8.repository.EmployeeRepository;

@Service
public class AuthService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeRepository repo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	public Employee register(RegisterDTO input) {
		Employee user = new Employee();
		user.setName(input.getName());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setRole(input.getRole());
		
		return repo.save(user);
	}
	
	public Employee login(LoginDTO input) {
		authenticationManager.authenticate(
			    new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
		
		return repo.findByEmail(input.getEmail());
	}



}
