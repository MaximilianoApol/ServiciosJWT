package com.example.marketing.dto;

import jakarta.validation.constraints.*;

public record UserRequest(
		@NotBlank(message = "El nombre completo es obligatorio")
		@Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
		String fullName,

		@NotBlank(message = "El email es obligatorio")
		@Email(message = "El formato del email no es válido")
		@Size(max = 100, message = "El email no puede exceder 100 caracteres")
		String email,

		@NotBlank(message = "La contraseña es obligatoria")
		@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
		String password,

		@NotNull(message = "Debe indicar si el usuario está activo")
		Boolean isActive
) {}