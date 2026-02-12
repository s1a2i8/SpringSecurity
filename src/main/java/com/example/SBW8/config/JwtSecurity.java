package com.example.SBW8.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity(debug=true)
public class JwtSecurity {

	@Autowired
	private AuthenticationProvider authProvider;
	
	@Autowired
	private JwtFilter jwtfilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth-> auth
					.requestMatchers("/register").permitAll()
					.requestMatchers("/login").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/v3/api-docs/**").permitAll()
					.requestMatchers("/swagger-ui.html").permitAll()
					.requestMatchers("/h2-console/**").permitAll()
					.anyRequest().authenticated()
					)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authProvider)
			.addFilterBefore(jwtfilter,UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
	}
	
}
