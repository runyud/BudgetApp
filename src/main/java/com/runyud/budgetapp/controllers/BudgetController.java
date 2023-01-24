package com.runyud.budgetapp.controllers;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.runyud.budgetapp.domain.Budget;
import com.runyud.budgetapp.domain.User;
import com.runyud.budgetapp.service.BudgetService;
import com.runyud.budgetapp.utils.Utils;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

	@Autowired
	private BudgetService budgetService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getBudget(@AuthenticationPrincipal User user, ModelMap model) {
		// this should fetch budgets from the db for the logged in user
		populateUserBudgetOnModel(user, model);
		return "budgets";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody Budget postBudget(@AuthenticationPrincipal User user, ModelMap model) {

		Budget budget = new Budget();
		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date firstOfMonth = cal.getTime();

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, 0);

		Date lastOfMonth = cal.getTime();

		budget.setStartDate(firstOfMonth);
		budget.setEndDate(lastOfMonth);
		budget = budgetService.saveBudget(user, budget);

		populateUserBudgetOnModel(user, model);
		return budget;
	}

	@PutMapping("{budgetId}")
	public @ResponseBody void putBudget(@AuthenticationPrincipal User user, @RequestParam String startDate,
			@RequestParam String endDate, @PathVariable Long budgetId) {
		Optional<Budget> budget = budgetService.findOne(budgetId);

		budget.ifPresent(existingBudget -> {
			try {
				existingBudget.setStartDate(Utils.convertStringToDate(startDate));
				existingBudget.setEndDate(Utils.convertStringToDate(endDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			budgetService.saveBudget(user, existingBudget);
		});
	}

	@GetMapping("{budgetId}")
	public String getSingleBudget(@PathVariable Long budgetId, ModelMap model) {
		Optional<Budget> budget = budgetService.findOne(budgetId);
		budget.ifPresent(existingBudget -> {
			boolean hasCategories = existingBudget.getGroups().stream()
					.filter(group -> group.getCategories().size() > 0).count() > 0;
			model.put("budget", existingBudget);
			model.put("hasCategories", hasCategories);
		});
		return "budget";
	}

	private void populateUserBudgetOnModel(User user, ModelMap model) {
		TreeSet<Budget> budgets = budgetService.getBudgets(user);
		model.put("budgets", budgets);
	}
}
