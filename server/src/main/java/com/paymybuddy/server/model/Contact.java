package com.paymybuddy.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contact")
@IdClass(ContactId.class)
public class Contact
{
	@Id
	@ManyToOne(
			cascade = CascadeType.MERGE,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "user1_id")
	User user1;
	@Id
	@ManyToOne(
			cascade = CascadeType.MERGE,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "user2_id")
	User user2;

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
}
