package com.example.Marketing.repository;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.Marketing.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

	@Query(value = "SELECT * FROM campaigns WHERE campaign_name = :name", nativeQuery = true)
	List<Campaign> findByNameNative(@Param("name") String name);

	@Query("SELECT c FROM Campaign c WHERE c.campaignName = :name")
	List<Campaign> findByNameJPQL(@Param("name") String name);

//	// Regla 2: Ola de comentarios negativos
//	@Query("SELECT c FROM Campaign c JOIN c.publications p JOIN p.text_analysis t " +
//			"WHERE t.feeling = 'Negativo' AND p.publicationDate >= :fromTime " +
//			"GROUP BY c HAVING COUNT(p) > 50")
//	List<Campaign> findHighNegativeVolume(@Param("fromTime") OffsetDateTime fromTime);
}
