package com.example.Marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {

	@JsonProperty("author_api_id")
	private Integer authorApiId;

	private String username;

	@JsonProperty("is_verified")
	private Boolean isVerified;

	@JsonProperty("follower_count")
	private Integer followerCount;

	@JsonProperty("is_priority_influencer")
	private Boolean isPriorityInfluencer;

	@JsonProperty("first_registration_date")
	private OffsetDateTime firstRegistrationDate;
}
