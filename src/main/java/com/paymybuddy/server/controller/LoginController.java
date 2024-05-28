package com.paymybuddy.server.controller;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.paymybuddy.server.security.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

//@CrossOrigin
@RestController
public class LoginController
{
	private JwtService jwtService;

	public LoginController(JwtService jwtService)
	{
		this.jwtService = jwtService;
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

		} catch (ServletException e)
		{
			Logger.error("Invalid username or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
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
