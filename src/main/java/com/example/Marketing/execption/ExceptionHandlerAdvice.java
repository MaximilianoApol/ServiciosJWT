package com.example.Marketing.execption;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.persistence.EntityNotFoundException;

/**
 * Global exception handler para mapear excepciones a respuestas HTTP apropiadas
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

	/**
	 * Maneja EntityNotFoundException y retorna 404 NOT FOUND
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
			EntityNotFoundException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.error("Not Found")
				.message(ex.getMessage())
				.path(request.getDescription(false).replace("uri=", ""))
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Maneja IllegalArgumentException y retorna 400 BAD REQUEST
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
			IllegalArgumentException ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Bad Request")
				.message(ex.getMessage())
				.path(request.getDescription(false).replace("uri=", ""))
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Maneja errores de validación de Jakarta Bean Validation
	 * y retorna 400 BAD REQUEST con detalles de los campos inválidos
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
			MethodArgumentNotValidException ex, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Validation Failed")
				.message("La validación de uno o más campos ha fallado")
				.path(request.getDescription(false).replace("uri=", ""))
				.validationErrors(errors)
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Maneja excepciones genéricas no controladas
	 * y retorna 500 INTERNAL SERVER ERROR
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(
			Exception ex, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error("Internal Server Error")
				.message("Ha ocurrido un error inesperado: " + ex.getMessage())
				.path(request.getDescription(false).replace("uri=", ""))
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Clase interna para estructura de respuesta de error estándar
	 */
	@lombok.Data
	@lombok.Builder
	@lombok.NoArgsConstructor
	@lombok.AllArgsConstructor
	public static class ErrorResponse {
		private OffsetDateTime timestamp;
		private Integer status;
		private String error;
		private String message;
		private String path;
	}

	/**
	 * Clase interna para respuesta de errores de validación
	 */
	@lombok.Data
	@lombok.Builder
	@lombok.NoArgsConstructor
	@lombok.AllArgsConstructor
	public static class ValidationErrorResponse {
		private OffsetDateTime timestamp;
		private Integer status;
		private String error;
		private String message;
		private String path;
		private Map<String, String> validationErrors;
	}
}