package com.example.Marketing.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "saved_dashboard_views")
public class SavedDashboardView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "view_id")
	@JsonProperty("view_id")
	private Integer savedDashboardViewId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonProperty("user")
	private User user;

	@Column(name = "view_name", nullable = false)
	@JsonProperty("view_name")
	private String viewName;

	@Column(name = "configuration_json", columnDefinition = "jsonb", nullable = false)
	@JsonProperty("configuration_json")
	private String configurationJson;
}
