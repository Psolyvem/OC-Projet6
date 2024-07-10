package com.paymybuddy.server.service;

import com.paymybuddy.server.model.Transaction;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest
{
	ITransactionService transactionService;
	@Mock
	TransactionRepository transactionRepository;
	@Mock
	IUserService userService;
	Transaction testTransaction;

	@BeforeEach
	public void setUp()
	{
		this.transactionService = new TransactionService(this.transactionRepository, this.userService);

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
	public void getTransactionsByUserAdminTest()
	{
		ArrayList<Transaction> transactions = new ArrayList<>();
		transactions.add(testTransaction);


		when(userService.getUserByEmail("test@test.com")).thenReturn(testTransaction.getSender());
		when(transactionRepository.findByOrderByIdDesc()).thenReturn(transactions);

		assertThat(transactionService.getTransactionsByUser("test@test.com")).isEqualTo(transactions);
	}

	@Test
	public void getTransactionsByUserTest()
	{
		ArrayList<Transaction> transactions = new ArrayList<>();
		transactions.add(testTransaction);


		when(userService.getUserByEmail("test2@test.com")).thenReturn(testTransaction.getReceiver());
		when(transactionRepository.findByUser("test2@test.com")).thenReturn(transactions);

		assertThat(transactionService.getTransactionsByUser("test2@test.com")).isEqualTo(transactions);
	}

	@Test
	public void getTransactionByIdTest()
	{
		when(transactionRepository.findById(1)).thenReturn(Optional.of(testTransaction));
		assertThat(transactionService.getTransactionById(1)).isNotNull();
	}

	@Test
	public void createTransactionTest()
	{
		when(userService.getUserByEmail("admin@paymybuddy.fr")).thenReturn(testTransaction.getSender());

		transactionService.createTransaction(testTransaction);

		assertThat(testTransaction.getSender().getSolde()).isEqualTo(0.5d);
		assertThat(testTransaction.getReceiver().getSolde()).isEqualTo(199.5d);
	}

	@Test
	public void createTransactionInsufficientFundsTest()
	{
		testTransaction.setAmount(200);

		transactionService.createTransaction(testTransaction);

		assertThat(testTransaction.getSender().getSolde()).isEqualTo(100);
		assertThat(testTransaction.getReceiver().getSolde()).isEqualTo(100);
	}

	@Test
	public void updateTransactionTest()
	{
		transactionService.updateTransaction(testTransaction);
		verify(transactionRepository, times(1)).save(testTransaction);
	}

	@Test
	public void deleteTransactionTest()
	{
		transactionService.deleteTransaction(testTransaction);
		verify(transactionRepository, times(1)).delete(testTransaction);
	}

}
