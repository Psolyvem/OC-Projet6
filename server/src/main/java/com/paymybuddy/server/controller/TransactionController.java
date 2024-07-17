package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.service.ITransactionService;
import com.paymybuddy.server.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
public class TransactionController
{
	ITransactionService transactionService;

	public TransactionController(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}
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
		transactionService.createTransaction(transaction);
	}


}
