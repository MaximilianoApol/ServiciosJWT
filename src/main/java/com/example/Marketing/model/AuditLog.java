package com.example.Marketing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "audit_logs")
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	@JsonProperty("log_id")
	private Integer auditLogId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonProperty("user")
	private User user;

	@Column(name = "action", nullable = false)
	@JsonProperty("action")
	private String action;

	@Column(name = "details", columnDefinition = "jsonb")
	@JsonProperty("details")
	private String details;

	@Column(name = "log_date", nullable = false)
	@JsonProperty("log_date")
	private OffsetDateTime logDate;
}
