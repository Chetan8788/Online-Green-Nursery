package com.masai.authcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.model.JwtRequest;
import com.masai.model.JwtResponse;
import com.masai.model.User;
import com.masai.security.JwtHelper;
import com.masai.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private JwtHelper helper;
	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

		this.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.helper.generateToken(userDetails);

		JwtResponse response = JwtResponse.builder().jwtToken(token).userName(userDetails.getUsername()).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User createdUser = userService.registerUser(user);
		logger.info("User created: {}", createdUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	

}
