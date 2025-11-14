package com.example.marketing.dto;

import jakarta.validation.constraints.NotNull;

public record UserLoginResponse(
		@NotNull(message = "El userId es obligatorio")
		Integer userId,

		@NotNull(message = "El roleId es obligatorio")
		Integer roleId
) {}
