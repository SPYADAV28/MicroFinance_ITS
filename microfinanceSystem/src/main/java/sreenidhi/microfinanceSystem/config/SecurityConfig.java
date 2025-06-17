package sreenidhi.microfinanceSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // <--- ADD THIS IMPORT
import org.springframework.web.cors.CorsConfigurationSource; // <--- ADD THIS IMPORT
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // <--- ADD THIS IMPORT

import sreenidhi.microfinanceSystem.security.JwtAuthenticationFilter;
import sreenidhi.microfinanceSystem.security.CustomUserDetailsService;
import sreenidhi.microfinanceSystem.security.JwtAuthEntryPoint;
import java.util.Arrays; // <--- ADD THIS IMPORT

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint authEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // <--- Ensure this is injected or a Bean

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint,
            JwtAuthenticationFilter jwtAuthenticationFilter) { // <--- Update constructor
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter; // <--- Initialize
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) // <--- Removed JwtAuthenticationFilter from params
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // <--- ADD THIS LINE FOR CORS
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // .anyRequest().permitAll()
                        .requestMatchers("/api/auth/**").permitAll() // Allow public access to auth endpoints
                        .anyRequest().authenticated() // All other requests require authentication
                );

        // Add the JWT authentication filter before the Spring Security's default
        // UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // <--- ADD THIS NEW BEAN FOR CORS CONFIGURATION SOURCE
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Your frontend origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed methods
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (important for JWT)
        configuration.setMaxAge(3600L); // Max age for preflight response cache

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Apply to /api/** paths
        return source;
    }
}