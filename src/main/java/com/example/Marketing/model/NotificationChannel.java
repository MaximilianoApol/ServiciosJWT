package com.example.Marketing.model;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification_channels")
public class NotificationChannel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "channel_id")
	@JsonProperty("channel_id")
	private Integer notificationChannelId;

	@Column(name = "channel_type", nullable = false)
	@JsonProperty("channel_type")
	private String channelType;

	@Column(name = "configuration", columnDefinition = "jsonb", nullable = false)
	@JsonProperty("configuration")
	private String configuration;

	@Column(name = "name", nullable = false)
	@JsonProperty("name")
	private String name;

	@OneToMany(mappedBy = "notificationChannel")
	private List<AlertRule> alertRules;
}
