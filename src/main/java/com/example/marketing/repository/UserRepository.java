package com.example.marketing.repository;

import java.util.Optional;

import com.example.marketing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Role, Long> {
	public Optional<Role> findByAuthority(String role);
}