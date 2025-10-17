package com.example.Marketing.mapper;

import com.example.Marketing.dto.RoleRequest;
import com.example.Marketing.dto.RoleResponse;
import com.example.Marketing.model.Role;

public class RoleMapper {

	// Convierte de entidad → DTO de respuesta
	public static RoleResponse toResponse(Role entity) {
		if (entity == null) return null;

		return RoleResponse.builder()
				.roleId(entity.getRoleId())
				.roleName(entity.getRoleName())
				.description(entity.getDescription())
				.build();
	}

	// Convierte de DTO de request → entidad nueva
	public static Role toEntity(RoleRequest request) {
		if (request == null) return null;

		Role entity = new Role();
		entity.setRoleName(request.roleName());
		entity.setDescription(request.description());
		return entity;
	}

	// Copia los valores del request sobre una entidad existente (para updates)
	public static void copyToEntity(RoleRequest request, Role entity) {
		if (request == null || entity == null) return;

		entity.setRoleName(request.roleName());
		entity.setDescription(request.description());
	}
}