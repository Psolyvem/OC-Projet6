package com.paymybuddy.server.security;

import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		User user = userService.getUserByEmail(email);

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user.getRole()));

	}

	private List<GrantedAuthority> getGrantedAuthorities(String role)
	{
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(role == "ADMIN")
		{
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
}