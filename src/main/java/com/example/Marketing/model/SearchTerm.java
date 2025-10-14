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
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@Table(name = "search_terms")
public class SearchTerm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "term_id")
	@JsonProperty("term_id")
	private Integer searchTermId;

	@ManyToOne
	@JoinColumn(name = "campaign_id", nullable = false)
	private Campaign campaign;

	@Column(name = "term", nullable = false, length = 255)
	private String term;

	@Column(name = "is_active", nullable = false)
	private Boolean active = true;
}
