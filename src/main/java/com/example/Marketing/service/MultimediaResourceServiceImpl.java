package com.example.Marketing.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.Marketing.dto.MultimediaResourceRequest;
import com.example.Marketing.dto.MultimediaResourceResponse;
import com.example.Marketing.mapper.MultimediaResourceMapper;
import com.example.Marketing.model.MultimediaResource;
import com.example.Marketing.model.Publication;
import com.example.Marketing.repository.MultimediaResourceRepository;
import com.example.Marketing.repository.PublicationRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MultimediaResourceServiceImpl implements MultimediaResourceService {

	private final MultimediaResourceRepository repository;
	private final PublicationRepository publicationRepository;

	@Override
	@Transactional(readOnly = true)
	public List<MultimediaResourceResponse> findAll() {
		return repository.findAll()
				.stream()
				.map(MultimediaResourceMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<MultimediaResourceResponse> findAll(int page, int pageSize) {
		PageRequest pageReq = PageRequest.of(page, pageSize);
		return repository.findAll(pageReq)
				.getContent()
				.stream()
				.map(MultimediaResourceMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public MultimediaResourceResponse findById(Integer resourceId) {
		MultimediaResource entity = repository.findById(resourceId)
				.orElseThrow(() -> new EntityNotFoundException("MultimediaResource not found: " + resourceId));
		return MultimediaResourceMapper.toResponse(entity);
	}

	@Override
	public MultimediaResourceResponse create(MultimediaResourceRequest request) {
		// Validar que exista la publicación
		Publication publication = publicationRepository.findById(request.publicationApiId())
				.orElseThrow(() -> new EntityNotFoundException("Publication not found: " + request.publicationApiId()));

		MultimediaResource entity = MultimediaResourceMapper.toEntity(request);
		entity.setPublication(publication);

		MultimediaResource saved = repository.save(entity);
		return MultimediaResourceMapper.toResponse(saved);
	}

	@Override
	public MultimediaResourceResponse update(Integer resourceId, MultimediaResourceRequest request) {
		MultimediaResource existing = repository.findById(resourceId)
				.orElseThrow(() -> new EntityNotFoundException("MultimediaResource not found: " + resourceId));

		// Actualizar relación con publicación si cambió
		if (!existing.getPublication().getPublicationId().equals(request.publicationApiId())) {
			Publication publication = publicationRepository.findById(request.publicationApiId())
					.orElseThrow(() -> new EntityNotFoundException("Publication not found: " + request.publicationApiId()));
			existing.setPublication(publication);
		}

		MultimediaResourceMapper.copyToEntity(request, existing);
		MultimediaResource saved = repository.save(existing);
		return MultimediaResourceMapper.toResponse(saved);
	}

	@Override
	public void delete(Integer resourceId) {
		if (!repository.existsById(resourceId)) {
			throw new EntityNotFoundException("MultimediaResource not found: " + resourceId);
		}
		repository.deleteById(resourceId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MultimediaResourceResponse> findByPublicationId(Integer publicationId) {
		return repository.findByPublicationId(publicationId)
				.stream()
				.map(MultimediaResourceMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<MultimediaResourceResponse> findByResourceType(String type) {
		return repository.findByResourceType(type)
				.stream()
				.map(MultimediaResourceMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<MultimediaResourceResponse> findWithoutVisualAnalysis() {
		return repository.findWithoutVisualAnalysis()
				.stream()
				.map(MultimediaResourceMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<MultimediaResourceResponse> findWithVisualAnalysis() {
		return repository.findWithVisualAnalysis()
				.stream()
				.map(MultimediaResourceMapper::toResponse)
				.collect(Collectors.toList());
	}
}