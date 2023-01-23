package com.runyud.budgetapp.repositories;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runyud.budgetapp.domain.Budget;
import com.runyud.budgetapp.domain.User;

public interface BudgetDAO extends JpaRepository<Budget, Long> {
	TreeSet<Budget> findByUsersIn(Set<User> users);
	
	long countByUsersIn(Set<User> users);
}
