package com.example.Marketing.model;



import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "processing_queue")
public class ProcessingQueue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "queue_id")
	@JsonProperty("queue_id")
	private Integer processingQueueId;

	@Column(name = "job_type", nullable = false)
	@JsonProperty("job_type")
	private String jobType;

	@Column(name = "resource_fk_id", nullable = false)
	@JsonProperty("resource_fk_id")
	private Integer resourceFkId;

	@Column(name = "status", nullable = false)
	@JsonProperty("status")
	private String status;

	@Column(name = "attempts", nullable = false)
	@JsonProperty("attempts")
	private Integer attempts;

	@Column(name = "creation_date", nullable = false)
	@JsonProperty("creation_date")
	private OffsetDateTime creationDate;

	@Column(name = "last_attempt")
	@JsonProperty("last_attempt")
	private OffsetDateTime lastAttempt;

	@Column(name = "error_message")
	@JsonProperty("error_message")
	private String errorMessage;
}
