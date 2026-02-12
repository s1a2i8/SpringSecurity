package com.example.SBW8.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.SBW8.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTService jwtservice;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String authHeader = request.getHeader("Authorization");
		
		if(authHeader ==null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			final String jwt = authHeader.substring(7);
			final String userEmail = jwtservice.extractUserName(jwt);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if(userEmail !=null && authentication ==null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				
				if(jwtservice.isTokenValid(jwt, userDetails)) {
					
					UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
							userDetails,null,userDetails.getAuthorities());
					
					authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authtoken);
					
				}
			}
			filterChain.doFilter(request, response);
		}catch (Exception e){
			handlerExceptionResolver.resolveException(request,response,null,e);
		}
		
	}

}
