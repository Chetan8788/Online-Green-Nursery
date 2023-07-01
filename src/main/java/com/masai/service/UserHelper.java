package com.masai.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
	@Autowired
	private UserService userService;

	public String getLoggedInEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	public List<String> getLoggedInUserRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());
	}

	public Integer getLoggedInUserId() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.getUserByEmail(email).getUserId();
	}

}
