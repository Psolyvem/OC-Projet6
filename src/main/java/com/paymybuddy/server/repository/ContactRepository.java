package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer>
{
	@Query(value = "SELECT * FROM contact WHERE user1_id = 8 OR user2_id = 8", nativeQuery = true)
	public Iterable<Contact> findByCostNative(@Param("cout") Integer id);
}
