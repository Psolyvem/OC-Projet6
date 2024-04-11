package com.paymybuddy.server.service;

import com.paymybuddy.server.model.User;

import java.util.Optional;

public interface IUserService
{
	public Iterable<User> getUsers();

	public Optional<User> getUserById(int id);

	public User getUserByEmail(String name);

	public void createUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);
}
