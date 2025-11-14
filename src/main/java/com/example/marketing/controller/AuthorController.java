package com.example.marketing.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.marketing.dto.AuthorRequest;
import com.example.marketing.dto.AuthorResponse;
import com.example.marketing.service.AuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/authors")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@Tag(name = "Authors", description = "Provides methods for managing authors")
@RequiredArgsConstructor
@Validated
public class AuthorController {

	private final AuthorService service;

	@Operation(summary = "Get all authors with pagination")
	@ApiResponse(responseCode = "200", description = "Authors found",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping(params = {"page", "pageSize"})
	public ResponseEntity<List<AuthorResponse>> getAllPaginated(
			@Parameter(description = "Page number (starts at 0)", example = "0")
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@Parameter(description = "Page size", example = "10")
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return ResponseEntity.ok(service.getAll(page, pageSize));
	}


	@Operation(summary = "Get all authors order by followers")
	@ApiResponse(responseCode = "200", description = "Found authors ordered by follower count",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping("/orderByFollowers")
	public ResponseEntity<List<AuthorResponse>> getAllOrderByFollowers() {
		return ResponseEntity.ok(service.getAllOrderByFollowers());
	}

	@Operation(summary = "Get an author by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author found",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = AuthorResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid author id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Author not found",
					content = @Content)
	})
	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponse> getById(
			@Parameter(description = "ID del autor", required = true)
			@PathVariable Integer id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	@Operation(summary = "Register an author")
	@ApiResponse(responseCode = "201", description = "Registered author",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = AuthorResponse.class)))
	@PostMapping
	public ResponseEntity<AuthorResponse> create(
			@Valid @RequestBody AuthorRequest request) {
		AuthorResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing author")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author updated successfully",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = AuthorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Author not found",
					content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid data or username already in use",
					content = @Content)
	})
	@PutMapping("/{id}")
	public ResponseEntity<AuthorResponse> update(
			@Parameter(description = "ID del autor", required = true)
			@PathVariable Integer id,
			@Valid @RequestBody AuthorRequest request) {
		AuthorResponse response = service.update(id, request);
		return ResponseEntity.ok(response);
	}



	@Operation(summary = "Get negative influencers with pagination",
			description = "Find influencers with high-confidence negative publications (>0.85) and many followers (>100K)")
	@ApiResponse(responseCode = "200", description = "List of negative influencers",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping(value = "/alerts/negative-influencers", params = {"page", "pageSize"})
	public ResponseEntity<List<AuthorResponse>> getNegativeInfluencersPaginated(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		List<AuthorResponse> all = service.getNegativeInfluencers();
		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, all.size());
		if (fromIndex >= all.size()) return ResponseEntity.ok(Collections.emptyList());

		return ResponseEntity.ok(all.subList(fromIndex, toIndex));
	}

	@Operation(summary = "Detect potential spam",
			description = "Find authors with low follower count (<100) publishing content with suspicious keywords")
	@ApiResponse(responseCode = "200", description = "List of potential spammers",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping("/alerts/potential-spam")
	public ResponseEntity<List<AuthorResponse>> getPotentialSpam(
			@Parameter(description = "Keyword to search in publications", required = true)
			@RequestParam String keyword) {
		List<AuthorResponse> authors = service.getPotentialSpam(keyword);
		return ResponseEntity.ok(authors);
	}

	@Operation(summary = "Get verified authors with pagination")
	@ApiResponse(responseCode = "200", description = "List of verified authors",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping(value = "/verified", params = {"page", "pageSize"})
	public ResponseEntity<List<AuthorResponse>> getVerifiedAuthorsPaginated(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		List<AuthorResponse> all = service.findVerifiedAuthors();
		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, all.size());
		if (fromIndex >= all.size()) return ResponseEntity.ok(Collections.emptyList());

		return ResponseEntity.ok(all.subList(fromIndex, toIndex));
	}

	@Operation(summary = "Get priority influencers with pagination")
	@ApiResponse(responseCode = "200", description = "List of priority influencers",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping(value = "/priority-influencers", params = {"page", "pageSize"})
	public ResponseEntity<List<AuthorResponse>> getPriorityInfluencersPaginated(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		List<AuthorResponse> all = service.findPriorityInfluencers();
		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, all.size());
		if (fromIndex >= all.size()) return ResponseEntity.ok(Collections.emptyList());

		return ResponseEntity.ok(all.subList(fromIndex, toIndex));
	}

	@Operation(summary = "Get all authors ordered by followers with pagination")
	@ApiResponse(responseCode = "200", description = "Authors ordered by follower count",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping(value = "/orderByFollowers", params = {"page", "pageSize"})
	public ResponseEntity<List<AuthorResponse>> getAllOrderByFollowersPaginated(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		List<AuthorResponse> all = service.getAllOrderByFollowers();
		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, all.size());
		if (fromIndex >= all.size()) return ResponseEntity.ok(Collections.emptyList());

		return ResponseEntity.ok(all.subList(fromIndex, toIndex));
	}

	@Operation(summary = "Search authors by username",
			description = "Supports both exact and partial matches")
	@ApiResponse(responseCode = "200", description = "Authors matching the search criteria",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping("/search")
	public ResponseEntity<List<AuthorResponse>> searchByUsername(
			@Parameter(description = "Username to search (supports partial match)", required = true)
			@RequestParam String username,
			@Parameter(description = "Exact match only", example = "false")
			@RequestParam(defaultValue = "false") boolean exact) {
		List<AuthorResponse> authors = exact
				? service.findByUsernameExact(username)
				: service.searchByUsername(username);
		return ResponseEntity.ok(authors);
	}

	@Operation(summary = "Get authors filtered by follower count range with pagination")
	@ApiResponse(responseCode = "200", description = "Authors found within the follower count range",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
	@GetMapping(value = "/followers-range", params = {"page", "pageSize"})
	public ResponseEntity<List<AuthorResponse>> getByFollowerCountRange(
			@RequestParam Integer min,
			@RequestParam Integer max,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

		List<AuthorResponse> authors = service.findByFollowerCountRange(min, max, page, pageSize);
		return ResponseEntity.ok(authors);
	}

}
