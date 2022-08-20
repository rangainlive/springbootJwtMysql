package com.spring.boot.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.model.RegisterRequest;
import com.spring.boot.model.Role;
import com.spring.boot.model.RoleName;
import com.spring.boot.model.User;
import com.spring.boot.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public String registerUser(RegisterRequest request) {
		Role role = new Role();
		if(!request.getRole().isEmpty())
			role.setName(request.getRole().equals("USER") ? RoleName.ROLE_USER : RoleName.ROLE_ADMIN);
		Set<Role> roles = new HashSet<>();
			roles.add(role);
		User user = new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		user.setPassword(request.getPassword());
		user.setUsername(request.getUsername());
		user.setRoles(roles);
		userRepository.save(user);
		return "Success";
	}

}
