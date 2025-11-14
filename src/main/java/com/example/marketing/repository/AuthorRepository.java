package com.example.marketing.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.marketing.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

//	 Verificar si existe un username
	boolean existsByUsername(String username);

//	 Buscar autores verificados
	@Query("SELECT a FROM Author a WHERE a.isVerified = true")
	List<Author> findVerifiedAuthors();

//	  Buscar influencers prioritarios
	@Query("SELECT a FROM Author a WHERE a.isPriorityInfluencer = true ORDER BY a.followerCount DESC")
	List<Author> findPriorityInfluencers();

//	 Encuentra autores con publicaciones negativas, alta confianza y muchos seguidores
	@Query("SELECT DISTINCT a FROM Author a " +
			"JOIN a.publications p " +
			"JOIN p.textAnalysis t " +
			"WHERE t.sentiment = 'Negativo' " +
			"AND t.sentimentConfidenceScore > 0.85 " +
			"AND a.followerCount > 100000")
	List<Author> findNegativeInfluencers();

//	 Encuentra autores con bajo número de seguidores que publican contenido con keywords específicos
	@Query("SELECT DISTINCT a FROM Author a " +
			"JOIN a.publications p " +
			"WHERE LOWER(p.textContent) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"AND a.followerCount < 100")
	List<Author> findPotentialSpam(@Param("keyword") String keyword);

//	 Buscar autores por rango de seguidores
	@Query("SELECT a FROM Author a WHERE a.followerCount BETWEEN :min AND :max ORDER BY a.followerCount DESC")
	List<Author> findByFollowerCountRange(@Param("min") Integer min, @Param("max") Integer max);

//	/**
//	 * Buscar autores con más de X publicaciones
//	 */
//	@Query("SELECT a FROM Author a WHERE SIZE(a.publications) > :minPublications")
//	List<Author> findAuthorsWithMinPublications(@Param("minPublications") int minPublications);


//	Buscar autores por username parcial
	@Query("SELECT a FROM Author a WHERE LOWER(a.username) LIKE LOWER(CONCAT('%', :search, '%'))")
	List<Author> searchByUsername(@Param("search") String search);
}