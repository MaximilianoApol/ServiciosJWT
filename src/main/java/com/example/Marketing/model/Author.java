package com.example.Marketing.model;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import  jakarta.persistence.OneToMany;
import  jakarta.persistence.CascadeType;
import  jakarta.persistence.FetchType;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_api_id")
    @JsonProperty("author_api_id")
    private Integer authorId;

    @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(name = "is_verified", nullable = false)
    @JsonProperty("is_verified")
    private Boolean isVerified = false;

    @Column(name = "follower_count")
    @JsonProperty("follower_count")
    private Integer followerCount = 0;

    @Column(name = "is_priority_influencer", nullable = false)
    @JsonProperty("is_priority_influencer")
    private Boolean isPriorityInfluencer = false;

    @Column(name = "first_registration_date", nullable = false)
    @JsonProperty("first_registration_date")
    private OffsetDateTime firstRegistrationDate = OffsetDateTime.now();

    // Relación uno a muchos con publicaciones
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("publications")
    private List<Publication> publications;
}

