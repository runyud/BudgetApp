package com.runyud.budgetapp.controllers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runyud.budgetapp.domain.Category;
import com.runyud.budgetapp.domain.Group;
import com.runyud.budgetapp.domain.Transaction;
import com.runyud.budgetapp.service.CategoryService;
import com.runyud.budgetapp.service.GroupService;

@Controller
@RequestMapping("/budgets/{budgetId}/groups/{groupId}/categories")
public class CategoryController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private CategoryService categoryService;

	@PostMapping("")
	public String postCategory(@PathVariable Long groupId) {
		Category category = new Category();

		Optional<Group> group = groupService.findOne(groupId);
		group.ifPresent(existingGroup -> {
			category.setGroup(existingGroup);
			existingGroup.getCategories().add(category);
			category.setName("Test Category");
		});

		Category savedCategory = categoryService.saveCategory(category);
		return "redirect:/budgets/" + group.get().getBudget().getId() + "/groups/" + group.get().getId()
				+ "/categories/" + savedCategory.getId();

	}

	@GetMapping("{categoryId}")
	public String getCategory(@PathVariable Long categoryId, ModelMap model) {
		Optional<Category> category = categoryService.findOne(categoryId);
		category.ifPresent(existingCategory -> {
			LocalDate startDate = existingCategory.getGroup().getBudget().getStartDate();
			LocalDate endDate = existingCategory.getGroup().getBudget().getEndDate();
			Set<Transaction> filteredTransactions = existingCategory.getTransactions().stream()
					.filter(tx -> (tx.getDate().isAfter(startDate) || tx.getDate().isEqual(startDate))
							&& (tx.getDate().isBefore(endDate) || tx.getDate().isEqual(endDate)))
					.collect(Collectors.toSet());
			model.put("filteredTransactions", filteredTransactions);
			model.put("category", existingCategory);
			model.put("group", existingCategory.getGroup());
		});
		return "category";
	}

	@PostMapping("{categoryId}")
	public String saveCategory(@ModelAttribute Category category, @PathVariable Long categoryId) {
		Optional<Category> categoryFromDB = categoryService.findOne(categoryId);
		categoryFromDB.ifPresent(existingCategory -> {
			existingCategory.setName(category.getName());
			existingCategory.setBudget(category.getBudget());
			categoryService.saveCategory(existingCategory);
		});
		return "redirect:/budgets/" + categoryFromDB.get().getGroup().getBudget().getId();
	}
}
