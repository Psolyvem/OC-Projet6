package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.service.ITransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		System.out.println(email);
		return transactionService.getTransactionsByUser(email);
	}

	@GetMapping(path = "/transaction", params = "id")
	public Optional<Transaction> getTransactionById(@RequestParam(name = "id") int id)
	{
		return transactionService.getTransactionById(id);
	}



}
