package com.spring.boot.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenUtils {
	
	private static final int expireInMs = 60*1000;
	
	private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generate(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuer("Ranga")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expireInMs))
				.signWith(key)
				.compact();
	}
	
	public boolean validate(String token) {
		if(getUsername(token)!=null && isExpired(token)) {
			return true;
		}
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		return claims.getSubject();
	}
	
	public boolean isExpired(String token) {
		Claims claims = getClaims(token);
		return claims.getExpiration().after(new Date(System.currentTimeMillis()));
	}
	
	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

}
