package tumminia.wrpsrlibrary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	JWTAuthFilter jwtFilter;
	@Autowired
	CorsFilter corsFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(c -> c.disable());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(corsFilter, JWTAuthFilter.class);

		// ENDPOINT
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/upload-libro").hasRole("ADMIN"));
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/cancella-libro/**").hasRole("ADMIN"));
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/modifica-libro/**").hasRole("ADMIN"));
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/editori/upload-editore").hasRole("ADMIN"));
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/editori/elenco-editori").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/users/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/libreria-personale/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/libri/**").authenticated());

		return http.build();
	}

	// BEAN PER CRIPTARE LA PASSWORD
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
}