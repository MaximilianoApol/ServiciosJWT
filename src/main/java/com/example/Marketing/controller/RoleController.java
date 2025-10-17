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

import com.example.Marketing.dto.RoleRequest;
import com.example.Marketing.dto.RoleResponse;
import com.example.Marketing.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*")
@Tag(name = "Roles", description = "Manage user roles and permissions")
public class RoleController {

	@Autowired
	private RoleService service;

	@Operation(summary = "Get all roles")
	@ApiResponse(responseCode = "200", description = "Found roles", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))})
	@GetMapping
	public List<RoleResponse> getAll() {
		return service.findAll();
	}

	@Operation(summary = "Get role by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Role found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Role not found", content = @Content) })
	@GetMapping("{id}")
	public ResponseEntity<RoleResponse> getById(@PathVariable Integer id) {
		RoleResponse response = service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Get role by name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Role found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Role not found", content = @Content) })
	@GetMapping("/name/{roleName}")
	public ResponseEntity<RoleResponse> getByName(@PathVariable String roleName) {
		RoleResponse response = service.findByName(roleName);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Create a new role")
	@ApiResponse(responseCode = "201", description = "Role created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class)) })
	@PostMapping
	public ResponseEntity<RoleResponse> create(@Valid @RequestBody RoleRequest request) {
		RoleResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing role")
	@PutMapping("{id}")
	public ResponseEntity<RoleResponse> update(@PathVariable Integer id, @Valid @RequestBody RoleRequest request) {
		RoleResponse response = service.update(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Delete a role")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Check if role name exists")
	@GetMapping("/exists")
	public ResponseEntity<Boolean> existsByName(@RequestParam String roleName) {
		return new ResponseEntity<>(service.existsByName(roleName), HttpStatus.OK);
	}
}