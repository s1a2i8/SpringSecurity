package com.example.SBW8.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	//// building the JWT token
	
	@Value("${security.jwt.secret-key}")
	private String Secretkey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;
	
	public String generateToken(UserDetails userDetails) {
		
		return generateToken(new HashMap<>(),userDetails);
	}
	
	public String generateToken(Map<String,Object> extraclaims, UserDetails userDetails) {
		// TODO Auto-generated method stub
		return buildToken(extraclaims,userDetails,jwtExpiration);
	}
	
	public long getExpirationTime() {
		return jwtExpiration;
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(Secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private String buildToken(Map<String, Object> extraclaims, UserDetails userDetails, long expiration) {
		
		return Jwts
				.builder()
				.setClaims(extraclaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ expiration))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
		
	}

	
//// Above is for building the token

	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
		
	}

	private boolean isTokenExpired(String token) {
	    return extractExpiration(token).before(new Date());
	}

	public String extractUserName(String token) {
	    return extractClaim(token, Claims::getSubject);
	}

	private Date extractExpiration(String token) {
	    return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	    final Claims claims = extractAllClaims(token);
	    return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(getSignInKey())   // your secret key
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}




	
	
}
