package com.spring.boot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, String> users = new HashMap<>();
		users.put("admin", encoder.encode("1234"));
		users.put("user", encoder.encode("4321"));
		if(users.containsKey(username)) {
			return new User(username, users.get(username), new ArrayList<>());
		}
		throw new UsernameNotFoundException(username);
	}

}
