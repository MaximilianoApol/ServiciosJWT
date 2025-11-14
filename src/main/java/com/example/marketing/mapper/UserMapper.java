package com.example.marketing.mapper;

import com.example.marketing.dto.UserRequest;
import com.example.marketing.dto.UserResponse;
import com.example.marketing.model.User;

import java.util.stream.Collectors;

public class UserMapper {

	/**
	 * Convierte de entidad User a DTO UserResponse
	 */
	public static UserResponse toResponse(User entity) {
		if (entity == null) return null;

		return UserResponse.builder()
				.userId(entity.getUserId())
				.fullName(entity.getFullName())
				.email(entity.getEmail())
				.isActive(entity.getIsActive())
				.creationDate(entity.getCreationDate())
				.roles(entity.getRoles() != null
						? entity.getRoles().stream()
						.map(role -> role.getRoleName())
						.collect(Collectors.toList())
						: null)
				.build();
	}

	/**
	 * Convierte de DTO UserRequest a entidad User nueva
	 * NOTA: El password será hasheado en el servicio
	 */
	public static User toEntity(UserRequest request) {
		if (request == null) return null;

		User entity = new User();
		entity.setFullName(request.fullName());
		entity.setEmail(request.email());
		// Password se asigna sin hashear, el servicio lo hasheará
		entity.setPasswordHash(request.password());
		entity.setIsActive(request.isActive());
		return entity;
	}

	/**
	 * Copia los valores del DTO UserRequest sobre una entidad User existente
	 * Usado para actualizaciones
	 */
	public static void copyToEntity(UserRequest request, User entity) {
		if (request == null || entity == null) return;

		entity.setFullName(request.fullName());
		entity.setEmail(request.email());

		// Solo actualizar password si se proporciona uno nuevo
		if (request.password() != null && !request.password().isBlank()) {
			// Password se asigna sin hashear, el servicio lo hasheará
			entity.setPasswordHash(request.password());
		}

		entity.setIsActive(request.isActive());
	}
}