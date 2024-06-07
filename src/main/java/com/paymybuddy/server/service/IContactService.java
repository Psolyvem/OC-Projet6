package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.model.User;

import java.util.Optional;

public interface IContactService
{
	public Iterable<Contact> getContacts();

	public Iterable<Contact> getContactByUser(String email);

	public void createContact(Contact contact);

	public void updateContact(Contact contact);

	public void deleteContact(Contact contact);
}
