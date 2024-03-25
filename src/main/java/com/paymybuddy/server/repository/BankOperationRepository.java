package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.BankOperation;
import org.springframework.data.repository.CrudRepository;

public interface BankOperationRepository extends CrudRepository<BankOperation, Integer>
{
}
