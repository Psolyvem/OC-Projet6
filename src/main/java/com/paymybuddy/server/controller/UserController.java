package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.Optional;

@RestController
public class UserController
{
	@Autowired
	IUserService userService;

	@GetMapping("/user")
	public Iterable<User> getUsers()
	{
		return userService.getUsers();
	}

	@GetMapping(path = "/user", params = "id")
	public Optional<User> getUserById(@RequestParam(name = "id") int id)
	{
		return userService.getUserById(id);
	}

	@GetMapping(path = "/user", params = "email")
	public User getUserByEmail(@RequestParam(name = "email") String email)
	{
		return userService.getUserByEmail(email);
	}

	@PatchMapping(path = "/user")
	public void patchUser(@RequestBody User user)
	{
		userService.updateUser(user);
	}
}
