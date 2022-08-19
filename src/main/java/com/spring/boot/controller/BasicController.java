package com.spring.boot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic")
public class BasicController {
	
	@PostMapping("/welcome")
	public ResponseEntity<String> grettings(){
		return ResponseEntity.ok("Hey, Welcome to the Spring Boot lecture");
	}
	
	@PostMapping("/hai")
	public ResponseEntity<String> hai(){
		return ResponseEntity.ok("Hey, configuration done");
	}
	

}
