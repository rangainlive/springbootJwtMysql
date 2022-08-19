package com.spring.boot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.model.LoginRequest;
import com.spring.boot.security.TokenUtils;

@RestController
@RequestMapping("/auth")
public class LoginController {

	private static final Logger log = LogManager.getLogger(LoginController.class);
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		log.info("Login method called");
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());

		authenticationManager.authenticate(token);
		
		String jwtToken = tokenUtils.generate(loginRequest.getUsername());
		
		return ResponseEntity.ok(jwtToken);
	}

}
