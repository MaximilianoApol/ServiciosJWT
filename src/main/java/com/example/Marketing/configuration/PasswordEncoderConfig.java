package com.example.Marketing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuración del encoder de contraseñas para Spring Security
 */
@Configuration
public class PasswordEncoderConfig {

	/**
	 * Bean para codificación de contraseñas usando BCrypt
	 * BCrypt es un algoritmo de hashing adaptativo que incluye salt automático
	 *
	 * @return BCryptPasswordEncoder configurado
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}