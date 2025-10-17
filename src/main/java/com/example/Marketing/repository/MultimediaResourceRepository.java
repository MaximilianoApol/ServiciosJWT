package com.example.Marketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Marketing.model.MultimediaResource;

public interface MultimediaResourceRepository extends JpaRepository<MultimediaResource, Integer> {

	// Buscar recursos por publicación
	@Query("SELECT m FROM MultimediaResource m WHERE m.publication.publicationId = :publicationId")
	List<MultimediaResource> findByPublicationId(@Param("publicationId") Integer publicationId);

	// Buscar recursos por tipo
	@Query("SELECT m FROM MultimediaResource m WHERE m.resourceType = :type")
	List<MultimediaResource> findByResourceType(@Param("type") String type);

	// Buscar recursos sin análisis visual
	@Query("SELECT m FROM MultimediaResource m WHERE m.visualAnalysis IS NULL")
	List<MultimediaResource> findWithoutVisualAnalysis();

	// Buscar recursos con análisis visual
	@Query("SELECT m FROM MultimediaResource m WHERE m.visualAnalysis IS NOT NULL")
	List<MultimediaResource> findWithVisualAnalysis();
}