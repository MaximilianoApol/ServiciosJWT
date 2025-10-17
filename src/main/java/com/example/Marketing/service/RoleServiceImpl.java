package com.example.Marketing.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.Marketing.dto.RoleRequest;
import com.example.Marketing.dto.RoleResponse;
import com.example.Marketing.mapper.RoleMapper;
import com.example.Marketing.model.Role;
import com.example.Marketing.repository.RoleRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

	private final RoleRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<RoleResponse> findAll() {
		return repository.findAll()
				.stream()
				.map(RoleMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public RoleResponse findById(Integer roleId) {
		Role entity = repository.findById(roleId)
				.orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
		return RoleMapper.toResponse(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public RoleResponse findByName(String roleName) {
		Role entity = repository.findByRoleName(roleName)
				.orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
		return RoleMapper.toResponse(entity);
	}

	@Override
	public RoleResponse create(RoleRequest request) {
		// Validar nombre único
		if (repository.existsByRoleName(request.roleName())) {
			throw new IllegalArgumentException("Role name already exists: " + request.roleName());
		}

		Role entity = RoleMapper.toEntity(request);
		Role saved = repository.save(entity);
		return RoleMapper.toResponse(saved);
	}

	@Override
	public RoleResponse update(Integer roleId, RoleRequest request) {
		Role existing = repository.findById(roleId)
				.orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));

		// Validar nombre único si cambió
		if (!existing.getRoleName().equals(request.roleName()) &&
				repository.existsByRoleName(request.roleName())) {
			throw new IllegalArgumentException("Role name already exists: " + request.roleName());
		}

		RoleMapper.copyToEntity(request, existing);
		Role saved = repository.save(existing);
		return RoleMapper.toResponse(saved);
	}

	@Override
	public void delete(Integer roleId) {
		if (!repository.existsById(roleId)) {
			throw new EntityNotFoundException("Role not found: " + roleId);
		}
		repository.deleteById(roleId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByName(String roleName) {
		return repository.existsByRoleName(roleName);
	}
}