package com.example.marketing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.marketing.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	// Buscar usuario por email (usado para login y validación)
	Optional<User> findByEmail(String email);

	// Verificar si existe un email
	boolean existsByEmail(String email);

	// Buscar usuarios activos
	@Query("SELECT u FROM User u WHERE u.isActive = true")
	List<User> findActiveUsers();

	// Buscar usuarios por nombre (búsqueda parcial)
	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<User> searchByName(@Param("name") String name);
}