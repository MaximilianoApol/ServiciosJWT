package com.example.Marketing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "extracted_text_entities")
public class ExtractedTextEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "entity_id")
	private Integer extractedTextEntityId;

	@ManyToOne
	@JoinColumn(name = "text_analysis_id", nullable = false)
	private TextAnalysis textAnalysis;

	@Column(name = "entity_text", nullable = false)
	private String entityText;

	@Column(name = "entity_type")
	private String entityType;

	@Column(name = "confidence_score", precision = 5, scale = 4)
	private BigDecimal confidenceScore;
}
