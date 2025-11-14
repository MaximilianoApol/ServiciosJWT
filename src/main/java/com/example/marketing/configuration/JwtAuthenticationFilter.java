package com.example.marketing.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.marketing.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");

		// Si no hay header Authorization o no comienza con "Bearer ", continuar sin autenticar
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			// Extraer el token (quitar "Bearer ")
			final String jwt = authHeader.substring(7);
			final String userEmail = jwtService.extractUsername(jwt);

			// Si hay email y no hay autenticación previa en el contexto
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (userEmail != null && authentication == null) {
				// Cargar los detalles del usuario
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

				// Validar el token
				if (jwtService.isTokenValid(jwt, userDetails)) {
					// Crear token de autenticación
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// Establecer la autenticación en el contexto de seguridad
					SecurityContextHolder.getContext().setAuthentication(authToken);

					log.debug("Usuario autenticado: {}", userEmail);
				}
			}

			filterChain.doFilter(request, response);
		} catch (Exception exception) {
			log.error("Error en el filtro JWT: {}", exception.getMessage());
			handlerExceptionResolver.resolveException(request, response, null, exception);
		}
	}
}