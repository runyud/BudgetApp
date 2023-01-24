package com.runyud.budgetapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runyud.budgetapp.domain.Transaction;
import com.runyud.budgetapp.repositories.TransactionDAO;

@Service
public class TransactionService {

	@Autowired
	private TransactionDAO transactionDao;

	public Transaction save(Transaction transaction) {
		return transactionDao.save(transaction);
	}

	public Optional<Transaction> findOne(Long transactionId) {
		return transactionDao.findById(transactionId);
	}
}
