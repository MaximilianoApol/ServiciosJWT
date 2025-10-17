package com.example.Marketing.controller;

import java.util.List;

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

import com.example.Marketing.dto.MultimediaResourceRequest;
import com.example.Marketing.dto.MultimediaResourceResponse;
import com.example.Marketing.service.MultimediaResourceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/multimedia-resources")
@CrossOrigin(origins = "*")
@Tag(name = "Multimedia Resources", description = "Manage multimedia resources")
public class MultimediaResourceController {

	@Autowired
	private MultimediaResourceService service;

	@Operation(summary = "Get all multimedia resources")
	@ApiResponse(responseCode = "200", description = "Found multimedia resources", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MultimediaResourceResponse.class)))})
	@GetMapping
	public List<MultimediaResourceResponse> getAll(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {
		if (page != null && size != null) {
			return service.findAll(page, size);
		}
		return service.findAll();
	}

	@Operation(summary = "Get multimedia resource by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Multimedia resource found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MultimediaResourceResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Multimedia resource not found", content = @Content) })
	@GetMapping("{id}")
	public ResponseEntity<MultimediaResourceResponse> getById(@PathVariable Integer id) {
		MultimediaResourceResponse response = service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Create a new multimedia resource")
	@ApiResponse(responseCode = "201", description = "Multimedia resource created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MultimediaResourceResponse.class)) })
	@PostMapping
	public ResponseEntity<MultimediaResourceResponse> create(@Valid @RequestBody MultimediaResourceRequest request) {
		MultimediaResourceResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing multimedia resource")
	@PutMapping("{id}")
	public ResponseEntity<MultimediaResourceResponse> update(@PathVariable Integer id, @Valid @RequestBody MultimediaResourceRequest request) {
		MultimediaResourceResponse response = service.update(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Delete a multimedia resource")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Get multimedia resources by publication")
	@GetMapping("/publication/{publicationId}")
	public ResponseEntity<List<MultimediaResourceResponse>> getByPublicationId(@PathVariable Integer publicationId) {
		return new ResponseEntity<>(service.findByPublicationId(publicationId), HttpStatus.OK);
	}

	@Operation(summary = "Get multimedia resources by type")
	@GetMapping("/type/{type}")
	public ResponseEntity<List<MultimediaResourceResponse>> getByResourceType(@PathVariable String type) {
		return new ResponseEntity<>(service.findByResourceType(type), HttpStatus.OK);
	}

	@Operation(summary = "Get multimedia resources without visual analysis")
	@GetMapping("/pending-analysis")
	public ResponseEntity<List<MultimediaResourceResponse>> getWithoutVisualAnalysis() {
		return new ResponseEntity<>(service.findWithoutVisualAnalysis(), HttpStatus.OK);
	}

	@Operation(summary = "Get multimedia resources with visual analysis")
	@GetMapping("/analyzed")
	public ResponseEntity<List<MultimediaResourceResponse>> getWithVisualAnalysis() {
		return new ResponseEntity<>(service.findWithVisualAnalysis(), HttpStatus.OK);
	}
}