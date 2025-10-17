package com.example.Marketing.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.Marketing.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// Buscar usuario por email (útil para autenticación)
	Optional<User> findByEmail(String email);

	// Verificar si existe un email (útil para validación)
	boolean existsByEmail(String email);

	// Buscar usuarios activos
	@Query("SELECT u FROM User u WHERE u.active = true")
	java.util.List<User> findAllActive();

	// Buscar por nombre (búsqueda parcial)
	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
	java.util.List<User> findByFullNameContaining(@Param("name") String name);
}