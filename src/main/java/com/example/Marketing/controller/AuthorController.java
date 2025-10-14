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

import com.example.Marketing.dto.AuthorRequest;
import com.example.Marketing.dto.AuthorResponse;
import com.example.Marketing.model.Author;
import com.example.Marketing.service.AuthorService;

@RestController
@RequestMapping("/api/v1/authors")
@CrossOrigin(origins = "*")
@Tag(name = "Authors", description = "Manage authors and specialized queries")
public class AuthorController {

	@Autowired
	private AuthorService service;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Get all authors")
	@ApiResponse(responseCode = "200", description = "Found authors", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class)))})
	@GetMapping
	public List<AuthorResponse> getAll() {
		return service.findAll();
	}

	@Operation(summary = "Get author by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Author not found", content = @Content) })
	@GetMapping("{id}")
	public ResponseEntity<AuthorResponse> getById(@PathVariable Integer id) {
		AuthorResponse response = service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Create a new author")
	@ApiResponse(responseCode = "201", description = "Author created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)) })
	@PostMapping
	public ResponseEntity<AuthorResponse> create(@RequestBody AuthorRequest request) {
		AuthorResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing author")
	@PutMapping("{id}")
	public ResponseEntity<AuthorResponse> update(@PathVariable Integer id, @RequestBody AuthorRequest request) {
		AuthorResponse response = service.update(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Delete an author")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// --- Consultas especializadas ---

	@Operation(summary = "Get negative influencers")
	@GetMapping("alerts/negative-influencers")
	public ResponseEntity<List<AuthorResponse>> negativeInfluencers() {
		return new ResponseEntity<>(service.getNegativeInfluencers(), HttpStatus.OK);
	}

	@Operation(summary = "Get potential spam authors by keyword")
	@GetMapping("alerts/potential-spam")
	public ResponseEntity<List<AuthorResponse>> potentialSpam(@RequestParam String keyword) {
		return new ResponseEntity<>(service.getPotentialSpam(keyword), HttpStatus.OK);
	}

	// --- Ejemplo de búsqueda por username con repositorio nativo o JPQL ---
	@Operation(summary = "Find authors by username (JPQL)")
	@GetMapping("search/jpql")
	public ResponseEntity<List<AuthorResponse>> findByUsernameJPQL(@RequestParam String username) {
		List<Author> authors = service.findByUsernameJPQL(username);
		List<AuthorResponse> response = authors.stream()
				.map(a -> modelMapper.map(a, AuthorResponse.class))
				.toList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Find authors by username (Native Query)")
	@GetMapping("search/native")
	public ResponseEntity<List<AuthorResponse>> findByUsernameNative(@RequestParam String username) {
		List<Author> authors = service.findByUsernameNative(username);
		List<AuthorResponse> response = authors.stream()
				.map(a -> modelMapper.map(a, AuthorResponse.class))
				.toList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
