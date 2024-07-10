package com.paymybuddy.server.model;

import com.paymybuddy.server.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BankAccountTest
{
	BankAccount testBankAccount;

	@BeforeEach
	public void setUp()
	{
		User user = new User();
		user.setAdmin(false);
		user.setEmail("test@test.com");
		user.setSolde(100);
		user.setId(10);
		user.setPassword("test");

		testBankAccount = new BankAccount();
		testBankAccount.setIban("FR7617499123451234567890153");
		testBankAccount.setRib("17499 12345 12345678901 53");
		testBankAccount.setUser(user);
		testBankAccount.setId(10);
	}

	@Test
	public void bankAccountTests()
	{
		assertThat(testBankAccount.getId()).isEqualTo(10);
		assertThat(testBankAccount.getIban()).isEqualTo("FR7617499123451234567890153");
		assertThat(testBankAccount.getRib()).isEqualTo("17499 12345 12345678901 53");
		assertThat(testBankAccount.getUser().getEmail()).isEqualTo("test@test.com");
	}
}
