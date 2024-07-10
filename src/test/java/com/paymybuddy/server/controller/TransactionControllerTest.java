package com.paymybuddy.server.controller;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest
{
	TransactionController transactionController;
	@Mock
	TransactionService transactionService;
	Transaction testTransaction;

	@BeforeEach
	public void setUp()
	{
		this.transactionController = new TransactionController(this.transactionService);

		User sender = new User();
		sender.setAdmin(true);
		sender.setEmail("test@test.com");
		sender.setSolde(100);
		sender.setId(10);
		sender.setPassword("test");

		User receiver = new User();
		receiver.setAdmin(false);
		receiver.setEmail("test2@test.com");
		receiver.setSolde(100);
		receiver.setId(11);
		receiver.setPassword("test");

		this.testTransaction = new Transaction();
		testTransaction.setAmount(100);
		testTransaction.setDate(new Date());
		testTransaction.setDescription("test");
		testTransaction.setReceiver(receiver);
		testTransaction.setSender(sender);
		testTransaction.setFee(0.5f);
	}

	@Test
	public void getTransactionsByUserTest()
	{
		ArrayList<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);
		when(transactionService.getTransactionsByUser("test@test.com")).thenReturn(testTransactionList);
		assertThat(transactionController.getTransactionsbyUser(new Principal()
		{
			@Override
			public String getName()
			{
				return "test@test.com";
			}
		})).isEqualTo(testTransactionList);
	}

	@Test
	public void getTransactionByIdTest()
	{
		when(transactionService.getTransactionById(1)).thenReturn(Optional.of(testTransaction));
		assertThat(transactionController.getTransactionById(1)).isNotNull();
	}

	@Test
	public void createTransactionTest()
	{
		transactionController.createTransaction(testTransaction);
		verify(transactionService, times(1)).createTransaction(testTransaction);
	}

}
