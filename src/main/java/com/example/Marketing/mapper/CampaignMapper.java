package com.example.Marketing.mapper;

import com.example.Marketing.dto.CampaignRequest;
import com.example.Marketing.dto.CampaignResponse;
import com.example.Marketing.model.Campaign;

public class CampaignMapper {

	// Convierte de entidad → DTO de respuesta
	public static CampaignResponse toResponse(Campaign entity) {
		if (entity == null) return null;

		return CampaignResponse.builder()
				.campaignId(entity.getCampaignId())
				.campaignName(entity.getCampaignName())
				.creatorUserId(entity.getCreatorUser() != null ? entity.getCreatorUser().getUserId() : null)
				.isActive(entity.getIsActive())
				.creationDate(entity.getCreationDate())
				.build();
	}

	// Convierte de DTO de request → entidad nueva
	public static Campaign toEntity(CampaignRequest request) {
		if (request == null) return null;

		Campaign entity = new Campaign();
		entity.setCampaignName(request.campaignName());
		entity.setIsActive(request.isActive());
		// Nota: creatorUser se asigna desde el service, ya que es una relación con User.
		return entity;
	}

	// Copia los valores del request sobre una entidad existente (para updates)
	public static void copyToEntity(CampaignRequest request, Campaign entity) {
		if (request == null || entity == null) return;

		entity.setCampaignName(request.campaignName());
		entity.setIsActive(request.isActive());
		// creatorUser también puede actualizarse si se permite
	}
}
