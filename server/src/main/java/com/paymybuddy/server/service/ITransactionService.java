package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Transaction;

import java.util.Optional;

public interface ITransactionService
{
	public Iterable<Transaction> getTransactionsByUser(String email);

	public Optional<Transaction> getTransactionById(int id);

	public void createTransaction(Transaction transaction);

	public void updateTransaction(Transaction transaction);

	public void deleteTransaction(Transaction transaction);
}
