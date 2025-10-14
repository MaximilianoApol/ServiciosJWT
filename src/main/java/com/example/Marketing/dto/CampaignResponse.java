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
public class CampaignResponse {

    @JsonProperty("campaign_id")
    private Integer campaignId;

    @JsonProperty("campaign_name")
    private String campaignName;

    @JsonProperty("creator_user_id")
    private Integer creatorUserId;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;
}
