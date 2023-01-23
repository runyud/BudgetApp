package com.runyud.budgetapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runyud.budgetapp.domain.Group;

public interface GroupDAO extends JpaRepository<Group, Long> {

}
