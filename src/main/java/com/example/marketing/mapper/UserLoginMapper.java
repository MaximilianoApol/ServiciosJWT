package com.example.Marketing.mapper;

import com.example.Marketing.model.UserRole;
import com.example.Marketing.model.User;
import com.example.Marketing.model.Role;
import com.example.Marketing.dto.UserRoleRequest;
import com.example.Marketing.dto.UserRoleResponse;

import org.springframework.stereotype.Component;

@Component
public class UserLoginMapper {


	public UserRoleResponse toResponse(UserRole userRole) {
		if (userRole == null) return null;

		UserRoleResponse response = new UserRoleResponse();
		response.setUserRoleId(userRole.getUserRoleId());
		response.setUserId(userRole.getUser() != null ? userRole.getUser().getUserId() : null);
		response.setRoleId(userRole.getRole() != null ? userRole.getRole().getRoleId() : null);
		return response;
	}


	public UserRole toEntity(UserRoleRequest request, User user, Role role) {
		if (request == null) return null;

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		return userRole;
	}

	public void updateEntity(UserRole userRole, User user, Role role) {
		if (userRole == null) return;

		userRole.setUser(user);
		userRole.setRole(role);
	}
}
