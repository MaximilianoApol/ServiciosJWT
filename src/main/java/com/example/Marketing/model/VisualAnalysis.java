package com.example.Marketing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "visual_analysis")
public class VisualAnalysis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "visual_analysis_id")
	private Integer visualAnalysisId;

	@OneToOne
	@JoinColumn(name = "resource_id", unique = true, nullable = false)
	private MultimediaResource resource;

	@Column(name = "general_context")
	private String generalContext;

	@Column(name = "sensitive_content")
	private String sensitiveContent;

	@Column(name = "ocr_text")
	private String ocrText;

	@Column(name = "analysis_date", nullable = false)
	private OffsetDateTime analysisDate = OffsetDateTime.now();

	@OneToMany(mappedBy = "visualAnalysis", cascade = CascadeType.ALL)
	private List<VisualTag> visualTags;

	@OneToMany(mappedBy = "visualAnalysis", cascade = CascadeType.ALL)
	private List<DetectedLogo> detectedLogos;

	@OneToMany(mappedBy = "visualAnalysis", cascade = CascadeType.ALL)
	private List<DetectedFace> detectedFaces;
}
