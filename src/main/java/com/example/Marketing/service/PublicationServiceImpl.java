package com.example.Marketing.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.Marketing.dto.PublicationRequest;
import com.example.Marketing.dto.PublicationResponse;
import com.example.Marketing.mapper.PublicationMapper;
import com.example.Marketing.model.Author;
import com.example.Marketing.model.Campaign;
import com.example.Marketing.model.Publication;
import com.example.Marketing.repository.AuthorRepository;
import com.example.Marketing.repository.CampaignRepository;
import com.example.Marketing.repository.PublicationRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicationServiceImpl implements PublicationService {

	private final PublicationRepository repository;
	private final CampaignRepository campaignRepository;
	private final AuthorRepository authorRepository;

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> findAll() {
		return repository.findAll()
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> findAll(int page, int pageSize) {
		PageRequest pageReq = PageRequest.of(page, pageSize);
		return repository.findAll(pageReq)
				.getContent()
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public PublicationResponse findById(Integer publicationId) {
		Publication entity = repository.findById(publicationId)
				.orElseThrow(() -> new EntityNotFoundException("Publication not found: " + publicationId));
		return PublicationMapper.toResponse(entity);
	}

	@Override
	public PublicationResponse create(PublicationRequest request) {
		// Validar que existan campaign y author
		Campaign campaign = campaignRepository.findById(request.campaignId())
				.orElseThrow(() -> new EntityNotFoundException("Campaign not found: " + request.campaignId()));

		Author author = authorRepository.findById(request.authorApiId())
				.orElseThrow(() -> new EntityNotFoundException("Author not found: " + request.authorApiId()));

		Publication entity = PublicationMapper.toEntity(request);
		entity.setCampaign(campaign);
		entity.setAuthor(author);

		Publication saved = repository.save(entity);
		return PublicationMapper.toResponse(saved);
	}

	@Override
	public PublicationResponse update(Integer publicationId, PublicationRequest request) {
		Publication existing = repository.findById(publicationId)
				.orElseThrow(() -> new EntityNotFoundException("Publication not found: " + publicationId));

		// Actualizar relaciones si cambiaron
		if (!existing.getCampaign().getCampaignId().equals(request.campaignId())) {
			Campaign campaign = campaignRepository.findById(request.campaignId())
					.orElseThrow(() -> new EntityNotFoundException("Campaign not found: " + request.campaignId()));
			existing.setCampaign(campaign);
		}

		if (!existing.getAuthor().getAuthorId().equals(request.authorApiId())) {
			Author author = authorRepository.findById(request.authorApiId())
					.orElseThrow(() -> new EntityNotFoundException("Author not found: " + request.authorApiId()));
			existing.setAuthor(author);
		}

		PublicationMapper.copyToEntity(request, existing);
		Publication saved = repository.save(existing);
		return PublicationMapper.toResponse(saved);
	}

	@Override
	public void delete(Integer publicationId) {
		if (!repository.existsById(publicationId)) {
			throw new EntityNotFoundException("Publication not found: " + publicationId);
		}
		repository.deleteById(publicationId);
	}

	// --- Consultas Especializadas ---

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> getHighRiskNegativeInfluencers() {
		return repository.findHighRiskNegativeInfluencers()
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Long countNegativePublicationsByCampaign(Integer campaignId, OffsetDateTime startTime) {
		return repository.countNegativePublicationsByCampaign(campaignId, startTime);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> getPotentialViral() {
		return repository.findPotentialViral()
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> getCrossMentions(String brand, String competitorA, String competitorB) {
		return repository.findCrossMentions(brand, competitorA, competitorB)
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> getSpamLowFollowers(String keyword1, String keyword2, String keyword3) {
		return repository.findSpamLowFollowers(keyword1, keyword2, keyword3)
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> getSupportTickets(String word1, String word2, String word3, String word4) {
		return repository.findSupportTickets(word1, word2, word3, word4)
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> getManualReviewNeeded(String symbol) {
		return repository.findManualReviewNeeded(symbol)
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> findByCampaignId(Integer campaignId) {
		return repository.findByCampaignId(campaignId)
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> findByAuthorId(Integer authorId) {
		return repository.findByAuthorId(authorId)
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicationResponse> findByDateRange(OffsetDateTime startDate, OffsetDateTime endDate) {
		return repository.findByDateRange(startDate, endDate)
				.stream()
				.map(PublicationMapper::toResponse)
				.collect(Collectors.toList());
	}
}