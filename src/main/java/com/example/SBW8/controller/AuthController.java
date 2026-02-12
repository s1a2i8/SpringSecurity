package com.example.SBW8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SBW8.dto.LoginDTO;
import com.example.SBW8.dto.RegisterDTO;
import com.example.SBW8.entity.Employee;
import com.example.SBW8.service.AuthService;
import com.example.SBW8.service.JWTService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JWTService jwtservice;
	
	@PostMapping("/register")
	public Employee Register(@RequestBody RegisterDTO registerDto) {
		
		Employee  user = authService.register(registerDto);
		
		return user;
		
	}
	
	@PostMapping("/login")
	public String Login(@RequestBody LoginDTO loginDto) {
		Employee User = authService.login(loginDto);

		String token = jwtservice.generateToken(User);
		return token;
	}

}
