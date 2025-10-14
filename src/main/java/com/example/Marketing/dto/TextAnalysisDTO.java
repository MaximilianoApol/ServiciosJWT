package com.example.Marketing.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TextAnalysisDTO {
	private Integer idtextAnalysisId;
	private String idPublicationApiId;
	private String feeling;
	private BigDecimal scoreTrustFeeling;
	private String lenguageDetected;
	private BigDecimal scoreCrisis;
	private OffsetDateTime dateAnalysis;
	private Integer publicationApiId; // relación con Publication

	// Getters y Setters
	public Integer getIdtextAnalysisId() {
		return idtextAnalysisId;
	}

	public void setIdtextAnalysisId(Integer idtextAnalysisId) {
		this.idtextAnalysisId = idtextAnalysisId;
	}

	public String getIdPublicationApiId() {
		return idPublicationApiId;
	}

	public void setIdPublicationApiId(String idPublicationApiId) {
		this.idPublicationApiId = idPublicationApiId;
	}

	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	public BigDecimal getScoreTrustFeeling() {
		return scoreTrustFeeling;
	}

	public void setScoreTrustFeeling(BigDecimal scoreTrustFeeling) {
		this.scoreTrustFeeling = scoreTrustFeeling;
	}

	public String getLenguageDetected() {
		return lenguageDetected;
	}

	public void setLenguageDetected(String lenguageDetected) {
		this.lenguageDetected = lenguageDetected;
	}

	public BigDecimal getScoreCrisis() {
		return scoreCrisis;
	}

	public void setScoreCrisis(BigDecimal scoreCrisis) {
		this.scoreCrisis = scoreCrisis;
	}

	public OffsetDateTime getDateAnalysis() {
		return dateAnalysis;
	}

	public void setDateAnalysis(OffsetDateTime dateAnalysis) {
		this.dateAnalysis = dateAnalysis;
	}

	public Integer getPublicationApiId() {
		return publicationApiId;
	}

	public void setPublicationApiId(Integer publicationApiId) {
		this.publicationApiId = publicationApiId;
	}
}
