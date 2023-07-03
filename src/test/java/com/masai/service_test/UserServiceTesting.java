package com.masai.service_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.masai.exception.UserException;
import com.masai.model.Role;
import com.masai.model.User;
import com.masai.repository.RoleDao;
import com.masai.repository.UserDao;
import com.masai.service.UserServiceImpl;

@SpringBootTest(classes = { UserServiceTesting.class })
public class UserServiceTesting {

	@Mock
	private UserDao userDao;

	@Mock
	private RoleDao roleRepo;

	@Mock
	private PasswordEncoder encoder;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void testRegisterUser() {
		User user = new User(1, "Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd");
		Role role = new Role("2", "ROLE_USER");

		when(roleRepo.findById("2")).thenReturn(Optional.of(role));
		when(encoder.encode("Passw0rd")).thenReturn("encodedPassword");
		when(userDao.save(user)).thenReturn(user);

		assertEquals(user, userService.registerUser(user));

		verify(roleRepo).findById("2");
		verify(encoder).encode("Passw0rd");

	}

	@Test
	public void testRegisterUser_RoleNotFound() {
		User user = new User(1, "Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd");
		when(roleRepo.findById("2")).thenReturn(Optional.empty());

		assertThrows(UserException.class, () -> userService.registerUser(user));

		verify(userDao, never()).save(any(User.class));

	}

	@Test
	public void testRegisterUser_InvalidData() {

		assertThrows(UserException.class, () -> userService.registerUser(null));
		verify(userDao, never()).save(any(User.class));

	}

	@Test
	public void testGetUserByEmail() {
		User user = new User(1, "Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd");
		String email = "shudhanshukumarmuz@gamil.com";

		when(userDao.findByEmail(email)).thenReturn(user);
		assertEquals(user, userService.getUserByEmail(email));
	}

	@Test
	public void testGetUserByEmail_UserNotFoundException() {
		String email = "shudhanshukumarmuz@gamil.com";
		when(userDao.findByEmail(email)).thenReturn(null);
		assertThrows(UserException.class, () -> userService.getUserByEmail(email));
	}

	@Test
	public void testUpdateUser() {
		User existingUser = new User(1, "old", "old@gamil.com", "Old@1234");
		User updatedUser = new User(1, "new", "new@gamil.com", "new@1234");
		when(userDao.findById(updatedUser.getUserId())).thenReturn(Optional.of(existingUser));
		when(userDao.save(existingUser)).thenReturn(existingUser);

		assertEquals(updatedUser, userService.updateUser(updatedUser));

	}

	@Test
	public void testUpdateUser_UserNotFound() {
		User updatedUser = new User(1, "new", "new@gamil.com", "new@1234");
		when(userDao.findById(updatedUser.getUserId())).thenReturn(Optional.empty());

		assertThrows(UserException.class, () -> userService.updateUser(updatedUser));
		verify(userDao, never()).save(any(User.class));
	}

	@Test
	public void testDeleteUser() {
		User user = new User(1, "Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd");
		Integer userId = 1;

		when(userDao.findById(userId)).thenReturn(Optional.of(user));
		assertEquals(user, userService.deleteUser(userId));

		verify(userDao).deleteById(userId);

	}

	@Test
	public void testDeleteUser_UserNotFoundException() {
		Integer userId = 1;
		when(userDao.findById(userId)).thenReturn(Optional.empty());
		assertThrows(UserException.class, () -> userService.deleteUser(userId));
		verify(userDao, never()).deleteById(anyInt());

	}

	@Test
	public void testGetUserById() {
		User user = new User(1, "Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd");
		Integer userId = 1;
		when(userDao.findById(userId)).thenReturn(Optional.of(user));
		assertEquals(user, userService.getUserById(userId));
	}

	@Test
	public void testGetUserById_UserNotFoundException() {
		Integer userId = 1;
		when(userDao.findById(userId)).thenReturn(Optional.empty());

		assertThrows(UserException.class, () -> userService.getUserById(userId));
	}

	@Test
	public void testGetAllUser() {
		List<User> users = new ArrayList<>();
		users.add(new User("Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd"));
		users.add(new User("Raushan Gupta", "raushan@gamil.com", "Passw0rd"));
		when(userDao.findAll()).thenReturn(users);
		assertEquals(2, userService.getAllUser().size());
	}
}
