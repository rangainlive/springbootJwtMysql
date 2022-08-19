package com.spring.boot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {
	
	@GetMapping("/welcome")
	public ResponseEntity<String> grettings(){
		return ResponseEntity.ok("Hey, Welcome to the Spring Boot lecture");
	}

}