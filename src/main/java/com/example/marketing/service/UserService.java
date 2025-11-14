package com.example.marketing.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.marketing.dto.UserRequest;
import com.example.marketing.dto.UserResponse;
import com.example.marketing.mapper.UserMapper;
import com.example.marketing.model.Role;
import com.example.marketing.model.User;
import com.example.marketing.repository.RoleRepository;
import com.example.marketing.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * Implementación de UserDetailsService para Spring Security
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Cargando usuario por email: {}", username);
		return userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
	}

	/**
	 * Obtener todos los usuarios
	 */
	@Transactional(readOnly = true)
	public List<UserResponse> findAll() {
		log.debug("Obteniendo todos los usuarios");
		return userRepository.findAll().stream()
				.map(UserMapper::toResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Obtener usuarios con paginación
	 */
	@Transactional(readOnly = true)
	public List<UserResponse> findAll(int page, int size) {
		log.debug("Obteniendo usuarios - Página: {}, Tamaño: {}", page, size);
		PageRequest pageRequest = PageRequest.of(page, size);
		return userRepository.findAll(pageRequest).getContent().stream()
				.map(UserMapper::toResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Buscar usuario por ID
	 */
	@Transactional(readOnly = true)
	public UserResponse findById(Integer id) {
		log.debug("Buscando usuario con ID: {}", id);
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + id));
		return UserMapper::toResponse(user);
	}

	/**
	 * Buscar usuario por email
	 */
	@Transactional(readOnly = true)
	public UserResponse findByEmail(String email) {
		log.debug("Buscando usuario con email: {}", email);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + email));
		return UserMapper.toResponse(user);
	}

	/**
	 * Crear nuevo usuario
	 */
	@Transactional
	public UserResponse create(UserRequest request) {
		log.info("Creando nuevo usuario: {}", request.email());

		// Validar que el email no exista
		if (userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("El email ya está registrado: " + request.email());
		}

		// Crear usuario
		User user = UserMapper.toEntity(request);
		user.setPasswordHash(passwordEncoder.encode(request.password()));

		// Asignar rol por defecto si no tiene roles
		if (user.getRoles() == null || user.getRoles().isEmpty()) {
			Role userRole = roleRepository.findByRoleName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));
			Set<Role> roles = new HashSet<>();
			roles.add(userRole);
			user.setRoles(roles);
		}

		User saved = userRepository.save(user);
		log.info("Usuario creado exitosamente con ID: {}", saved.getUserId());

		return UserMapper.toResponse(saved);
	}

	/**
	 * Actualizar usuario existente
	 */
	@Transactional
	public UserResponse update(Integer id, UserRequest request) {
		log.info("Actualizando usuario con ID: {}", id);

		User existing = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + id));

		// Validar email único si cambió
		if (!existing.getEmail().equals(request.email())
				&& userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("El nuevo email ya está en uso: " + request.email());
		}

		// Actualizar datos
		UserMapper.copyToEntity(request, existing);

		// Si se proporciona password, encriptarla
		if (request.password() != null && !request.password().isBlank()) {
			existing.setPasswordHash(passwordEncoder.encode(request.password()));
		}

		User saved = userRepository.save(existing);
		log.info("Usuario actualizado exitosamente: {}", saved.getUserId());

		return UserMapper.toResponse(saved);
	}

	/**
	 * Eliminar usuario
	 */
	@Transactional
	public void delete(Integer id) {
		log.info("Eliminando usuario con ID: {}", id);

		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("Usuario no encontrado: " + id);
		}

		userRepository.deleteById(id);
		log.info("Usuario eliminado exitosamente: {}", id);
	}

	/**
	 * Obtener usuarios activos
	 */
	@Transactional(readOnly = true)
	public List<UserResponse> findActiveUsers() {
		log.debug("Obteniendo usuarios activos");
		return userRepository.findActiveUsers().stream()
				.map(UserMapper::toResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Buscar usuarios por nombre
	 */
	@Transactional(readOnly = true)
	public List<UserResponse> searchByName(String name) {
		log.debug("Buscando usuarios con nombre: {}", name);
		return userRepository.searchByName(name).stream()
				.map(UserMapper::toResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Verificar si existe un email
	 */
	@Transactional(readOnly = true)
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
}