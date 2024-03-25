package com.paymybuddy.server;

import com.paymybuddy.server.model.*;
import com.paymybuddy.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner
{
	@Autowired
	UserRepository userRepository;
	@Autowired
	BankAccountRepository bankAccountRepository;
	@Autowired
	BankOperationRepository bankOperationRepository;
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	ContactRepository contactRepository;

	public static void main(String[] args)
	{
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
//		Iterable<User> users = userRepository.findAll();
//		users.forEach(user -> System.out.println(user.getEmail()));

//		Iterable<BankAccount> bankAccounts = bankAccountRepository.findAll();
//		bankAccounts.forEach(bankAccount -> System.out.println(bankAccount.getUser().getEmail()));
//
//		Iterable<BankOperation> bankOperations = bankOperationRepository.findAll();
//		bankOperations.forEach(bankOperation -> System.out.println(bankOperation.getBankAccount().getUser().getEmail() + " : " + bankOperation.getAmount()));

		Iterable<Transaction> transactions = transactionRepository.findAll();
		transactions.forEach(transaction -> System.out.println(transaction.getSender().getEmail() + " to " + transaction.getReceiver().getEmail() + " : " + transaction.getFee() + " \"" + transaction.getDescription() + "\""));

		Iterable<Contact> contacts = contactRepository.findAll();
		contacts.forEach(contact -> System.out.println(contact.getUser1().getEmail() + " is a contact of " + contact.getUser2().getEmail()));
	}

}
