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
@Table(name = "detected_faces")
public class DetectedFace {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "face_id")
	private Integer detectedFaceId;

	@ManyToOne
	@JoinColumn(name = "visual_analysis_id", nullable = false)
	private VisualAnalysis visualAnalysis;

	@Column(name = "main_emotion")
	private String mainEmotion;

	@Column(name = "confidence_score", precision = 5, scale = 4)
	private BigDecimal confidenceScore;
}
