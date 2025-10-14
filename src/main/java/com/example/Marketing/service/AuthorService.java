package com.example.Marketing.service;

import java.util.List;
import com.example.Marketing.dto.AuthorRequest;
import com.example.Marketing.dto.AuthorResponse;
import com.example.Marketing.model.Author;

public interface AuthorService {

	List<AuthorResponse> findAll();

	AuthorResponse findById(Integer authorApiId);

	AuthorResponse create(AuthorRequest request);

	AuthorResponse update(Integer authorApiId, AuthorRequest request);

	void delete(Integer authorApiId);

	// Consultas especializadas (Reglas)
	List<AuthorResponse> getNegativeInfluencers();

	List<AuthorResponse> getPotentialSpam(String keyword);

	List<Author> findByUsernameNative(String username);

	List<Author> findByUsernameJPQL(String username);
}
