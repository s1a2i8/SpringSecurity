package com.example.SBW8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//@Configuration
//@EnableWebSecurity(debug=true)
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/home").permitAll()
					.anyRequest().authenticated()
					)
			.userDetailsService(userDetailService())
			.httpBasic(httpBasic -> httpBasic.realmName("MyApp"));
	
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailService() {
		UserDetails user = User.builder() // to know more about builder study build patterns in java
				.username("test")
				.password("{noop}pass123") // {noop} - this allows plane text as password
				.build();
		return new InMemoryUserDetailsManager(user); // this is inmemory user managment class
		
	}
	
}	
