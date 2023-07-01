package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.UserException;
import com.masai.model.User;
import com.masai.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User registerUser(User user) throws UserException {

//		Set<Order> orders=customer.getOrders() ;
//		for(Order o:orders) {
//			o.setCustomer(customer);
//		}

		return userDao.save(user);
	}

	@Override
	public User getUserByEmail(String email) throws UserException {
		User customer = userDao.findByEmail(email);
		if (customer == null)
			throw new UserException("customer not found with email " + email);
		else
			return customer;
	}

	@Override
	public User updateUser(User user) throws UserException {

		Optional<User> optional = userDao.findById(user.getUserId());
		if (optional.isPresent()) {
			User existingUser = optional.get();

			userDao.save(user);

			return existingUser;
		} else
			throw new UserException("Customer not exist with customer Id : " + user.getUserId());

	}

	@Override
	public User deleteUser(Integer userId) throws UserException {

		Optional<User> optional = userDao.findById(userId);

		if (optional.isPresent()) {
			User existingUser = optional.get();

			userDao.deleteById(userId);

			return existingUser;
		} else
			throw new UserException("Customer not exist with customer Id : " + userId);

	}

	@Override
	public User getUserById(Integer userId) throws UserException {

		Optional<User> optional = userDao.findById(userId);

		if (optional.isPresent()) {

			return optional.get();

		} else
			throw new UserException("Customer not exist with customer Id : " + userId);

	}

	@Override
	public List<User> getAllUser() throws UserException {

		List<User> users = userDao.findAll();

		if (!users.isEmpty()) {
			return users;
		} else
			throw new UserException(" Customer Not Found !");

	}

}
