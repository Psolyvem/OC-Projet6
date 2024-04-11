package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
	public Optional<Contact> getContactById(int id)
	{
		return contactRepository.findById(id);
	}

	@Override
	public void createContact(Contact contact)
	{
		contactRepository.save(contact);
	}

	@Override
	public void updateContact(Contact contact)
	{
		contactRepository.save(contact);
	}

	@Override
	public void deleteContact(Contact contact)
	{
		contactRepository.delete(contact);
	}
}
