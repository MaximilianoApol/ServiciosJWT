package com.example.Marketing.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.Marketing.dto.CampaignRequest;
import com.example.Marketing.dto.CampaignResponse;
import com.example.Marketing.model.Campaign;
import com.example.Marketing.service.CampaignService;

@RestController
@RequestMapping("/api/v1/campaigns")
@CrossOrigin(origins = "*")
@Tag(name = "Campaigns", description = "Manage campaigns and specialized queries")
public class CampaignController {

	@Autowired
	private CampaignService service;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Get all campaigns")
	@ApiResponse(responseCode = "200", description = "Found campaigns", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CampaignResponse.class))) })
	@GetMapping
	public List<CampaignResponse> getAll() {
		return service.findAll();
	}

	@Operation(summary = "Get campaign by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Campaign found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CampaignResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Campaign not found", content = @Content) })
	@GetMapping("{id}")
	public ResponseEntity<CampaignResponse> getById(@PathVariable Integer id) {
		CampaignResponse response = service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Create a new campaign")
	@ApiResponse(responseCode = "201", description = "Campaign created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CampaignResponse.class)) })
	@PostMapping
	public ResponseEntity<CampaignResponse> create(@RequestBody CampaignRequest request) {
		CampaignResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing campaign")
	@PutMapping("{id}")
	public ResponseEntity<CampaignResponse> update(@PathVariable Integer id, @RequestBody CampaignRequest request) {
		CampaignResponse response = service.update(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Delete a campaign")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// --- Consultas especializadas ---

	@Operation(summary = "Find campaigns by name (JPQL)")
	@GetMapping("search/jpql")
	public ResponseEntity<List<CampaignResponse>> findByNameJPQL(@RequestParam String name) {
		List<Campaign> campaigns = service.findByNameJPQL(name);
		List<CampaignResponse> response = campaigns.stream()
				.map(c -> modelMapper.map(c, CampaignResponse.class))
				.toList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Find campaigns by name (Native Query)")
	@GetMapping("search/native")
	public ResponseEntity<List<CampaignResponse>> findByNameNative(@RequestParam String name) {
		List<Campaign> campaigns = service.findByNameNative(name);
		List<CampaignResponse> response = campaigns.stream()
				.map(c -> modelMapper.map(c, CampaignResponse.class))
				.toList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

