package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.security.Principal;
import java.util.Optional;

@RestController
public class TransactionController
{
	@Autowired
	ITransactionService transactionService;

	@GetMapping(path = "/transaction")
	public Iterable<Transaction> getTransactionsbyUser(Principal principal)
	{
		String email = principal.getName();
		return transactionService.getTransactionsByUser(email);
	}

	@GetMapping(path = "/transaction", params = "id")
	public Optional<Transaction> getTransactionById(@RequestParam(name = "id") int id)
	{
		return transactionService.getTransactionById(id);
	}

	/**
	 * Perform a transaction
	 *
	 * @param transaction Need a request with a JSON body containing the following entries :<br>
	 *                    sender : a user <br>
	 *                    receiver : a user <br>
	 *                    amount : a double <br>
	 *                    description : a string <br>
	 *                    date : a date <br>
	 *                    fee : a float <br>
	 */
	@PostMapping(path = "/transaction")
	public void createTransaction(@RequestBody Transaction transaction)
	{
		Logger.info("Transaction between " + transaction.getSender().getEmail() + " and " + transaction.getReceiver().getEmail() + " of " + transaction.getAmount());
		transactionService.createTransaction(transaction);
	}


}
