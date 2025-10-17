package com.example.Marketing.service;

import java.time.OffsetDateTime;
import java.util.List;

import com.example.Marketing.dto.PublicationRequest;
import com.example.Marketing.dto.PublicationResponse;

public interface PublicationService {

	List<PublicationResponse> findAll();

	List<PublicationResponse> findAll(int page, int pageSize);

	PublicationResponse findById(Integer publicationId);

	PublicationResponse create(PublicationRequest request);

	PublicationResponse update(Integer publicationId, PublicationRequest request);

	void delete(Integer publicationId);

	// Consultas especializadas
	List<PublicationResponse> getHighRiskNegativeInfluencers();

	Long countNegativePublicationsByCampaign(Integer campaignId, OffsetDateTime startTime);

	List<PublicationResponse> getPotentialViral();

	List<PublicationResponse> getCrossMentions(String brand, String competitorA, String competitorB);

	List<PublicationResponse> getSpamLowFollowers(String keyword1, String keyword2, String keyword3);

	List<PublicationResponse> getSupportTickets(String word1, String word2, String word3, String word4);

	List<PublicationResponse> getManualReviewNeeded(String symbol);

	List<PublicationResponse> findByCampaignId(Integer campaignId);

	List<PublicationResponse> findByAuthorId(Integer authorId);

	List<PublicationResponse> findByDateRange(OffsetDateTime startDate, OffsetDateTime endDate);
}