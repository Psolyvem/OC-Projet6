package com.paymybuddy.server.service;

import com.paymybuddy.server.model.BankOperation;
import com.paymybuddy.server.repository.BankOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankOperationService implements IBankOperationService
{
	@Autowired
	BankOperationRepository bankOperationRepository;

	@Override
	public Iterable<BankOperation> getBankOperations()
	{
		return bankOperationRepository.findAll();
	}

	@Override
	public Optional<BankOperation> getBankOperationById(int id)
	{
		return bankOperationRepository.findById(id);
	}

	@Override
	public void createBankOperation(BankOperation bankOperation)
	{
		bankOperationRepository.save(bankOperation);
	}

	@Override
	public void updateBankOperation(BankOperation bankOperation)
	{
		bankOperationRepository.save(bankOperation);
	}

	@Override
	public void deleteBankOperation(BankOperation bankOperation)
	{
		bankOperationRepository.delete(bankOperation);
	}
}
