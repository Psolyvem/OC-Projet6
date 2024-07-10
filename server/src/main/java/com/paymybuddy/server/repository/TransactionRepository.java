package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>
{
	@Query(value = "SELECT t.id, t.date, t.amount, t.description, t.fee, t.sender_id, t.receiver_id FROM transaction t JOIN user u ON t.sender_id = u.id JOIN user ru ON t.receiver_id = ru.id WHERE u.email = :email OR ru.email = :email ORDER BY date DESC", nativeQuery = true)
	public Iterable<Transaction> findByUser(@Param("email") String email);

	public Iterable<Transaction> findByOrderByIdDesc();
}
