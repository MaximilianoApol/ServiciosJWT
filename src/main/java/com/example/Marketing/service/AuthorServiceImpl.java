package com.example.Marketing.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.Marketing.dto.AuthorRequest;
import com.example.Marketing.dto.AuthorResponse;
import com.example.Marketing.mapper.AuthorMapper;
import com.example.Marketing.model.Author;
import com.example.Marketing.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository repository;

	@Override
	public List<AuthorResponse> findAll() {
		return repository.findAll()
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public AuthorResponse findById(Integer authorApiId) {
		Author entity = repository.findById(authorApiId)
				.orElseThrow(() -> new EntityNotFoundException("Author not found: " + authorApiId));
		return AuthorMapper.toResponse(entity);
	}

	@Override
	public AuthorResponse create(AuthorRequest request) {
		Author saved = repository.save(AuthorMapper.toEntity(request));
		return AuthorMapper.toResponse(saved);
	}

	@Override
	public AuthorResponse update(Integer authorApiId, AuthorRequest request) {
		Author existing = repository.findById(authorApiId)
				.orElseThrow(() -> new EntityNotFoundException("Author not found: " + authorApiId));
		AuthorMapper.copyToEntity(request, existing);
		Author saved = repository.save(existing);
		return AuthorMapper.toResponse(saved);
	}

	@Override
	public void delete(Integer authorApiId) {
		if (!repository.existsById(authorApiId)) {
			throw new EntityNotFoundException("Author not found: " + authorApiId);
		}
		repository.deleteById(authorApiId);
	}

	@Override
	public List<AuthorResponse> getNegativeInfluencers() {
		return repository.findNegativeInfluencers()
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<AuthorResponse> getPotentialSpam(String keyword) {
		return repository.findPotentialSpam(keyword)
				.stream()
				.map(AuthorMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<Author> findByUsernameNative(String username) {
		return repository.findByUsernameNative(username);
	}

	@Override
	public List<Author> findByUsernameJPQL(String username) {
		return repository.findByUsernameJPQL(username);
	}
}
