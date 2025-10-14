package com.example.Marketing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	@JsonProperty("role_id")
	private Integer roleId;

	@Column(name = "role_name", nullable = false, unique = true, length = 50)
	@JsonProperty("role_name")
	private String roleName;

	@Column(name = "description")
	@JsonProperty("description")
	private String description;
}
