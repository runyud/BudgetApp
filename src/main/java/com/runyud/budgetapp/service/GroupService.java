package com.runyud.budgetapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runyud.budgetapp.domain.Budget;
import com.runyud.budgetapp.domain.Group;
import com.runyud.budgetapp.repositories.GroupDAO;

@Service
public class GroupService {

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private GroupDAO groupDao;

	public Group createNewGroup(Long budgetId) {
		Group group = new Group();

		Optional<Budget> budget = budgetService.findOne(budgetId);
		budget.ifPresent(existingBudget -> {
			group.setBudget(existingBudget);
			existingBudget.getGroups().add(group);
		});

		return save(group);
	}

	public Group save(Group group) {
		return groupDao.save(group);
	}

	public Optional<Group> findOne(Long groupId) {
		return groupDao.findById(groupId);
	}
}
