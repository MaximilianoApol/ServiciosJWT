package com.example.Marketing.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.Marketing.dto.TextAnalysisRequest;
import com.example.Marketing.dto.TextAnalysisResponse;
import com.example.Marketing.mapper.TextAnalysisMapper;
import com.example.Marketing.model.Publication;
import com.example.Marketing.model.TextAnalysis;
import com.example.Marketing.repository.PublicationRepository;
import com.example.Marketing.repository.TextAnalysisRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TextAnalysisServiceImpl implements TextAnalysisService {

	private final TextAnalysisRepository repository;
	private final PublicationRepository publicationRepository;

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> findAll() {
		return repository.findAll()
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> findAll(int page, int pageSize) {
		PageRequest pageReq = PageRequest.of(page, pageSize);
		return repository.findAll(pageReq)
				.getContent()
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public TextAnalysisResponse findById(Integer textAnalysisId) {
		TextAnalysis entity = repository.findById(textAnalysisId)
				.orElseThrow(() -> new EntityNotFoundException("TextAnalysis not found: " + textAnalysisId));
		return TextAnalysisMapper.toResponse(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public TextAnalysisResponse findByPublicationId(Integer publicationId) {
		TextAnalysis entity = repository.findByPublicationId(publicationId)
				.orElseThrow(() -> new EntityNotFoundException("TextAnalysis not found for publication: " + publicationId));
		return TextAnalysisMapper.toResponse(entity);
	}

	@Override
	public TextAnalysisResponse create(TextAnalysisRequest request) {
		// Validar que exista la publicación
		Publication publication = publicationRepository.findById(request.publicationApiId())
				.orElseThrow(() -> new EntityNotFoundException("Publication not found: " + request.publicationApiId()));

		// Verificar que la publicación no tenga ya un análisis
		if (repository.findByPublicationId(request.publicationApiId()).isPresent()) {
			throw new IllegalArgumentException("Publication already has a text analysis: " + request.publicationApiId());
		}

		TextAnalysis entity = TextAnalysisMapper.toEntity(request);
		entity.setPublication(publication);

		TextAnalysis saved = repository.save(entity);
		return TextAnalysisMapper.toResponse(saved);
	}

	@Override
	public TextAnalysisResponse update(Integer textAnalysisId, TextAnalysisRequest request) {
		TextAnalysis existing = repository.findById(textAnalysisId)
				.orElseThrow(() -> new EntityNotFoundException("TextAnalysis not found: " + textAnalysisId));

		// Actualizar relación con publicación si cambió
		if (!existing.getPublication().getPublicationId().equals(request.publicationApiId())) {
			Publication publication = publicationRepository.findById(request.publicationApiId())
					.orElseThrow(() -> new EntityNotFoundException("Publication not found: " + request.publicationApiId()));
			existing.setPublication(publication);
		}

		TextAnalysisMapper.copyToEntity(request, existing);
		TextAnalysis saved = repository.save(existing);
		return TextAnalysisMapper.toResponse(saved);
	}

	@Override
	public void delete(Integer textAnalysisId) {
		if (!repository.existsById(textAnalysisId)) {
			throw new EntityNotFoundException("TextAnalysis not found: " + textAnalysisId);
		}
		repository.deleteById(textAnalysisId);
	}

	// --- Consultas Especializadas ---

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> getPotentialViralContent() {
		return repository.findPotentialViralContent()
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> getCrossMention(String brand, String competitorA, String competitorB) {
		return repository.findCrossMention(brand, competitorA, competitorB)
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> getSupportTickets(String keyword1, String keyword2, String keyword3, String keyword4) {
		return repository.findSupportTickets(keyword1, keyword2, keyword3, keyword4)
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> getMixedOrQuestionPosts(String symbol) {
		return repository.findMixedOrQuestionPosts(symbol)
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> findBySentiment(String sentiment) {
		return repository.findBySentiment(sentiment)
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAnalysisResponse> findHighCrisisScore(BigDecimal threshold) {
		return repository.findHighCrisisScore(threshold)
				.stream()
				.map(TextAnalysisMapper::toResponse)
				.collect(Collectors.toList());
	}
}