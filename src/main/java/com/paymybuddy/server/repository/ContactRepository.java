package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer>
{
}
