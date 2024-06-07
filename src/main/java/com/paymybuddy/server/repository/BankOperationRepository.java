package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.BankOperation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankOperationRepository extends CrudRepository<BankOperation, Integer>
{
}
