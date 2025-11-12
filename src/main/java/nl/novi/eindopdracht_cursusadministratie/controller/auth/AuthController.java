package nl.novi.eindopdracht_cursusadministratie.controller.auth;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.auth.AuthenticationRequest;
import nl.novi.eindopdracht_cursusadministratie.dto.auth.AuthenticationResponse;
import nl.novi.eindopdracht_cursusadministratie.dto.auth.CurrentUserDto;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.security.CustomUserDetailsService;
import nl.novi.eindopdracht_cursusadministratie.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller voor authenticatie en tokenbeheer.
 *
 * Functionaliteit:
 *  - /login → genereert JWT-token
 *  - /check → controleert of token geldig is
 *  - /me → geeft huidige ingelogde gebruiker terug
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    // ============================================================
    // LOGIN — JWT GENEREREN
    // ============================================================

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @NotNull AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Ongeldige inloggegevens", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Gebruiker niet gevonden na succesvolle authenticatie."));

        long expiresAt = System.currentTimeMillis() + jwtUtil.getExpirationTime();

        AuthenticationResponse response = new AuthenticationResponse(
                jwt,
                user.getId(),
                user.getEmail(),
                "ROLE_" + user.getRole().name(),
                user.getName(),
                expiresAt
        );

        return ResponseEntity.ok(response);
    }


    // ============================================================
    //  TOKENVALIDATIE — CONTROLE
    // ============================================================

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAuth(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());
        response.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return ResponseEntity.ok(response);
    }


    // ============================================================
    //  HUIDIGE GEBRUIKER — PROFIEL
    // ============================================================

    @GetMapping("/me")
    public ResponseEntity<CurrentUserDto> getCurrentUser(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Gebruiker niet gevonden."));

        CurrentUserDto dto = new CurrentUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(dto);
    }
}
