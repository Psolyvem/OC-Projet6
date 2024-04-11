package com.paymybuddy.server;

import com.paymybuddy.server.model.Contact;
import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner
{
	@Autowired
	IUserService userService;
	@Autowired
	IBankAccountService bankAccountService;
	@Autowired
	IBankOperationService bankOperationService;
	@Autowired
	ITransactionService transactionService;
	@Autowired
	IContactService contactService;

	public static void main(String[] args)
	{
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
//		Iterable<User> users = userService.getUsers();
//		users.forEach(user -> System.out.println(user.getEmail()));

//		System.out.println(userService.getUserByEmail("maude.passe@email.freu").getRole());
//
//		Iterable<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
//		bankAccounts.forEach(bankAccount -> System.out.println(bankAccount.getUser().getEmail()));
//
//		Iterable<BankOperation> bankOperations = bankOperationService.getOperations();
//		bankOperations.forEach(bankOperation -> System.out.println(bankOperation.getBankAccount().getUser().getEmail() + " : " + bankOperation.getAmount()));
//
//		Iterable<Transaction> transactions = transactionService.getTransactions();
//		transactions.forEach(transaction -> System.out.println(transaction.getSender().getEmail() + " to " + transaction.getReceiver().getEmail() + " : " + transaction.getFee() + " \"" + transaction.getDescription() + "\""));
//
//		Iterable<Contact> contacts = contactService.getContacts();
//		contacts.forEach(contact -> System.out.println(contact.getUser1().getEmail() + " is a contact of " + contact.getUser2().getEmail()));
	}

}
