package com.example.marketing.service;

import java.util.List;
import com.example.marketing.dto.AuthorRequest;
import com.example.marketing.dto.AuthorResponse;


public interface AuthorService {

	List<AuthorResponse> getAll(int page, int pageSize);

	AuthorResponse findById(Integer authorApiId);
	AuthorResponse create(AuthorRequest request);
	AuthorResponse update(Integer authorApiId, AuthorRequest request);

	List<AuthorResponse> getNegativeInfluencers();
	List<AuthorResponse> getPotentialSpam(String keyword);
	List<AuthorResponse> findVerifiedAuthors();
	List<AuthorResponse> findPriorityInfluencers();
	List<AuthorResponse> searchByUsername(String search);
	List<AuthorResponse> getAllOrderByFollowers();
	List<AuthorResponse> findByUsernameExact(String username);
	List<AuthorResponse> findByFollowerCountRange(Integer min, Integer max, int page, int pageSize);
}