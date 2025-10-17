package com.example.Marketing.mapper;

import com.example.Marketing.dto.PublicationRequest;
import com.example.Marketing.dto.PublicationResponse;
import com.example.Marketing.model.Publication;

public class PublicationMapper {

	// Convierte de entidad → DTO de respuesta
	public static PublicationResponse toResponse(Publication entity) {
		if (entity == null) return null;

		return PublicationResponse.builder()
				.publicationApiId(entity.getPublicationId())
				.campaignId(entity.getCampaign() != null ? entity.getCampaign().getCampaignId() : null)
				.campaignName(entity.getCampaign() != null ? entity.getCampaign().getCampaignName() : null)
				.authorApiId(entity.getAuthor() != null ? entity.getAuthor().getAuthorId() : null)
				.authorUsername(entity.getAuthor() != null ? entity.getAuthor().getUsername() : null)
				.textContent(entity.getTextContent())
				.publicationDate(entity.getPublicationDate())
				.likes(entity.getLikes())
				.comments(entity.getComments())
				.shares(entity.getShares())
				.geolocation(entity.getGeolocation())
				.publicationUrl(entity.getPublicationUrl())
				.classificationPriority(entity.getClassificationPriority())
				.collectionDate(entity.getCollectionDate())
				.sentiment(entity.getTextAnalysis() != null ? entity.getTextAnalysis().getSentiment() : null)
				.crisisScore(entity.getTextAnalysis() != null && entity.getTextAnalysis().getCrisisScore() != null
						? entity.getTextAnalysis().getCrisisScore().toString() : null)
				.build();
	}

	// Convierte de DTO de request → entidad nueva (sin relaciones)
	public static Publication toEntity(PublicationRequest request) {
		if (request == null) return null;

		Publication entity = new Publication();
		entity.setTextContent(request.textContent());
		entity.setPublicationDate(request.publicationDate());
		entity.setLikes(request.likes() != null ? request.likes() : 0);
		entity.setComments(request.comments() != null ? request.comments() : 0);
		entity.setShares(request.shares() != null ? request.shares() : 0);
		entity.setGeolocation(request.geolocation());
		entity.setPublicationUrl(request.publicationUrl());
		entity.setClassificationPriority(request.classificationPriority() != null
				? request.classificationPriority() : "Neutral");
		// Las relaciones (campaign, author) se establecen en el servicio
		return entity;
	}

	// Copia los valores del request sobre una entidad existente (para updates)
	public static void copyToEntity(PublicationRequest request, Publication entity) {
		if (request == null || entity == null) return;

		entity.setTextContent(request.textContent());
		entity.setPublicationDate(request.publicationDate());
		entity.setLikes(request.likes() != null ? request.likes() : 0);
		entity.setComments(request.comments() != null ? request.comments() : 0);
		entity.setShares(request.shares() != null ? request.shares() : 0);
		entity.setGeolocation(request.geolocation());
		entity.setPublicationUrl(request.publicationUrl());
		entity.setClassificationPriority(request.classificationPriority());
		// Las relaciones se actualizan en el servicio si es necesario
	}
}