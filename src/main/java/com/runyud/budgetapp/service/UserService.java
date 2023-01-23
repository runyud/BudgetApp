package com.runyud.budgetapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runyud.budgetapp.domain.Authority;
import com.runyud.budgetapp.domain.User;
import com.runyud.budgetapp.repositories.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDao;

	public User save(User user) {
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUser(user);

		Set<Authority> authorities = new HashSet<>();
		authorities.add(authority);
		user.setAuthorities(authorities);
		user = userDao.save(user);
		return user;
	}
}
