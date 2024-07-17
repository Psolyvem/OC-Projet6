package com.paymybuddy.server.service;

import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.Optional;

@Service
public class UserService implements IUserService
{
	UserRepository userRepository;

	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public Iterable<User> getUsers()
	{
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(int id)
	{
		return userRepository.findById(id);
	}

	@Override
	public User getUserByEmail(String email)
	{
		User user = userRepository.findByEmail(email);
		if(user == null)
		{
			Logger.info("Username \"" + email + "\" not found");
		}

		return user;
	}

	@Override
	public void createUser(User user)
	{
		userRepository.save(user);
	}

	@Override
	public void updateUser(User user)
	{
		userRepository.save(user);
	}

	@Override
	public void deleteUser(User user)
	{
		userRepository.delete(user);
	}
}
