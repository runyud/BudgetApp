package com.runyud.budgetapp.service;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.runyud.budgetapp.domain.Budget;
import com.runyud.budgetapp.domain.User;
import com.runyud.budgetapp.repositories.BudgetDAO;

@Service
public class BudgetService {
	
	@Autowired
	private BudgetDAO budgetDao;
	
	public TreeSet<Budget> getBudgets(@AuthenticationPrincipal User user) {
		Set<User> users = new HashSet<>();
		users.add(user);
		
		return budgetDao.findByUsersIn(users);
	}
	
	public Budget saveBudget(User user, Budget budget) {
		Set<User> users = new HashSet<>();
		Set<Budget> budgets = new HashSet<>();

		users.add(user);

		budgets.add(budget);

		long count = getBudgetCount(users);
		
		budget.setName("New Budget #" + ++count);
		budget.setUsers(users);

		user.setBudgets(budgets);
		return budgetDao.save(budget);
	}

	private long getBudgetCount(Set<User> users) {
		return budgetDao.countByUsersIn(users);
	}
}
