package com.example.Marketing.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.Marketing.dto.TextAnalysisRequest;
import com.example.Marketing.dto.TextAnalysisResponse;

public interface TextAnalysisService {

	List<TextAnalysisResponse> findAll();

	List<TextAnalysisResponse> findAll(int page, int pageSize);

	TextAnalysisResponse findById(Integer textAnalysisId);

	TextAnalysisResponse findByPublicationId(Integer publicationId);

	TextAnalysisResponse create(TextAnalysisRequest request);

	TextAnalysisResponse update(Integer textAnalysisId, TextAnalysisRequest request);

	void delete(Integer textAnalysisId);

	// Consultas especializadas
	List<TextAnalysisResponse> getPotentialViralContent();

	List<TextAnalysisResponse> getCrossMention(String brand, String competitorA, String competitorB);

	List<TextAnalysisResponse> getSupportTickets(String keyword1, String keyword2, String keyword3, String keyword4);

	List<TextAnalysisResponse> getMixedOrQuestionPosts(String symbol);

	List<TextAnalysisResponse> findBySentiment(String sentiment);

	List<TextAnalysisResponse> findHighCrisisScore(BigDecimal threshold);
}