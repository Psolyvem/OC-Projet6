package com.paymybuddy.server.service;

import com.paymybuddy.server.model.BankAccount;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest
{
	IBankAccountService bankAccountService;
	@Mock
	BankAccountRepository bankAccountRepository;
	BankAccount testBankAccount;

	@BeforeEach
	public void setUp()
	{
		this.bankAccountService = new BankAccountService(this.bankAccountRepository);

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
	}

	@Test
	public void getBankAccountsTest()
	{
		bankAccountService.getBankAccounts();
		verify(bankAccountRepository, times(1)).findAll();
	}

	@Test
	public void getBankAccountByIdTest()
	{
		bankAccountService.getBankAccountById(1);
		verify(bankAccountRepository, times(1)).findById(1);
	}

	@Test
	public void createBankAccountTest()
	{
		bankAccountService.createBankAccount(testBankAccount);
		verify(bankAccountRepository, times(1)).save(testBankAccount);
	}

	@Test
	public void updateBankAccountTest()
	{
		bankAccountService.updateBankAccount(testBankAccount);
		verify(bankAccountRepository, times(1)).save(testBankAccount);
	}

	@Test
	public void deleteBankAccountTest()
	{
		bankAccountService.deleteBankAccount(testBankAccount);
		verify(bankAccountRepository, times(1)).delete(testBankAccount);
	}
}
