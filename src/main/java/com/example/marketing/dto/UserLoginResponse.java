package com.example.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

	@JsonProperty("user_id")
	private Integer userId;

	@JsonProperty("email")
	private String email;

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("roles")
	private List<String> roles;

	@JsonProperty("token")
	private String token;

	@JsonProperty("expires_in")
	private Long expiresIn;
}