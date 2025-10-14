package com.example.Marketing.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import com.example.Marketing.model.Publication;
import com.example.Marketing.repository.PublicationRepository;

@Service
@Transactional
public class PublicationService {

	@Autowired
	private PublicationRepository repository;

	public List<Publication> getAll() {
		return repository.findAll();
	}

	public Publication getById(Integer idPublication) {
		return repository.findById(idPublication).orElse(null);
	}

	public Publication save(Publication publication) {
		return repository.save(publication);
	}

	// ---- Consultas Especializadas ----

	public List<Publication> getHighRiskNegativeInfluencers() {
		return repository.findHighRiskNegativeInfluencers();
	}

	public Long countNegativePublicationsByCampaign(Integer campaignId, OffsetDateTime startTime) {
		return repository.countNegativePublicationsByCampaign(campaignId, startTime);
	}

	public List<Publication> getPotentialViral() {
		return repository.findPotentialViral();
	}

	public List<Publication> getCrossMentions(String brand, String competitorA, String competitorB) {
		return repository.findCrossMentions(brand, competitorA, competitorB);
	}

	public List<Publication> getSpamLowFollowers() {
		return repository.findSpamLowFollowers("sorteo", "giveaway", "sígueme y te sigo");
	}

	public List<Publication> getSupportTickets() {
		return repository.findSupportTickets("ayuda", "no funciona", "problema", "dónde está mi pedido");
	}

	public List<Publication> getManualReviewNeeded() {
		return repository.findManualReviewNeeded("?");
	}
}
