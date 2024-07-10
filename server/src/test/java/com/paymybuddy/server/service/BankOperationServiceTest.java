package com.paymybuddy.server.service;

import com.paymybuddy.server.model.BankAccount;
import com.paymybuddy.server.model.BankOperation;
import com.paymybuddy.server.model.User;
import com.paymybuddy.server.repository.BankOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BankOperationServiceTest
{
	IBankOperationService bankOperationService;
	@Mock
	BankOperationRepository bankOperationRepository;
	BankOperation testBankOperation;

	@BeforeEach
	public void setUp()
	{
		this.bankOperationService = new BankOperationService(this.bankOperationRepository);

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
		testBankOperation.setDate(new Date());
		testBankOperation.setBankAccount(bankAccount);
	}

	@Test
	public void getBankOperationsTest()
	{
		bankOperationService.getBankOperations();
		verify(bankOperationRepository, times(1)).findAll();
	}

	@Test
	public void getBankOperationByIdTest()
	{
		bankOperationService.getBankOperationById(1);
		verify(bankOperationRepository, times(1)).findById(1);
	}

	@Test
	public void createBankOperationTest()
	{
		bankOperationService.createBankOperation(testBankOperation);
		verify(bankOperationRepository, times(1)).save(testBankOperation);
	}

	@Test
	public void updateBankOperationTest()
	{
		bankOperationService.updateBankOperation(testBankOperation);
		verify(bankOperationRepository, times(1)).save(testBankOperation);
	}

	@Test
	public void deleteBankOperationTest()
	{
		bankOperationService.deleteBankOperation(testBankOperation);
		verify(bankOperationRepository, times(1)).delete(testBankOperation);
	}
}
