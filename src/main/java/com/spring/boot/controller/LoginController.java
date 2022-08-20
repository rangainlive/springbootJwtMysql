package com.spring.boot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.model.LoginRequest;
import com.spring.boot.model.RegisterRequest;
import com.spring.boot.security.AuthTokenProvider;
import com.spring.boot.service.UserService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	private static final Logger log = LogManager.getLogger(LoginController.class);
		
	@Autowired
	AuthTokenProvider authTokenUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		log.info("Login method called");
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());
 
		Authentication authentication = authenticationManager.authenticate(token);
		
		String jwtToken = authTokenUtils.generateToken(authentication);
		
		return ResponseEntity.ok(jwtToken);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
		log.info("Register method called");
		String status = userService.registerUser(registerRequest);
		
		
		return ResponseEntity.ok(status);
	}

}
