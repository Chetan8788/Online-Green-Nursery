package com.masai.controller_test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masai.controller.UserController;
import com.masai.exception.UserException;
import com.masai.model.User;
import com.masai.service.UserHelper;
import com.masai.service.UserServiceImpl;

@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { UserControllerTesting.class })
public class UserControllerTesting {
	MockMvc mockMvc;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private UserHelper userHelper;

	@InjectMocks
	private UserController controller;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testDeleteUser() throws Exception {

		User user = new User(1, "Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd");
		Integer userId = 1;

		when(userHelper.getLoggedInEmail()).thenReturn("shudhanshukumarmuz@gamil.com");
		when(userHelper.getLoggedInUserRoles()).thenReturn(Arrays.asList("ROLE_ADMIN"));
		when(userService.getUserById(userId)).thenReturn(user);
		when(userService.deleteUser(userId)).thenReturn(user);

		mockMvc.perform(delete("/users/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is("shudhanshukumarmuz@gamil.com")));

		verify(userHelper, times(1)).getLoggedInEmail();
		verify(userHelper, times(1)).getLoggedInUserRoles();
		verify(userService, times(1)).getUserById(userId);
		verify(userService, times(1)).deleteUser(userId);
	}

	@Test
	public void TestGetUserById() throws Exception {
		User user = new User("Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd");
		List<String> roles = Arrays.asList("ROLE_ADMIN");
		Integer userId = 1;
		when(userHelper.getLoggedInEmail()).thenReturn("shudhanshukumarmuz@gamil.com");
		when(userHelper.getLoggedInUserRoles()).thenReturn(roles);
		when(userService.getUserById(userId)).thenReturn(user);
		MvcResult result = mockMvc.perform(get("/users/{userId}", userId)).andExpect(status().isOk()).andReturn();
		String responseBody = result.getResponse().getContentAsString();

		User responseUser = new ObjectMapper().readValue(responseBody, User.class);

		assertEquals(user, responseUser);
		verify(userHelper, times(1)).getLoggedInEmail();
		verify(userHelper, times(1)).getLoggedInUserRoles();
		verify(userService, times(1)).getUserById(userId);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testGetAllUser() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(new User("Sudhanshu Kumar", "shudhanshukumarmuz@gamil.com", "Passw0rd"));
		users.add(new User("Raushan Gupta", "raushan@gamil.com", "Passw0rd"));
		when(userService.getAllUser()).thenReturn(users);

		MvcResult result = mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn();

		String responseBody = result.getResponse().getContentAsString();

		List<User> userList = new ObjectMapper().readValue(responseBody, new TypeReference<List<User>>() {
		});

		assertEquals(2, userList.size());
		verify(userService, times(1)).getAllUser();
	}

}
