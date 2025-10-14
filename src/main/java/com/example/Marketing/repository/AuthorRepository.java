package com.example.Marketing.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.Marketing.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query(value = "SELECT * FROM authors WHERE username = :username", nativeQuery = true)
	List<Author> findByUsernameNative(@Param("username") String username);

	@Query("SELECT a FROM Author a WHERE a.username = :username")
	List<Author> findByUsernameJPQL(@Param("username") String username);

	// Regla 1: Influencer negativo con seguidores > 100000 y score confianza > 0.85
	@Query("SELECT a FROM Author a JOIN a.publications p JOIN p.text_analysis t " +
			"WHERE t.feeling = 'Negativo' AND t.scoreTrustFeeling > 0.85 AND a.followersCount > 100000")
	List<Author> findNegativeInfluencers();

	// Regla 9: Filtrado de spam
	@Query("SELECT a FROM Author a JOIN a.publications p " +
			"WHERE (p.textContent LIKE %:keyword%) AND a.followersCount < 100")
	List<Author> findPotentialSpam(@Param("keyword") String keyword);
}
