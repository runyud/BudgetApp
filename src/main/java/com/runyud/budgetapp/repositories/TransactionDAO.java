package com.runyud.budgetapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runyud.budgetapp.domain.Transaction;

public interface TransactionDAO extends JpaRepository<Transaction, Long> {

}
