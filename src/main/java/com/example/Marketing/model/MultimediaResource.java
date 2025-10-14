package com.example.Marketing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import  jakarta.persistence.OneToOne;
import  jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "multimedia_resources")
public class MultimediaResource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resource_id")
	private Integer multimediaResourceId;

	@ManyToOne
	@JoinColumn(name = "publication_api_id", nullable = false)
	private Publication publication;

	@Column(name = "resource_type", nullable = false, length = 10)
	private String resourceType;

	@Column(name = "resource_url", nullable = false)
	private String resourceUrl;

	@Column(name = "local_storage_path")
	private String localStoragePath;

	@OneToOne(mappedBy = "resource", cascade = CascadeType.ALL)
	private VisualAnalysis visualAnalysis;
}
