package com.example.marketing.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.marketing.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	// Buscar rol por nombre (útil para asignación de roles)
	Optional<Role> findByRoleName(String roleName);

	// Verificar si existe un rol por nombre
	boolean existsByRoleName(String roleName);
}