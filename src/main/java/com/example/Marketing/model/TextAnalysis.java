package com.example.Marketing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "text_analysis")
public class TextAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_analysis_id")
    @JsonProperty("text_analysis_id")
    private Integer textAnalysisId;

    // 🔹 Relación 1:1 con Publication (cada publicación tiene un análisis de texto único)
    @OneToOne
    @JoinColumn(name = "publication_api_id", referencedColumnName = "publication_api_id")
    @JsonBackReference
    @JsonProperty("publication")
    private Publication publication;

    @Column(name = "sentiment", length = 20)
    @JsonProperty("sentiment")
    private String sentiment;

    @Column(name = "sentiment_confidence_score", precision = 5, scale = 4)
    @JsonProperty("sentiment_confidence_score")
    private BigDecimal sentimentConfidenceScore;

    @Column(name = "detected_language", length = 10)
    @JsonProperty("detected_language")
    private String detectedLanguage;

    @Column(name = "crisis_score", precision = 5, scale = 2)
    @JsonProperty("crisis_score")
    private BigDecimal crisisScore = BigDecimal.ZERO;

    @Column(name = "analysis_date", nullable = false)
    @JsonProperty("analysis_date")
    private OffsetDateTime analysisDate = OffsetDateTime.now();
}
