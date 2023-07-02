package com.masai.service_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.masai.model.User;
import com.masai.repository.UserDao;
import com.masai.service.UserServiceImpl;

@SpringBootTest(classes = { UserServiceTesting.class })
public class UserServiceTesting {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void testGetAllUser() {
		List<User> users = new ArrayList<>();
		users.add(new User("Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd"));
		users.add(new User("Raushan Gupta", "raushan@gamil.com", "Passw0rd"));
		when(userDao.findAll()).thenReturn(users);
		assertEquals(2, userService.getAllUser().size());
	}
}
