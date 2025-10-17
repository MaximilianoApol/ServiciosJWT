package com.example.Marketing.mapper;


import com.example.Marketing.dto.UserRequest;
import com.example.Marketing.dto.UserResponse;
import com.example.Marketing.model.User;
import com.example.Marketing.model.UserRole;

import java.util.stream.Collectors;

public class UserMapper {

	// Convierte de entidad → DTO de respuesta
	public static UserResponse toResponse(User entity) {
		if (entity == null) return null;

		return UserResponse.builder()
				.userId(entity.getUserId())
				.fullName(entity.getFullName())
				.email(entity.getEmail())
				.isActive(entity.getActive())
				.creationDate(entity.getCreationDate())
				.roles(entity.getUserRoles() != null
						? entity.getUserRoles().stream()
						.map(ur -> ur.getRole().getRoleName())
						.collect(Collectors.toList())
						: null)
				.build();
	}

	// Convierte de DTO de request → entidad nueva (sin password hash aún)
	public static User toEntity(UserRequest request) {
		if (request == null) return null;

		User entity = new User();
		entity.setFullName(request.fullName());
		entity.setEmail(request.email());
		// Password será hasheada en el servicio
		entity.setPasswordHash(request.password());
		entity.setActive(request.isActive());
		return entity;
	}

	// Copia los valores del request sobre una entidad existente (para updates)
	public static void copyToEntity(UserRequest request, User entity) {
		if (request == null || entity == null) return;

		entity.setFullName(request.fullName());
		entity.setEmail(request.email());
		// Password será hasheada en el servicio si se proporciona
		if (request.password() != null && !request.password().isBlank()) {
			entity.setPasswordHash(request.password());
		}
		entity.setActive(request.isActive());
	}
}