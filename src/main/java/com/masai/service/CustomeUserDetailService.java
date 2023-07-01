package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.exception.UserException;
import com.masai.model.User;

@Service
public class CustomeUserDetailService implements UserDetailsService {
	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {
			user = service.getUserByEmail(username);
		} catch (UserException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("user not found with email : " + username);
		}
		return user;
	}

}
