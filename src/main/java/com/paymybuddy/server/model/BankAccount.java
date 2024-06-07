package com.paymybuddy.server.model;


import jakarta.persistence.*;

@Entity
@Table(name = "bank_account")
public class BankAccount
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	String rib;
	String iban;
	@ManyToOne(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	User user;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getRib()
	{
		return rib;
	}

	public void setRib(String rib)
	{
		this.rib = rib;
	}

	public String getIban()
	{
		return iban;
	}

	public void setIban(String iban)
	{
		this.iban = iban;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}
