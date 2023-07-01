package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.masai.exception.UserException;
import com.masai.model.Role;
import com.masai.model.User;
import com.masai.repository.RoleDao;
import com.masai.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleRepo;

	@Autowired
	private PasswordEncoder encoder;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * Registers a new user.
	 *
	 * @param user The User object to be registered.
	 * @return The created User object.
	 * @throws UserException if the provided user object is null or if there is an
	 *                       error while registering the user.
	 */
	@Override
	public User registerUser(User user) throws UserException {
		if (user == null) {
			throw new UserException("Please provide valid data for the user.");
		}

		user.setPassword(encoder.encode(user.getPassword()));
		Role opt = roleRepo.findById("2").orElse(null);

		if (opt == null) {
			throw new UserException("Role not found with ID: 2");
		}

		user.getRoles().add(opt);
		User createdUser = userDao.save(user);

		// Log the created user
		logger.info("User created: {}", createdUser);

		return createdUser;
	}

	/**
	 * Retrieves a user by email.
	 *
	 * @param email The email of the user to be retrieved.
	 * @return The User object with the given email.
	 * @throws UserException if the user with the given email is not found.
	 */
	@Override
	public User getUserByEmail(String email) throws UserException {
		User customer = userDao.findByEmail(email);
		if (customer == null) {
			throw new UserException("Customer not found with email: " + email);
		}
		return customer;
	}

	/**
	 * Updates an existing user.
	 *
	 * @param user The updated User object.
	 * @return The existing User object before the update.
	 * @throws UserException if the provided user object is null or if the user with
	 *                       the given userId is not found.
	 */
	@Override
	public User updateUser(User user) throws UserException {
		Optional<User> optional = userDao.findById(user.getUserId());

		if (optional.isPresent()) {
			User existingUser = optional.get();
			userDao.save(user);
			return existingUser;
		} else {
			throw new UserException("Customer does not exist with customer ID: " + user.getUserId());
		}
	}

	/**
	 * Deletes a user.
	 *
	 * @param userId The ID of the user to be deleted.
	 * @return The deleted User object.
	 * @throws UserException if the user with the given userId is not found or if
	 *                       there is an error while deleting the user.
	 */
	@Override
	public User deleteUser(Integer userId) throws UserException {
		Optional<User> optional = userDao.findById(userId);

		if (optional.isPresent()) {
			User existingUser = optional.get();
			userDao.deleteById(userId);
			return existingUser;
		} else {
			throw new UserException("Customer does not exist with customer ID: " + userId);
		}
	}

	/**
	 * Retrieves a user by ID.
	 *
	 * @param userId The ID of the user to be retrieved.
	 * @return The User object with the given ID.
	 * @throws UserException if the user with the given userId is not found.
	 */
	@Override
	public User getUserById(Integer userId) throws UserException {
		Optional<User> optional = userDao.findById(userId);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new UserException("Customer does not exist with customer ID: " + userId);
		}
	}

	/**
	 * Retrieves all users.
	 *
	 * @return A list of all User objects.
	 * @throws UserException if there are no users found.
	 */
	@Override
	public List<User> getAllUser() throws UserException {
		List<User> users = userDao.findAll();

		if (!users.isEmpty()) {
			return users;
		} else {
			throw new UserException("No customers found.");
		}
	}
}
