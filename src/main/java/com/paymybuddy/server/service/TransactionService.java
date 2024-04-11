package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService implements ITransactionService
{
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public Iterable<Transaction> getTransactions()
	{
		return transactionRepository.findAll();
	}

	@Override
	public Optional<Transaction> getTransactionById(int id)
	{
		return transactionRepository.findById(id);
	}

	@Override
	public void createTransaction(Transaction transaction)
	{
		transactionRepository.save(transaction);
	}

	@Override
	public void updateTransaction(Transaction transaction)
	{
		transactionRepository.save(transaction);
	}

	@Override
	public void deleteTransaction(Transaction transaction)
	{
		transactionRepository.delete(transaction);
	}
}
