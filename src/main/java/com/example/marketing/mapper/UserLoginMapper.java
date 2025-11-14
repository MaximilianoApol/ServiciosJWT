package com.example.marketing.mapper;

import java.util.stream.Collectors;

import com.example.marketing.dto.UserLoginRequest;
import com.example.marketing.dto.UserLoginResponse;
import com.example.marketing.model.User;

public final class UserLoginMapper {

	/**
	 * Convierte User entity a UserLoginResponse DTO
	 */
	public static UserLoginResponse toResponse(User user) {
		if (user == null) return null;

		return UserLoginResponse.builder()
				.userId(user.getUserId())
				.email(user.getEmail())
				.fullName(user.getFullName())
				.roles(user.getRoles() != null
						? user.getRoles().stream()
						.map(role -> role.getRoleName())
						.collect(Collectors.toList())
						: null)
				.build();
	}

	/**
	 * Convierte UserLoginRequest DTO a User entity
	 */
	public static User toEntity(UserLoginRequest dto) {
		if (dto == null) return null;

		User user = User.builder()
				.email(dto.getEmail())
				.passwordHash(dto.getPassword())
				.build();
		return user;
	}

	/**
	 * Copia datos de UserLoginRequest a User entity existente
	 */
	public static void copyToEntity(UserLoginRequest dto, User entity) {
		if (dto == null || entity == null) return;
		entity.setEmail(dto.getEmail());
		entity.setPasswordHash(dto.getPassword());
	}
}