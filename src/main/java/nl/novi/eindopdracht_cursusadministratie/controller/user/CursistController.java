package nl.novi.eindopdracht_cursusadministratie.controller.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.response.RegistrationDetailsResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.CursistResponseDto;
import nl.novi.eindopdracht_cursusadministratie.helper.CursistSecurityHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.service.user.CursistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursisten")
@RequiredArgsConstructor
@CrossOrigin
public class CursistController {

    private final CursistService cursistService;


    // ============================================================
    //  EIGEN PROFIEL
    // ============================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CURSIST','ADMIN')")
    public ResponseEntity<CursistResponseDto> getCursistById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Cursist cursist = cursistService.getCursistById(id);

        if (!CursistSecurityHelper.hasAccess(userDetails, cursist)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        CursistResponseDto dto = new CursistResponseDto(
                cursist.getId(),
                cursist.getName(),
                cursist.getEmail(),
                cursist.isActive()
        );
        return ResponseEntity.ok(dto);
    }


    // ============================================================
    //  CERTIFICATEN
    // ============================================================

    @GetMapping("/{id}/certificaten")
    @PreAuthorize("hasAnyRole('CURSIST','ADMIN')")
    public ResponseEntity<List<Certificate>> getCertificatesByCursist(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Cursist cursist = cursistService.getCursistById(id);

        if (!CursistSecurityHelper.hasAccess(userDetails, cursist)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(cursistService.getCertificatesByCursist(id));
    }


    // ============================================================
    //  INSCHRIJVINGEN
    // ============================================================

    @GetMapping("/{id}/inschrijvingen")
    @PreAuthorize("hasAnyRole('CURSIST','ADMIN')")
    public ResponseEntity<List<RegistrationDetailsResponseDto>> getRegistrationsByCursist(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Cursist cursist = cursistService.getCursistById(id);

        if (!CursistSecurityHelper.hasAccess(userDetails, cursist)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Registration> registrations = cursistService.getRegistrationsByCursist(id);

        List<RegistrationDetailsResponseDto> dtos = registrations.stream()
                .map(reg -> new RegistrationDetailsResponseDto(
                        reg.getId(),
                        reg.getCourse().getId(),
                        reg.getCourse().getName(),
                        reg.getStudent().getId(),
                        reg.getStudent().getName(),
                        reg.getRegistrationDate(),
                        reg.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
