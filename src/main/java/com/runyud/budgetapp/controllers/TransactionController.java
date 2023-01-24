package com.runyud.budgetapp.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runyud.budgetapp.domain.Budget;
import com.runyud.budgetapp.domain.Category;
import com.runyud.budgetapp.domain.Transaction;
import com.runyud.budgetapp.service.BudgetService;
import com.runyud.budgetapp.service.CategoryService;
import com.runyud.budgetapp.service.TransactionService;

@Controller
@RequestMapping(value = { "/budgets/{budgetId}/groups/{groupId}/categories/{categoryId}/transactions",
		"/budgets/{budgetId}/transactions" })
public class TransactionController {

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("")
	public String addTransactionToBudget(@PathVariable Long budgetId, @PathVariable(required = false) Long groupId,
			@PathVariable(required = false) Long categoryId) {

		Transaction transaction = new Transaction();
		Optional<Budget> budget = budgetService.findOne(budgetId);
		budget.ifPresent(existingBudget -> {
			transaction.setBudget(existingBudget);
			existingBudget.getTransactions().add(transaction);
		});

		transaction.setDate(new Date());

		if (categoryId != null) {
			Optional<Category> category = categoryService.findOne(categoryId);

			if (category.isPresent()) {
				Category existingCategory = category.get();

				transaction.setCategory(existingCategory);
				existingCategory.getTransactions().add(transaction);
			}
		}

		Transaction savedTransaction = transactionService.save(transaction);

		return "redirect:/budgets/" + budgetId + "/transactions/" + savedTransaction.getId();
	}

	@GetMapping("{transactionId}")
	public String getTransaction(@PathVariable Long transactionId, ModelMap model) {
		Optional<Transaction> transaction = transactionService.findOne(transactionId);
		transaction.ifPresent(existingTransaction -> {
			model.put("transaction", existingTransaction);
			model.put("budget", existingTransaction.getBudget());
		});
		return "transaction";
	}

	@PostMapping("{transactionId}")
	public String postTransaction(@ModelAttribute Transaction transaction, @PathVariable Long transactionId) {
		transaction = transactionService.save(transaction);
		return "redirect:/budgets/" + transaction.getBudget().getId();
	}
}
