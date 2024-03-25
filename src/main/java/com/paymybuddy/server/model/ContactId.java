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

	public ContactId(User user1, User user2)
	{
		this.user1 = user1;
		this.user2 = user2;
	}
}
