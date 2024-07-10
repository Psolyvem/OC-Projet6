package com.paymybuddy.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BankOperationTest
{
	BankOperation testBankOperation;

	@BeforeEach
	public void setUp()
	{
		User user = new User();
		user.setAdmin(false);
		user.setEmail("test@test.com");
		user.setSolde(100);
		user.setId(10);
		user.setPassword("test");

		BankAccount bankAccount = new BankAccount();
		bankAccount.setIban("FR7617499123451234567890153");
		bankAccount.setRib("17499 12345 12345678901 53");
		bankAccount.setUser(user);

		testBankOperation = new BankOperation();
		testBankOperation.setAmount(100);
		testBankOperation.setDate(new Date("09/07/2024"));
		testBankOperation.setBankAccount(bankAccount);
		testBankOperation.setId(10);
	}

	@Test
	public void bankOperationTests()
	{
		assertThat(testBankOperation.getId()).isEqualTo(10);
		assertThat(testBankOperation.getDate()).isEqualTo(new Date("09/07/2024"));
		assertThat(testBankOperation.getAmount()).isEqualTo(100);
		assertThat(testBankOperation.getBankAccount().getIban()).isEqualTo("FR7617499123451234567890153");
	}
}
