package com.example.Marketing.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "alert_rules")
public class AlertRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rule_id")
	@JsonProperty("rule_id")
	private Integer alertRuleId;

	@ManyToOne
	@JoinColumn(name = "campaign_id", nullable = false)
	@JsonProperty("campaign")
	private Campaign campaign;

	@Column(name = "rule_name", nullable = false)
	@JsonProperty("rule_name")
	private String ruleName;

	@Column(name = "description")
	@JsonProperty("description")
	private String description;

	@Column(name = "conditions_json", columnDefinition = "jsonb", nullable = false)
	@JsonProperty("conditions_json")
	private String conditionsJson;

	@ManyToOne
	@JoinColumn(name = "notification_channel_id")
	@JsonProperty("notification_channel")
	private NotificationChannel notificationChannel;

	@Column(name = "is_active", nullable = false)
	@JsonProperty("is_active")
	private Boolean active;

	@OneToMany(mappedBy = "alertRule")
	private List<GeneratedAlert> generatedAlerts;
}
