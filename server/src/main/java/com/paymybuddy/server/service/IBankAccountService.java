package com.paymybuddy.server.service;

import com.paymybuddy.server.model.BankAccount;

import java.util.Optional;

public interface IBankAccountService
{
	public Iterable<BankAccount> getBankAccounts();

	public Optional<BankAccount> getBankAccountById(int id);

	public void createBankAccount(BankAccount bankAccount);

	public void updateBankAccount(BankAccount bankAccount);

	public void deleteBankAccount(BankAccount bankAccount);
}
