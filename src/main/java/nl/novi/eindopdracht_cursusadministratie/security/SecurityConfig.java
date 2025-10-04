package nl.novi.eindopdracht_cursusadministratie.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Zet CSRF uit (handig voor Postman-tests)
                .csrf(csrf -> csrf.disable())
                // Alle endpoints zijn vrij toegankelijk
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // Schakel login en basic auth volledig uit
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
