package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

@Service
public class ContactService implements IContactService
{
	@Autowired
	ContactRepository contactRepository;

	@Override
	public Iterable<Contact> getContacts()
	{
		return contactRepository.findAll();
	}

	@Override
	public Iterable<Contact> getContactByUser(String email)
	{
		return contactRepository.findByUser(email);
	}

	@Override
	public void createContact(Contact contact)
	{
		try
		{
			contactRepository.save(contact);
		}
		catch (Exception e)
		{
			Logger.error("Unable to save contact");
		}

	}

	@Override
	public void updateContact(Contact contact)
	{
		try
		{
			contactRepository.save(contact);
		}
		catch (Exception e)
		{
			Logger.error("Unable to save contact");
		}
	}

	@Override
	public void deleteContact(Contact contact)
	{
		try
		{
			contactRepository.delete(contact);
		}
		catch (Exception e)
		{
			Logger.error("Unable to delete contact");
		}
	}
}
