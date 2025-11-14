package com.example.marketing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Deshabilitar CSRF (no es necesario para APIs stateless con JWT)
				.csrf(csrf -> csrf.disable())

				// Habilitar CORS
				.cors(withDefaults())

				// Configurar gestión de sesiones como STATELESS
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Configurar autorización de endpoints
				.authorizeHttpRequests(auth -> auth
						// Endpoints públicos (sin autenticación)
						.requestMatchers(
								"/api/v1/auth/**",           // Autenticación y registro
								"/doc/**",                   // Swagger UI
								"/swagger-ui/**",            // Swagger UI recursos
								"/v3/api-docs/**",           // OpenAPI docs
								"/swagger-ui.html"           // Swagger HTML
						).permitAll()

						// Endpoints protegidos por roles
						.requestMatchers("/api/v1/authors/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers("/api/v1/users/**").hasRole("ADMIN")

						// Cualquier otra petición requiere autenticación
						.anyRequest().authenticated()
				)

				// Configurar el provider de autenticación
				.authenticationProvider(authenticationProvider)

				// Agregar el filtro JWT antes del filtro de autenticación estándar
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Configuración de CORS
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// Orígenes permitidos (configurable por environment)
		configuration.setAllowedOrigins(List.of(
				"http://localhost:3000",      // React dev
				"http://localhost:4200",      // Angular dev
				"http://localhost:8080"       // Mismo origen
		));

		// Métodos HTTP permitidos
		configuration.setAllowedMethods(List.of(
				"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
		));

		// Headers permitidos
		configuration.setAllowedHeaders(List.of(
				"Authorization",
				"Content-Type",
				"Accept"
		));

		// Permitir credenciales
		configuration.setAllowCredentials(true);

		// Tiempo de cache para preflight requests
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}