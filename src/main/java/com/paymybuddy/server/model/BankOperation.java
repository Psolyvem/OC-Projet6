package com.paymybuddy.server.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "bank_operation")
public class BankOperation
{
	@Id
	@GeneratedValue
	@Column(name = "id")
	int id;
	Date date;
	float amount;
	@ManyToOne(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	BankAccount bankAccount;

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

	public float getAmount()
	{
		return amount;
	}

	public void setAmount(float amount)
	{
		this.amount = amount;
	}

	public BankAccount getBankAccount()
	{
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount)
	{
		this.bankAccount = bankAccount;
	}
}
