package com.example.Marketing.dto;

import java.time.OffsetDateTime;

public class PublicationDTO {
	private Integer publicationApiId;
	private String textContent;
	private OffsetDateTime publicationDate;
	private Integer likes;
	private Integer comments;
	private Integer shares;
	private String geolocation;
	private String publicationUrl;
	private String classificationPriority;
	private OffsetDateTime collectionDate;
	private Integer authorApiId;
	private Integer campaignId;

	// Getters y Setters
	public Integer getPublicationApiId() {
		return publicationApiId;
	}

	public void setPublicationApiId(Integer publicationApiId) {
		this.publicationApiId = publicationApiId;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public OffsetDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(OffsetDateTime publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}

	public String getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}

	public String getPublicationUrl() {
		return publicationUrl;
	}

	public void setPublicationUrl(String publicationUrl) {
		this.publicationUrl = publicationUrl;
	}

	public String getClassificationPriority() {
		return classificationPriority;
	}

	public void setClassificationPriority(String classificationPriority) {
		this.classificationPriority = classificationPriority;
	}

	public OffsetDateTime getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(OffsetDateTime collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Integer getAuthorApiId() {
		return authorApiId;
	}

	public void setAuthorApiId(Integer authorApiId) {
		this.authorApiId = authorApiId;
	}

	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
}
