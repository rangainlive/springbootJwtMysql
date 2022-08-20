package com.spring.boot.model;

import lombok.Data;

@Data
public class RegisterRequest {
	
	private String username;
	private String password;
	private String email;
	private String role;
	private String name;
	
}
