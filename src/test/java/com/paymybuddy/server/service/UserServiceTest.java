package com.paymybuddy.server.service;

import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.UserRepository;
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
public class UserServiceTest
{
	IUserService userService;
	@Mock
	UserRepository userRepository;
	User testUser;
	ArrayList<User> testUserList;

	@BeforeEach
	public void setUp()
	{
		this.userService = new UserService(this.userRepository);

		testUser = new User();
		testUser.setAdmin(false);
		testUser.setEmail("test@test.com");
		testUser.setSolde(100);
		testUser.setId(10);
		testUser.setPassword("test");

		this.testUserList = new ArrayList<>();
		testUserList.add(testUser);
	}

	@Test
	public void getUsersTest()
	{
		when(userRepository.findAll()).thenReturn(testUserList);
		assertThat(userService.getUsers()).element(0).isEqualTo(testUser);
	}

	@Test
	public void getUserByIdTest()
	{
		when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
		assertThat(userService.getUserById(1)).isNotNull();
	}

	@Test
	public void getUserByEmailTest()
	{
		when(userRepository.findByEmail("test@test.com")).thenReturn(testUser);

		assertThat(userService.getUserByEmail("test@test.com")).isEqualTo(testUser);
	}

	@Test
	public void getUserByEmailNotFoundTest()
	{
		when(userRepository.findByEmail("test@test.com")).thenReturn(null);

		assertThat(userService.getUserByEmail("test@test.com")).isNull();
	}

	@Test
	public void createUserTest()
	{
		userService.createUser(testUser);
		verify(userRepository, times(1)).save(testUser);
	}

	@Test
	public void updateUserTest()
	{
		userService.updateUser(testUser);
		verify(userRepository, times(1)).save(testUser);
	}

	@Test
	public void deleteUserTest()
	{
		userService.deleteUser(testUser);
		verify(userRepository, times(1)).delete(testUser);
	}
}
