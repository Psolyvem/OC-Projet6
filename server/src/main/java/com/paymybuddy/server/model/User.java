package com.paymybuddy.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	String email;
	String password;
	double solde;
	@Column(name = "is_admin")
	boolean isAdmin;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public double getSolde()
	{
		return solde;
	}

	public void setSolde(double solde)
	{
		this.solde = solde;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin(boolean admin)
	{
		isAdmin = admin;
	}

	public String getRole()
	{
		return this.isAdmin ? "ADMIN" : "USER";
	}

}
