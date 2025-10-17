package com.example.Marketing.controller;

import com.example.Marketing.dto.UserRoleRequest;
import com.example.Marketing.dto.UserRoleResponse;
import com.example.Marketing.service.UserRoleService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

	@GetMapping
	public List<UserRoleResponse> getAll() {
		return userRoleService.getAllUserRoles();
	}

	@GetMapping("/{id}")
	public UserRoleResponse getById(@PathVariable Integer id) {
		return userRoleService.getUserRoleById(id);
	}

	@PostMapping
	public UserRoleResponse create(@RequestBody UserRoleRequest request) {
		return userRoleService.createUserRole(request);
	}

	@PutMapping("/{id}")
	public UserRoleResponse update(@PathVariable Integer id, @RequestBody UserRoleRequest request) {
		return userRoleService.updateUserRole(id, request);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		userRoleService.deleteUserRole(id);
	}
}
