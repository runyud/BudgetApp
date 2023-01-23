package com.runyud.budgetapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runyud.budgetapp.domain.Category;
import com.runyud.budgetapp.repositories.CategoryDAO;

@Service
public class CategoryService {

	@Autowired
	CategoryDAO categoryDao;

	public Category saveCategory(Category category) {
		return categoryDao.save(category);
	}

	public Optional<Category> findOne(Long categoryId) {
		return categoryDao.findById(categoryId);
	}
}
