package com.example.Marketing.dto;


import jakarta.validation.constraints.*;

public record CampaignRequest(
		@NotBlank(message = "El nombre de la campaña es obligatorio")
		String campaignName,

		@NotNull(message = "Debe especificarse el usuario creador")
		Integer creatorUserId,

		@NotNull(message = "Debe indicar si la campaña está activa o no")
		Boolean isActive
) {}
