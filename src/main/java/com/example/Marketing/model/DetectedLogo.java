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
@Table(name = "detected_logos")
public class DetectedLogo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "logo_id")
	private Integer detectedLogoId;

	@ManyToOne
	@JoinColumn(name = "visual_analysis_id", nullable = false)
	private VisualAnalysis visualAnalysis;

	@Column(name = "detected_brand", nullable = false)
	private String detectedBrand;

	@Column(name = "bounding_box_coords")
	private String boundingBoxCoords;

	@Column(name = "confidence_score", precision = 5, scale = 4)
	private BigDecimal confidenceScore;
}
