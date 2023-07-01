package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.UserException;
import com.masai.model.User;
import com.masai.service.UserHelper;
import com.masai.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserHelper userHelper;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws UserException {
		logger.info("Registering a new user: " + user.getEmail());
		User registeredUser = userService.registerUser(user);
		logger.info("User registered successfully: " + registeredUser.getEmail());
		return new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) throws UserException {
		String loggedInEmail = userHelper.getLoggedInEmail();
		List<String> roles = userHelper.getLoggedInUserRoles();

		if (loggedInEmail.equals(email) || roles.contains("ROLE_ADMIN")) {
			logger.info("Fetching user by email: " + email);
			User user = userService.getUserByEmail(email);
			logger.info("User found: " + user.getEmail());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		throw new UserException("Unauthorized to access other user details.");
	}

	@PutMapping("")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws UserException {
		String loggedInEmail = userHelper.getLoggedInEmail();
		List<String> roles = userHelper.getLoggedInUserRoles();

		if (loggedInEmail.equals(user.getEmail()) || roles.contains("ROLE_ADMIN")) {
			logger.info("Updating user: " + user.getEmail());
			User updatedUser = userService.updateUser(user);
			logger.info("User updated successfully: " + updatedUser.getEmail());
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		}

		throw new UserException("Unauthorized to update other user.");
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId) throws UserException {
		String loggedInEmail = userHelper.getLoggedInEmail();
		List<String> roles = userHelper.getLoggedInUserRoles();

		User user = userService.getUserById(userId);

		if (loggedInEmail.equals(user.getEmail()) || roles.contains("ROLE_ADMIN")) {
			logger.info("Deleting user with user id: " + userId);
			User deletedUser = userService.deleteUser(userId);
			logger.info("User deleted successfully: " + deletedUser.getEmail());
			return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
		}

		throw new UserException("Unauthorized to delete user with user id: " + userId);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) throws UserException {
		User user = null;
		String loggedInEmail = userHelper.getLoggedInEmail();
		List<String> roles = userHelper.getLoggedInUserRoles();

		logger.info("Fetching user by user id: " + userId);
		user = userService.getUserById(userId);

		if (loggedInEmail.equals(user.getEmail()) || roles.contains("ROLE_ADMIN")) {
			logger.info("User found: " + user.getEmail());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		throw new UserException("Unauthorized to access user with user id: " + userId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("")
	public ResponseEntity<List<User>> getAllUser() throws UserException {
		logger.info("Fetching all users.");
		List<User> users = userService.getAllUser();
		logger.info("Total users fetched: " + users.size());
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
}
