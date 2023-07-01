package com.masai.controller;

import java.util.List;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.UserException;
import com.masai.model.User;
import com.masai.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/users")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws UserException {
		User registeredUser = userService.registerUser(user);
		return new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
	}

	@GetMapping("/users/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) throws UserException {

		User user = userService.getUserByEmail(email);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws UserException {

		User existingUser = userService.updateUser(user);
		return new ResponseEntity<User>(existingUser, HttpStatus.OK);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId) throws UserException {

		User user = userService.deleteUser(userId);

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@GetMapping("/getusers/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) throws UserException {

		User user = userService.getUserById(userId);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUser() throws UserException {
		List<User> users = userService.getAllUser();

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

}
