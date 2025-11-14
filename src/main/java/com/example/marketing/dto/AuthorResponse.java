package com.example.marketing.dto;

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

	@JsonProperty("identificator")
	private Integer authorApiId;

	private String username;

	@JsonProperty("it is verified")
	private Boolean verified;

	@JsonProperty("number of followers")
	private Integer follower;

	@JsonProperty("priority Influencer")
	private Boolean priority;

	@JsonProperty("first registration")
	private OffsetDateTime firstRegistrationDate;
}
