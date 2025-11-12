package nl.novi.eindopdracht_cursusadministratie.security;

import nl.novi.eindopdracht_cursusadministratie.security.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // ============================================================
    // AUTHENTICATIE EN ENCODING
    // ============================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(authProvider);
    }

    // ============================================================
    // SECURITYCONFIG
    // ============================================================

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth

                        //  OPEN ENDPOINTS
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()

                        //  USERS (JWT bepaalt rol)
                        .requestMatchers("/api/users/**").authenticated()

                        //  COURSES
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyRole("ADMIN", "TRAINER", "CURSIST")
                        .requestMatchers(HttpMethod.POST, "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN")

                        //  REGISTRATIONS
                        .requestMatchers(HttpMethod.GET, "/api/registrations/**").hasAnyRole("ADMIN", "TRAINER")
                        .requestMatchers(HttpMethod.POST, "/api/registrations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/registrations/**").hasRole("TRAINER")
                        .requestMatchers(HttpMethod.DELETE, "/api/registrations/**").hasRole("ADMIN")

                        // 🪪 CERTIFICATES
                        .requestMatchers(HttpMethod.GET, "/api/certificates/**").hasAnyRole("ADMIN", "TRAINER", "CURSIST")
                        .requestMatchers(HttpMethod.POST, "/api/certificates/**").hasRole("TRAINER")
                        .requestMatchers(HttpMethod.DELETE, "/api/certificates/**").hasRole("ADMIN")

                        //  REPORTS
                        .requestMatchers(HttpMethod.GET, "/api/reports/**").hasAnyRole("ADMIN", "TRAINER", "CURSIST")
                        .requestMatchers(HttpMethod.POST, "/api/reports/**").hasRole("TRAINER")
                        .requestMatchers(HttpMethod.PUT, "/api/reports/**").hasAnyRole("ADMIN", "TRAINER")
                        .requestMatchers(HttpMethod.DELETE, "/api/reports/**").hasRole("ADMIN")

                        //  LOCATIONS
                        .requestMatchers(HttpMethod.GET, "/api/locations/**").hasAnyRole("ADMIN", "TRAINER", "CURSIST")
                        .requestMatchers(HttpMethod.POST, "/api/locations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/locations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/locations/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
