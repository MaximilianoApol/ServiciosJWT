package com.example.Marketing.mapper;

import com.example.Marketing.dto.TextAnalysisRequest;
import com.example.Marketing.dto.TextAnalysisResponse;
import com.example.Marketing.model.TextAnalysis;

import java.math.BigDecimal;

public class TextAnalysisMapper {

	// Convierte de entidad → DTO de respuesta
	public static TextAnalysisResponse toResponse(TextAnalysis entity) {
		if (entity == null) return null;

		return TextAnalysisResponse.builder()
				.textAnalysisId(entity.getTextAnalysisId())
				.publicationApiId(entity.getPublication() != null ? entity.getPublication().getPublicationId() : null)
				.publicationTextContent(entity.getPublication() != null ? entity.getPublication().getTextContent() : null)
				.sentiment(entity.getSentiment())
				.sentimentConfidenceScore(entity.getSentimentConfidenceScore())
				.detectedLanguage(entity.getDetectedLanguage())
				.crisisScore(entity.getCrisisScore())
				.analysisDate(entity.getAnalysisDate())
				.build();
	}

	// Convierte de DTO de request → entidad nueva (sin relación)
	public static TextAnalysis toEntity(TextAnalysisRequest request) {
		if (request == null) return null;

		TextAnalysis entity = new TextAnalysis();
		entity.setSentiment(request.sentiment());
		entity.setSentimentConfidenceScore(request.sentimentConfidenceScore());
		entity.setDetectedLanguage(request.detectedLanguage());
		entity.setCrisisScore(request.crisisScore() != null ? request.crisisScore() : BigDecimal.ZERO);
		// La relación con Publication se establece en el servicio
		return entity;
	}

	// Copia los valores del request sobre una entidad existente (para updates)
	public static void copyToEntity(TextAnalysisRequest request, TextAnalysis entity) {
		if (request == null || entity == null) return;

		entity.setSentiment(request.sentiment());
		entity.setSentimentConfidenceScore(request.sentimentConfidenceScore());
		entity.setDetectedLanguage(request.detectedLanguage());
		entity.setCrisisScore(request.crisisScore() != null ? request.crisisScore() : BigDecimal.ZERO);
		// La relación con Publication se actualiza en el servicio si es necesario
	}
}