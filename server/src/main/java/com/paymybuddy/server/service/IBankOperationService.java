package com.paymybuddy.server.service;

import com.paymybuddy.server.model.BankOperation;

import java.util.Optional;

public interface IBankOperationService
{
	public Iterable<BankOperation> getBankOperations();

	public Optional<BankOperation> getBankOperationById(int id);

	public void createBankOperation(BankOperation bankOperation);

	public void updateBankOperation(BankOperation bankOperation);

	public void deleteBankOperation(BankOperation bankOperation);
}
