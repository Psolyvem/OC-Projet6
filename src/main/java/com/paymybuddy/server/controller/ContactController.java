package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.model.ContactId;
import com.paymybuddy.server.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.security.Principal;

@RestController
public class ContactController
{
	@Autowired
	IContactService contactService;

	public ContactController()
	{

	}

	public ContactController(IContactService contactService)
	{
		this.contactService = contactService;
	}

	@GetMapping(path = "/contact")
	public Iterable<Contact> getContactByUser(Principal principal)
	{
		String email = principal.getName();
		return contactService.getContactByUser(email);
	}

	/**
	 * Add a contact
	 *
	 * @param contactId Need a request with a JSON body containing the following entries :<br>
	 *                    user1 : a user <br>
	 *                    user2 : a user <br>
	 */
	@PostMapping(path ="/contact")
	public void createContact(@RequestBody ContactId contactId)
	{
		Contact contact = contactId.toContact();
		contactService.createContact(contact);
		Logger.info(contactId.getUser1().getEmail() + " added " + contactId.getUser2().getEmail() + " as contact");
	}

	/**
	 * Delete a contact
	 *
	 * @param contactId Need a request with a JSON body containing the following entries :<br>
	 *                    user1 : a user <br>
	 *                    user2 : a user <br>
	 */
	@DeleteMapping(path ="/contact")
	public void deleteContact(@RequestBody ContactId contactId)
	{
		// Deleting the two possible combinations of users
		Contact contact = contactId.toContact();
		Contact contact2 = new Contact();
		contact2.setUser1(contact.getUser2());
		contact2.setUser2(contact.getUser1());
		contactService.deleteContact(contact);
		contactService.deleteContact(contact2);
		Logger.info(contactId.getUser1().getEmail() + " deleted " + contactId.getUser2().getEmail() + " as contact");
	}
}
