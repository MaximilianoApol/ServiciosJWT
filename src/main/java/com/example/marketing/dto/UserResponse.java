package com.example.marketing.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	@JsonProperty("user_id")
	private Integer userId;

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("email")
	private String email;

	@JsonProperty("is_active")
	private Boolean isActive;

	@JsonProperty("creation_date")
	private OffsetDateTime creationDate;

	// For detail endpoints - include role names
	@JsonProperty("roles")
	private List<String> roles;
}