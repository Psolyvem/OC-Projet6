package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>
{
}
