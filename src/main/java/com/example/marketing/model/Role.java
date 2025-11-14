package com.example.marketing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "role_name", nullable = false, unique = true, length = 50)
	private String roleName; // Ejemplo: "ROLE_ADMIN", "ROLE_USER"

	@Column(name = "description", length = 255)
	private String description;
}