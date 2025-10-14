package com.example.Marketing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	@JsonProperty("user_id")
	private Integer userId;

	@Column(name = "full_name", nullable = false, length = 100)
	@JsonProperty("full_name")
	private String fullName;

	@Column(name = "email", nullable = false, unique = true, length = 100)
	@JsonProperty("email")
	private String email;

	@Column(name = "password_hash", nullable = false)
	@JsonProperty("password_hash")
	private String passwordHash;

	@Column(name = "is_active", nullable = false)
	@JsonProperty("is_active")
	private Boolean active = true;

	@Column(name = "creation_date", nullable = false)
	@JsonProperty("creation_date")
	private OffsetDateTime creationDate = OffsetDateTime.now();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonProperty("user_roles")
	private List<UserRole> userRoles;

	@OneToMany(mappedBy = "creatorUser")
	private List<Campaign> campaigns;
}
