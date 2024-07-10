package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.model.ContactId;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.IContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ContactControllerTest
{
	ContactController contactController;
	@Mock
	IContactService contactService;
	Contact testContact;

	@BeforeEach
	public void setUp()
	{
		this.contactController = new ContactController(this.contactService);

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
	}

	@Test
	public void getTransactionsByUserTest()
	{
		ArrayList<Contact> testContactList = new ArrayList<>();
		testContactList.add(testContact);

		when(contactService.getContactByUser("test@test.com")).thenReturn(testContactList);
		assertThat(contactController.getContactByUser(new Principal()
		{
			@Override
			public String getName()
			{
				return "test@test.com";
			}
		})).isEqualTo(testContactList);
	}

	@Test
	public void createContactTest()
	{
		contactController.createContact(new ContactId(testContact.getUser1(), testContact.getUser2()));
		verify(contactService, times(1)).createContact(any(Contact.class));
	}

	@Test
	public void deleteContactTest()
	{
		contactController.deleteContact(new ContactId(testContact.getUser1(), testContact.getUser2()));

		verify(contactService, times(2)).deleteContact(any(Contact.class));
	}
}
