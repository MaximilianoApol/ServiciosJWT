package com.example.Marketing.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Marketing.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Integer> {
//
//	// Regla 1: Alerta de Influencer Negativo
//	@Query("SELECT p FROM Publication p JOIN p.author a JOIN p.text_analysis t "
//			+ "WHERE t.feeling = 'Negativo' AND t.scoreTrustFeeling > 0.85 AND a.followersCount > 100000")
//	List<Publication> findHighRiskNegativeInfluencers();
//
//	// Regla 2: Ola de Comentarios Negativos
//	@Query("SELECT COUNT(p) FROM Publication p JOIN p.text_analysis t "
//			+ "WHERE t.feeling = 'Negativo' AND p.campaign.campaignId = :campaignId AND p.publicationDate >= :startTime")
//	Long countNegativePublicationsByCampaign(@Param("campaignId") Integer campaignId,
//	                                         @Param("startTime") OffsetDateTime startTime);
//
//	// Regla 5: Contenido Viral Potencial
//	@Query("SELECT p FROM Publication p JOIN p.text_analysis t "
//			+ "WHERE t.feeling <> 'Negativo' AND p.likes >= 1000 AND p.shares >= 100")
//	List<Publication> findPotentialViral();
//
//	// Regla 7: Mención Cruzada con Competencia
//	@Query("SELECT p FROM Publication p "
//			+ "WHERE p.textContent LIKE %:brand% AND (p.textContent LIKE %:competitorA% OR p.textContent LIKE %:competitorB%)")
//	List<Publication> findCrossMentions(@Param("brand") String brand,
//	                                    @Param("competitorA") String competitorA,
//	                                    @Param("competitorB") String competitorB);
//
//	// Regla 9: Filtrado Automático de Spam
//	@Query("SELECT p FROM Publication p JOIN p.author a "
//			+ "WHERE (p.textContent LIKE %:keyword1% OR p.textContent LIKE %:keyword2% OR p.textContent LIKE %:keyword3%) "
//			+ "AND a.followersCount < 100")
//	List<Publication> findSpamLowFollowers(@Param("keyword1") String keyword1,
//	                                       @Param("keyword2") String keyword2,
//	                                       @Param("keyword3") String keyword3);
//
//	// Regla 10: Asignación al Equipo de Soporte
//	@Query("SELECT p FROM Publication p JOIN p.text_analysis t "
//			+ "WHERE t.feeling = 'Negativo' AND (p.textContent LIKE %:word1% OR p.textContent LIKE %:word2% OR p.textContent LIKE %:word3% OR p.textContent LIKE %:word4%)")
//	List<Publication> findSupportTickets(@Param("word1") String word1,
//	                                     @Param("word2") String word2,
//	                                     @Param("word3") String word3,
//	                                     @Param("word4") String word4);
//
//	// Regla 11: Detección de Sarcasmo o Dudas
//	@Query("SELECT p FROM Publication p JOIN p.text_analysis t "
//			+ "WHERE t.feeling = 'Mixto' OR p.textContent LIKE %?1")
//	List<Publication> findManualReviewNeeded(String endsWithQuestionMark);
}
