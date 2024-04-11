package com.paymybuddy.server.service;

import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService
{
	@Autowired
	UserRepository userRepository;

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
		if(user != null)
		{
			return userRepository.findByEmail(email);
		}
		else
		{
			throw new UsernameNotFoundException("Username not found");
		}

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
