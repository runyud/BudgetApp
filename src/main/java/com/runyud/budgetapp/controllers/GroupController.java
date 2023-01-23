package com.runyud.budgetapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runyud.budgetapp.domain.Group;
import com.runyud.budgetapp.service.GroupService;

@Controller
@RequestMapping("/budgets/{budgetId}/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping("")
	public String postGroups(@PathVariable Long budgetId, ModelMap model) {
		Group group = groupService.createNewGroup(budgetId);

		return "redirect:/budgets/" + budgetId + "/groups/" + group.getId();

	}

	@GetMapping("{groupId}")
	public String getGroup(@PathVariable Long groupId, ModelMap model) {
		Optional<Group> group = groupService.findOne(groupId);
		group.ifPresent(existingGroup -> {
			model.put("group", existingGroup);
		});
		return "group";
	}

	@PostMapping("{groupId}")
	public String postGroup(@ModelAttribute Group group, @PathVariable Long groupId, @PathVariable Long budgetId,
			ModelMap model) {
		Optional<Group> groupFromDB = groupService.findOne(groupId);
		groupFromDB.ifPresent(existingGroup -> {
			existingGroup.setName(group.getName());
			groupService.save(existingGroup);
		}); 
		return "redirect:/budgets/" + budgetId;
	}
}
