package com.masai.service;

import java.util.List;

import com.masai.exception.UserException;
import com.masai.model.User;

public interface UserService {
	
 public User registerUser(User customer) throws UserException;

 public User getUserByEmail(String email)throws UserException;
 
 public User updateUser(User User) throws UserException;
 
 public User deleteUser(Integer UserId) throws UserException;
 
 public User getUserById(Integer UserId) throws UserException;
 
 public List<User> getAllUser() throws UserException;

}
