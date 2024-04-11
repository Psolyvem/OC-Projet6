package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController
{
	@Autowired
	IUserService userService;

	@GetMapping("/")
	public String home(Model model, Principal principal)
	{
		UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) principal);
		if (token.isAuthenticated())
		{
			org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) token.getPrincipal();
			model.addAttribute("username", u.getUsername());
		}
		return "home";
	}

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

	@GetMapping("/test")
	public String getTest()
	{
		return "Hello you !";
	}

}
