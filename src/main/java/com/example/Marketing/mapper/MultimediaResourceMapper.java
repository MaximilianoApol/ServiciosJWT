package com.example.Marketing.mapper;

import com.example.Marketing.dto.MultimediaResourceRequest;
import com.example.Marketing.dto.MultimediaResourceResponse;
import com.example.Marketing.model.MultimediaResource;

public class MultimediaResourceMapper {

	// Convierte de entidad → DTO de respuesta
	public static MultimediaResourceResponse toResponse(MultimediaResource entity) {
		if (entity == null) return null;

		return MultimediaResourceResponse.builder()
				.resourceId(entity.getMultimediaResourceId())
				.publicationApiId(entity.getPublication() != null ? entity.getPublication().getPublicationId() : null)
				.resourceType(entity.getResourceType())
				.resourceUrl(entity.getResourceUrl())
				.localStoragePath(entity.getLocalStoragePath())
				.hasVisualAnalysis(entity.getVisualAnalysis() != null)
				.build();
	}

	// Convierte de DTO de request → entidad nueva (sin relación)
	public static MultimediaResource toEntity(MultimediaResourceRequest request) {
		if (request == null) return null;

		MultimediaResource entity = new MultimediaResource();
		entity.setResourceType(request.resourceType());
		entity.setResourceUrl(request.resourceUrl());
		entity.setLocalStoragePath(request.localStoragePath());
		// La relación con Publication se establece en el servicio
		return entity;
	}

	// Copia los valores del request sobre una entidad existente (para updates)
	public static void copyToEntity(MultimediaResourceRequest request, MultimediaResource entity) {
		if (request == null || entity == null) return;

		entity.setResourceType(request.resourceType());
		entity.setResourceUrl(request.resourceUrl());
		entity.setLocalStoragePath(request.localStoragePath());
		// La relación con Publication se actualiza en el servicio si es necesario
	}
}