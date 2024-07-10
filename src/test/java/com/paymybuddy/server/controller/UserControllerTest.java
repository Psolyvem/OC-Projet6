package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest
{
	UserController userController;
	@Mock
	IUserService userService;
	User testUser;

	@BeforeEach
	public void setUp()
	{
		userController = new UserController(this.userService);

		testUser = new User();
		testUser.setAdmin(false);
		testUser.setEmail("test@test.com");
		testUser.setSolde(100);
		testUser.setId(10);
		testUser.setPassword("test");
	}

	@Test
	public void getUsersTest()
	{
		ArrayList<User> testUserList = new ArrayList<>();
		testUserList.add(testUser);
		when(userService.getUsers()).thenReturn(testUserList);
		assertThat(userController.getUsers()).isEqualTo(testUserList);
	}

	@Test
	public void getUserByIdTest()
	{
		when(userService.getUserById(1)).thenReturn(Optional.of(testUser));
		assertThat(userController.getUserById(1)).isNotNull();
	}

	@Test
	public void getUserByEmailTest()
	{
		when(userService.getUserByEmail("test@test.com")).thenReturn(testUser);
		assertThat(userController.getUserByEmail("test@test.com")).isEqualTo(testUser);
	}

	@Test
	public void patchUserTest()
	{
		userController.patchUser(testUser);
		verify(userService, times(1)).updateUser(testUser);
	}
}
