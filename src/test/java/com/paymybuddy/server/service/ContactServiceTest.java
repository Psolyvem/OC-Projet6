package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ContactServiceTest
{
	IContactService contactService;
	@Mock
	ContactRepository contactRepository;
	Contact testContact;

	@BeforeEach
	public void setUp()
	{
		this.contactService = new ContactService(this.contactRepository);

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
	public void getContactsTest()
	{
		contactService.getContacts();
		verify(contactRepository, times(1)).findAll();
	}

	@Test
	public void getContactByUserTest()
	{
		contactService.getContactByUser("test@test.com");
		verify(contactRepository, times(1)).findByUser("test@test.com");
	}

	@Test
	public void createContactTest()
	{
		contactService.createContact(testContact);
		verify(contactRepository, times(1)).save(testContact);
	}

	@Test
	public void updateContactTest()
	{
		contactService.updateContact(testContact);
		verify(contactRepository, times(1)).save(testContact);
	}

	@Test
	public void deleteContactTest()
	{
		contactService.deleteContact(testContact);
		verify(contactRepository, times(1)).delete(testContact);
	}
}
