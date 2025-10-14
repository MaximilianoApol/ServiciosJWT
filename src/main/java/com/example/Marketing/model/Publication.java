package com.example.Marketing.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publication_api_id")
    @JsonProperty("publication_api_id")
    private Integer publicationId;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    @JsonProperty("campaign")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "author_api_id", nullable = false)
    @JsonProperty("author")
    private Author author;

    @Column(name = "text_content", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("text_content")
    private String textContent;

    @Column(name = "publication_date", nullable = false)
    @JsonProperty("publication_date")
    private OffsetDateTime publicationDate;

    @Column(name = "likes")
    @JsonProperty("likes")
    private Integer likes = 0;

    @Column(name = "comments")
    @JsonProperty("comments")
    private Integer comments = 0;

    @Column(name = "shares")
    @JsonProperty("shares")
    private Integer shares = 0;

    @Column(name = "geolocation")
    @JsonProperty("geolocation")
    private String geolocation;

    @Column(name = "publication_url", length = 512)
    @JsonProperty("publication_url")
    private String publicationUrl;

    @Column(name = "classification_priority", length = 50)
    @JsonProperty("classification_priority")
    private String classificationPriority = "Neutral";

    @Column(name = "collection_date", nullable = false)
    @JsonProperty("collection_date")
    private OffsetDateTime collectionDate = OffsetDateTime.now();

    @OneToOne(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonProperty("text_analysis")
    private TextAnalysis textAnalysis;

}
