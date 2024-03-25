package com.paymybuddy.server.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction
{
	@Id
	@GeneratedValue
	@Column(name = "id")
	int id;
	Date date;
	double amount;
	String description;
	float fee;
	@ManyToOne(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")
	User sender;
	@ManyToOne(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "receiver_id")
	User receiver;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public float getFee()
	{
		return fee;
	}

	public void setFee(float fee)
	{
		this.fee = fee;
	}

	public User getSender()
	{
		return sender;
	}

	public void setSender(User sender)
	{
		this.sender = sender;
	}

	public User getReceiver()
	{
		return receiver;
	}

	public void setReceiver(User receiver)
	{
		this.receiver = receiver;
	}
}
