package com.runyud.budgetapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runyud.budgetapp.domain.User;

public interface UserDAO extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
