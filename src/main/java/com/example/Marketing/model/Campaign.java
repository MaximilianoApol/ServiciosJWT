package com.example.Marketing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import  jakarta.persistence.OneToMany;
import  jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    @JsonProperty("campaign_id")
    private Integer campaignId;

    @Column(name = "campaign_name", nullable = false, length = 100)
    @JsonProperty("campaign_name")
    private String campaignName;

    @Column(name = "is_active", nullable = false)
    @JsonProperty("is_active")
    private Boolean isActive = true;

    @Column(name = "creation_date", nullable = false)
    @JsonProperty("creation_date")
    private OffsetDateTime creationDate = OffsetDateTime.now();

    // 🔹 Relación con User (creador de la campaña)
    @ManyToOne
    @JoinColumn(name = "creator_user_id", referencedColumnName = "user_id")
    @JsonProperty("creator_user")
    private User creatorUser;

    // 🔹 Relación con Publications (1 campaña → muchas publicaciones)
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonProperty("publications")
    private List<Publication> publications;
}
