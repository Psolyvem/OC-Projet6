package com.paymybuddy.server.service;

import com.paymybuddy.server.model.BankAccount;
import com.paymybuddy.server.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService implements IBankAccountService
{
	@Autowired
	BankAccountRepository bankAccountRepository;

	public BankAccountService()
	{

	}

	public BankAccountService(BankAccountRepository bankAccountRepository)
	{
		this.bankAccountRepository = bankAccountRepository;
	}

	@Override
	public Iterable<BankAccount> getBankAccounts()
	{
		return bankAccountRepository.findAll();
	}

	@Override
	public Optional<BankAccount> getBankAccountById(int id)
	{
		return bankAccountRepository.findById(id);
	}

	@Override
	public void createBankAccount(BankAccount bankAccount)
	{
		bankAccountRepository.save(bankAccount);
	}

	@Override
	public void updateBankAccount(BankAccount bankAccount)
	{
		bankAccountRepository.save(bankAccount);
	}

	@Override
	public void deleteBankAccount(BankAccount bankAccount)
	{
		bankAccountRepository.delete(bankAccount);
	}
}
