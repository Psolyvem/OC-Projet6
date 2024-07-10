package com.paymybuddy.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ContactTest
{
	Contact testContact;
	ContactId testContactId;

	@BeforeEach
	public void setUp()
	{
		User user1 = new User();
		user1.setAdmin(false);
		user1.setEmail("test@test.com");
		user1.setSolde(100);
		user1.setId(10);
		user1.setPassword("test");

		User user2 = new User();
		user2.setAdmin(false);
		user2.setEmail("test2@test.com");
		user2.setSolde(100);
		user2.setId(11);
		user2.setPassword("test");

		testContact = new Contact();
		testContact.setUser1(user1);
		testContact.setUser2(user2);

		testContactId = new ContactId(user1, user2);
		testContactId.setUser1(user1);
		testContactId.setUser2(user2);
	}

	@Test
	public void contactTests()
	{
		assertThat(testContactId.getUser1().getEmail()).isEqualTo(testContact.getUser1().getEmail());
		assertThat(testContactId.getUser2().getEmail()).isEqualTo(testContact.getUser2().getEmail());
		assertThat(testContactId.toContact().getUser1().getEmail()).isEqualTo(testContact.getUser1().getEmail());
		assertThat(testContactId.toContact().getUser2().getEmail()).isEqualTo(testContact.getUser2().getEmail());
	}
}
