package com.paymybuddy.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTest
{
	User testUser;

	@BeforeEach
	public void setUp()
	{
		testUser = new User();
		testUser.setAdmin(false);
		testUser.setEmail("test@test.com");
		testUser.setSolde(100);
		testUser.setId(10);
		testUser.setPassword("test");
	}

	@Test
	public void userTests()
	{
		assertThat(testUser.getRole()).isEqualTo("USER");
		assertThat(testUser.getId()).isEqualTo(10);
		assertThat(testUser.getPassword()).isEqualTo("test");
	}
}
