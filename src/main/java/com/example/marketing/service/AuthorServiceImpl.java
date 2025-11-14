package com.example.marketing.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.marketing.dto.AuthorRequest;
import com.example.marketing.dto.AuthorResponse;
import com.example.marketing.mapper.AuthorMapper;
import com.example.marketing.model.Author;
import com.example.marketing.repository.AuthorRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository repository;


	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponse> getAll(int page, int pageSize) {
		log.debug("Obteniendo autores - Página: {}, Tamaño: {}", page, pageSize);

		PageRequest pageRequest = PageRequest.of(page, pageSize);

		return repository.findAll(pageRequest)
				.getContent()
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponse> getAllOrderByFollowers() {
		log.debug("Obteniendo autores ordenados por seguidores");

		Sort sort = Sort.by(Sort.Direction.DESC, "followerCount");

		return repository.findAll(sort)
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public AuthorResponse findById(Integer authorApiId) {
		log.debug("Buscando autor con ID: {}", authorApiId);

		Author entity = repository.findById(authorApiId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Author not found: " + authorApiId));

		return AuthorMapper.toResponse(entity);
	}

	@Override
	public AuthorResponse create(AuthorRequest request) {
		log.debug("Creando nuevo autor: {}", request.username());

		// Validación de username único
		if (repository.existsByUsername(request.username())) {
			throw new IllegalArgumentException(
					"Username already exists: " + request.username());
		}

		Author saved = repository.save(AuthorMapper.toEntity(request));

		log.info("Autor creado exitosamente con ID: {}", saved.getAuthorId());
		return AuthorMapper.toResponse(saved);
	}

	@Override
	public AuthorResponse update(Integer authorApiId, AuthorRequest request) {
		log.debug("Actualizando autor con ID: {}", authorApiId);

		Author existing = repository.findById(authorApiId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Author not found: " + authorApiId));

		// Validación de username único (si cambia y ya existe)
		if (!existing.getUsername().equals(request.username())
				&& repository.existsByUsername(request.username())) {
			throw new IllegalArgumentException(
					"New username already in use: " + request.username());
		}

		// Actualizar entidad
		AuthorMapper.copyToEntity(request, existing);

		Author saved = repository.save(existing);

		log.info("Autor actualizado exitosamente: {}", saved.getAuthorId());
		return AuthorMapper.toResponse(saved);
	}


	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponse> getNegativeInfluencers() {
		log.debug("Buscando influencers negativos (Regla 1)");

		return repository.findNegativeInfluencers()
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponse> getPotentialSpam(String keyword) {
		log.debug("Buscando potencial spam con keyword: {}", keyword);

		if (keyword == null || keyword.trim().isEmpty()) {
			throw new IllegalArgumentException("Keyword cannot be empty");
		}

		return repository.findPotentialSpam(keyword)
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}


	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponse> findVerifiedAuthors() {
		log.debug("Buscando autores verificados");

		return repository.findVerifiedAuthors()
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponse> findPriorityInfluencers() {
		log.debug("Buscando influencers prioritarios");

		return repository.findPriorityInfluencers()
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponse> searchByUsername(String search) {
		log.debug("Buscando autores con username similar a: {}", search);

		if (search == null || search.trim().isEmpty()) {
			throw new IllegalArgumentException("Search term cannot be empty");
		}

		return repository.searchByUsername(search)
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	public List<AuthorResponse> findByFollowerCountRange(Integer min, Integer max, int page, int pageSize) {
		List<AuthorResponse> allAuthors = repository.findByFollowerCountRange(min, max)
				.stream()
				.map(AuthorMapper::toResponse)
				.toList();

		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, allAuthors.size());

		if (fromIndex >= allAuthors.size()) {
			return Collections.emptyList();
		}

		return allAuthors.subList(fromIndex, toIndex);
	}


	@Override
	public List<AuthorResponse> findByUsernameExact(String username) {
		List<Author> authors = repository.searchByUsername(username);

		return authors.stream()
				.map(AuthorMapper::toResponse)
				.toList();
	}



}