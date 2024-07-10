package com.paymybuddy.server.model;

import com.paymybuddy.server.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionTest
{
	Transaction testTransaction;

	@BeforeEach
	public void setUp()
	{
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
		testTransaction.setDate(new Date("07/09/2024"));
		testTransaction.setDescription("test");
		testTransaction.setReceiver(receiver);
		testTransaction.setSender(sender);
		testTransaction.setFee(0.5f);
		testTransaction.setId(10);
	}

	@Test
	public void transactionTests()
	{
		assertThat(testTransaction.getId()).isEqualTo(10);
		assertThat(testTransaction.getDate()).isEqualTo(new Date("07/09/2024"));
		assertThat(testTransaction.getDescription()).isEqualTo("test");
	}
}
