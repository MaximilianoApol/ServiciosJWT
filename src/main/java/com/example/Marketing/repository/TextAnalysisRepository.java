package com.example.Marketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.Marketing.model.TextAnalysis;

public interface TextAnalysisRepository extends JpaRepository<TextAnalysis, Integer> {

//	@Query("SELECT t FROM TextAnalysis t WHERE t.publication.publicationApiId = :publicationId")
//	List<TextAnalysis> findByPublicationId(@Param("publicationId") Integer publicationId);
//
//	// Regla 5: Contenido Viral Potencial
//	@Query("SELECT t FROM TextAnalysis t JOIN t.publication p " + "WHERE p.likes > 1000 AND p.shares > 100 AND t.feeling <> 'Negativo'")
//	List<TextAnalysis> findPotentialViralContent();
//
//	// Regla 7: Mención cruzada con competencia
//	@Query("SELECT t FROM TextAnalysis t JOIN t.publication p " + "WHERE p.textContent LIKE %:brand% AND (p.textContent LIKE %:competitorA% OR p.textContent LIKE %:competitorB%)")
//	List<TextAnalysis> findCrossMention(@Param("brand") String brand, @Param("competitorA") String competitorA, @Param("competitorB") String competitorB);
//
//	// Regla 10: Asignación al equipo de soporte
//	@Query("SELECT t FROM TextAnalysis t JOIN t.publication p " + "WHERE t.feeling = 'Negativo' AND " + "(p.textContent LIKE %:keyword1% OR p.textContent LIKE %:keyword2% OR p.textContent LIKE %:keyword3% OR p.textContent LIKE %:keyword4%)")
//	List<TextAnalysis> findSupportTickets(@Param("keyword1") String k1, @Param("keyword2") String k2, @Param("keyword3") String k3, @Param("keyword4") String k4);
//
//	// Regla 11: Detección de sarcasmo o dudas
//	@Query("SELECT t FROM TextAnalysis t JOIN t.publication p " + "WHERE t.feeling = 'Mixto' OR p.textContent LIKE %?1")
//	List<TextAnalysis> findMixedOrQuestionPosts(String symbol);
}
