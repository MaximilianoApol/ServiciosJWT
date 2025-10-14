package com.example.Marketing.model;


import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "generated_alerts")
public class GeneratedAlert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alert_id")
	@JsonProperty("alert_id")
	private Integer generatedAlertId;

	@ManyToOne
	@JoinColumn(name = "rule_id", nullable = false)
	@JsonProperty("alert_rule")
	private AlertRule alertRule;

	@ManyToOne
	@JoinColumn(name = "publication_api_id", nullable = false)
	@JsonProperty("publication")
	private Publication publication;

	@Column(name = "generation_date", nullable = false)
	@JsonProperty("generation_date")
	private OffsetDateTime generationDate;

	@Column(name = "status", nullable = false)
	@JsonProperty("status")
	private String status;
}
