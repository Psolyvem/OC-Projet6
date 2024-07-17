package com.paymybuddy.server.controller;

import com.paymybuddy.server.security.JwtService;
import com.paymybuddy.server.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

//@CrossOrigin
@RestController
public class LoginController
{
	private JwtService jwtService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	private IUserService userService;

	public LoginController(JwtService jwtService, IUserService userService)
	{
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm form, HttpServletRequest request)
	{
		try
		{
			request.login(form.getUsername(), form.getPassword());
			Authentication auth = (Authentication) request.getUserPrincipal();
			String token = jwtService.generateToken(auth);

			User user = (User) auth.getPrincipal();
			Logger.info("User {} logged in.", user.getUsername());
			return ResponseEntity.status(HttpStatus.OK).body("{ \"token\": \"" + token + "\" }");

		}
		catch (ServletException e)
		{
			Logger.error("Invalid username or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
	}

	@GetMapping(path = "/login", params = "email")
	public ResponseEntity<String> userExist(@RequestParam String email)
	{
		if (userService.getUserByEmail(email) != null)
		{
			return ResponseEntity.status(HttpStatus.OK).body("{ \"userExist\": \"true\" }");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body("{ \"userExist\": \"false\" }");
		}
	}

	@PostMapping("/account")
	public ResponseEntity<String> createAccount(@RequestBody LoginForm form)
	{
		if (userService.getUserByEmail(form.getUsername()) != null)
		{
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("{ \"userExist\": \"true\" }");
		}
		else if(!form.getUsername().contains("@") || !form.getUsername().contains(".") || form.getPassword().length() < 8)
		{
			Logger.error("Error in the sign up form, cannot create user. Frontend verifications bypassed, should not have happened");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
		else
		{
			com.paymybuddy.server.model.User user = new com.paymybuddy.server.model.User();
			user.setEmail(form.getUsername());
			user.setPassword(passwordEncoder.encode(form.getPassword()));
			user.setSolde(0);
			user.setAdmin(false);

			try
			{
				userService.createUser(user);
				Logger.info("New account created for " + form.getUsername());
			}
			catch (Exception e)
			{
				Logger.error("Unable to create user");
			}

			return ResponseEntity.status(HttpStatus.OK).build();
		}
	}
}

class LoginForm
{
	private String username;
	private String password;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
