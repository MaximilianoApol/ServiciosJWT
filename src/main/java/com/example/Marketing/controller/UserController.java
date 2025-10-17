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

import com.example.Marketing.dto.UserRequest;
import com.example.Marketing.dto.UserResponse;
import com.example.Marketing.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
@Tag(name = "Users", description = "Manage users and authentication")
public class UserController {

	@Autowired
	private UserService service;

	@Operation(summary = "Get all users")
	@ApiResponse(responseCode = "200", description = "Found users", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))})
	@GetMapping
	public List<UserResponse> getAll(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {
		if (page != null && size != null) {
			return service.findAll(page, size);
		}
		return service.findAll();
	}

	@Operation(summary = "Get user by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
	@GetMapping("{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
		UserResponse response = service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Get user by email")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponse> getByEmail(@PathVariable String email) {
		UserResponse response = service.findByEmail(email);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Create a new user")
	@ApiResponse(responseCode = "201", description = "User created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) })
	@PostMapping
	public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
		UserResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing user")
	@PutMapping("{id}")
	public ResponseEntity<UserResponse> update(@PathVariable Integer id, @Valid @RequestBody UserRequest request) {
		UserResponse response = service.update(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Delete a user")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Get active users only")
	@GetMapping("/active")
	public ResponseEntity<List<UserResponse>> getActiveUsers() {
		return new ResponseEntity<>(service.findActiveUsers(), HttpStatus.OK);
	}

	@Operation(summary = "Search users by name")
	@GetMapping("/search")
	public ResponseEntity<List<UserResponse>> searchByName(@RequestParam String name) {
		return new ResponseEntity<>(service.searchByName(name), HttpStatus.OK);
	}

	@Operation(summary = "Check if email exists")
	@GetMapping("/exists")
	public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
		return new ResponseEntity<>(service.existsByEmail(email), HttpStatus.OK);
	}
}