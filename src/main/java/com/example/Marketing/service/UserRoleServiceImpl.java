package com.example.Marketing.service.impl;

import com.example.Marketing.dto.UserRoleRequest;
import com.example.Marketing.dto.UserRoleResponse;
import com.example.Marketing.mapper.UserRoleMapper;
import com.example.Marketing.model.UserRole;
import com.example.Marketing.repository.UserRepository;
import com.example.Marketing.repository.RoleRepository;
import com.example.Marketing.repository.UserRoleRepository;
import com.example.Marketing.service.UserRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

	private final UserRoleRepository userRoleRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserRoleMapper userRoleMapper;

	@Override
	public UserRoleResponse createUserRole(UserRoleRequest request) {
		UserRole userRole = userRoleMapper.toEntity(request,
				userRepository.findById(request.userId())
						.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")),
				roleRepository.findById(request.roleId())
						.orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"))
		);

		UserRole saved = userRoleRepository.save(userRole);
		return userRoleMapper.toResponse(saved);
	}

	@Override
	public UserRoleResponse getUserRoleById(Integer id) {
		UserRole userRole = userRoleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("UserRole no encontrado"));
		return userRoleMapper.toResponse(userRole);
	}

	@Override
	public List<UserRoleResponse> getAllUserRoles() {
		return userRoleRepository.findAll()
				.stream()
				.map(userRoleMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public UserRoleResponse updateUserRole(Integer id, UserRoleRequest request) {
		UserRole existing = userRoleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("UserRole no encontrado"));

		existing.setUser(userRepository.findById(request.userId())
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")));

		existing.setRole(roleRepository.findById(request.roleId())
				.orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));

		UserRole updated = userRoleRepository.save(existing);
		return userRoleMapper.toResponse(updated);
	}

	@Override
	public void deleteUserRole(Integer id) {
		UserRole existing = userRoleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("UserRole no encontrado"));
		userRoleRepository.delete(existing);
	}
}
