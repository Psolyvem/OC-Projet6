package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tinylog.Logger;

import java.util.Optional;

@Service
public class TransactionService implements ITransactionService
{
	TransactionRepository transactionRepository;
	IUserService userService;

	public TransactionService(TransactionRepository transactionRepository, IUserService userService)
	{
		this.transactionRepository = transactionRepository;
		this.userService = userService;
	}

	@Override
	public Iterable<Transaction> getTransactionsByUser(String email)
	{
		if (userService.getUserByEmail(email).isAdmin())
		{
			return transactionRepository.findByOrderByIdDesc();
		}
		else
		{
			return transactionRepository.findByUser(email);
		}
	}

	@Override
	public Optional<Transaction> getTransactionById(int id)
	{
		return transactionRepository.findById(id);
	}

	@Override
	@Transactional
	public void createTransaction(Transaction transaction)
	{
		if (transaction.getAmount() <= transaction.getSender().getSolde())
		{
			transaction.setAmount(Math.round(transaction.getAmount() * 100.d) / 100.d);
			User sender = transaction.getSender();
			User receiver = transaction.getReceiver();
			User admin = userService.getAdmin();
			double fee = Math.round((transaction.getAmount() / 100.0 * transaction.getFee()) * 100.d) / 100.d;
			sender.setSolde(sender.getSolde() - transaction.getAmount());
			receiver.setSolde(receiver.getSolde() + transaction.getAmount() - fee);
			admin.setSolde(admin.getSolde() + fee);
			userService.updateUser(sender);
			userService.updateUser(receiver);
			userService.updateUser(admin);
			transactionRepository.save(transaction);
			Logger.info("Transaction between " + transaction.getSender().getEmail() + " and " + transaction.getReceiver().getEmail() + " of " + transaction.getAmount() + "€, " + admin.getEmail() + " perceived a tax of +" + fee + "€");
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