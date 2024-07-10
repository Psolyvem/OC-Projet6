package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer>
{
	@Query(value = "SELECT * FROM contact c JOIN user u ON u.id = c.user1_id JOIN user cu ON c.user2_id = cu.id WHERE u.email = :email OR cu.email = :email", nativeQuery = true)
	public Iterable<Contact> findByUser(@Param("email") String email);
}
