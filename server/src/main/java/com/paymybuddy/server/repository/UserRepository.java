package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
	public User findByEmail(String email);
}
