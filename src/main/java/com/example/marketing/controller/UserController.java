package com.example.marketing.controller;

import java.util.List;

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

import com.example.marketing.dto.UserRequest;
import com.example.marketing.dto.UserResponse;
import com.example.marketing.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
@Tag(name = "Users", description = "Gestión de usuarios del sistema")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl service;

	@Operation(summary = "Obtener todos los usuarios")
	@ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = {
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

	@Operation(summary = "Obtener usuario por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content) })
	@GetMapping("{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
		UserResponse response = service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Obtener usuario por email")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content) })
	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponse> getByEmail(@PathVariable String email) {
		UserResponse response = service.findByEmail(email);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Crear nuevo usuario")
	@ApiResponse(responseCode = "201", description = "Usuario creado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) })
	@PostMapping
	public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
		UserResponse response = service.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Actualizar usuario existente")
	@PutMapping("{id}")
	public ResponseEntity<UserResponse> update(@PathVariable Integer id, @Valid @RequestBody UserRequest request) {
		UserResponse response = service.update(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Eliminar usuario")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Obtener solo usuarios activos")
	@GetMapping("/active")
	public ResponseEntity<List<UserResponse>> getActiveUsers() {
		return new ResponseEntity<>(service.findActiveUsers(), HttpStatus.OK);
	}

	@Operation(summary = "Buscar usuarios por nombre")
	@GetMapping("/search")
	public ResponseEntity<List<UserResponse>> searchByName(@RequestParam String name) {
		return new ResponseEntity<>(service.searchByName(name), HttpStatus.OK);
	}

	@Operation(summary = "Verificar si existe un email")
	@GetMapping("/exists")
	public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
		return new ResponseEntity<>(service.existsByEmail(email), HttpStatus.OK);
	}
}