package com.paymybuddy.server.model;

import java.io.Serializable;

/**
 * This class is an identification class for Contact.
 * It's only use is to define the composite primary key of Contact
 */
public class ContactId implements Serializable
{
	User user1;
	User user2;

	/**
	 * Default Contructor, never used but Spring is angry if there isn't one.
	 */
	public ContactId()
	{
		this.user1 = null;
		this.user2 = null;
	}

	public Contact toContact()
	{
		Contact contact = new Contact();
		contact.setUser1(this.user1);
		contact.setUser2(this.user2);
		return contact;
	}

	public User getUser1()
	{
		return user1;
	}

	public void setUser1(User user1)
	{
		this.user1 = user1;
	}

	public User getUser2()
	{
		return user2;
	}

	public void setUser2(User user2)
	{
		this.user2 = user2;
	}

	public ContactId(User user1, User user2)
	{
		this.user1 = user1;
		this.user2 = user2;
	}
}
