package nl.novi.eindopdracht_cursusadministratie.dto.auth;

/**
 * DTO voor het tonen van de huidige ingelogde gebruiker (/api/auth/me).
 */
public record CurrentUserDto(
        Long id,
        String name,
        String email,
        String role
) {}
