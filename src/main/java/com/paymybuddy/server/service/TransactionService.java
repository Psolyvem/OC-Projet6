package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.text.DecimalFormat;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService
{
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	IUserService userService;

	@Override
	public Iterable<Transaction> getTransactionsByUser(String email)
	{
		return transactionRepository.findByUser(email);
	}

	@Override
	public Optional<Transaction> getTransactionById(int id)
	{
		return transactionRepository.findById(id);
	}

	@Override
	public void createTransaction(Transaction transaction)
	{
		if (transaction.getAmount() <= transaction.getSender().getSolde())
		{
			transaction.setAmount(Math.round(transaction.getAmount() * 100.d) / 100.d);
			User sender = transaction.getSender();
			User receiver = transaction.getReceiver();
			sender.setSolde(sender.getSolde() - transaction.getAmount());
			receiver.setSolde(receiver.getSolde() + transaction.getAmount() - (transaction.getAmount() * (transaction.getFee()) / 100));
			userService.updateUser(sender);
			userService.updateUser(receiver);
			transactionRepository.save(transaction);
			Logger.info("Transaction between " + transaction.getSender().getEmail() + " and " + transaction.getReceiver().getEmail() + " of " + transaction.getAmount());
		}
		else
		{
			Logger.info(transaction.getSender().getEmail() + " tried to send " + transaction.getAmount() + " to " + transaction.getReceiver().getEmail() + " but does not have the necessary funds to perform transaction");
		}
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