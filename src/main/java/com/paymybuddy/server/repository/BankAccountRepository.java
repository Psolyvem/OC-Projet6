package com.paymybuddy.server.repository;

import com.paymybuddy.server.model.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer>
{
}
