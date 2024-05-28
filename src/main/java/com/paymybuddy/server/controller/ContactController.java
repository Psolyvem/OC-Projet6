package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ContactController
{
	@Autowired
	IContactService contactService;

	@GetMapping("/contact")
	public Iterable<Contact> getContacts()
	{
		return contactService.getContacts();
	}

//	@GetMapping(path = "/contact", params = "user")
//	public Optional<Contact> getContactByUser(@RequestParam(name = "user") User user)
//	{
//		return contactService.getContactByUser(user);
//	}
}
