package com.runyud.budgetapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runyud.budgetapp.domain.Category;

public interface CategoryDAO extends JpaRepository<Category, Long> {

}
