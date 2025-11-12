package nl.novi.eindopdracht_cursusadministratie.controller.certificate;

import nl.novi.eindopdracht_cursusadministratie.dto.certificate.CertificateResponseDto;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.certificate.CertificateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integratietest voor de CertificateController.
 * Test alleen de REST-laag, niet de echte database.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificateService certificateService;

    @MockBean
    private UserRepository userRepository;


    // ============================================================
    // GENERATE CERTIFICATE (POST)
    // ============================================================
    @Test
    @WithMockUser(username = "trainer@bedrijf.nl", roles = {"TRAINER"})
    @DisplayName("Trainer kan een certificaat genereren")
    void generateCertificate() throws Exception {
        var trainer = new User();
        trainer.setId(3L);
        trainer.setEmail("trainer@bedrijf.nl");
        trainer.setRole(Role.TRAINER);

        Mockito.when(userRepository.findByEmail("trainer@bedrijf.nl"))
                .thenReturn(Optional.of(trainer));

        Mockito.when(certificateService.generateCertificate(anyLong(), anyLong(), anyLong()))
                .thenReturn(new CertificateResponseDto(
                        1L, "2025-0001", "BHV", "Jan Jansen",
                        LocalDate.now(), LocalDate.now().plusYears(1), "Trainer X"
                ));

        mockMvc.perform(post("/api/certificates/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"courseId\":1,\"studentId\":2,\"trainerId\":3}"))
                .andExpect(status().isOk());
    }


    // ============================================================
    // GET CERTIFICATE BY ID
    // ============================================================
    @Test
    @WithMockUser(username = "jan@bedrijf.nl", roles = {"CURSIST"})
    @DisplayName("Cursist kan eigen certificaten ophalen (mocked)")
    void getMyCertificates_Mocked() throws Exception {
        var user = new User();
        user.setEmail("jan@bedrijf.nl");

        var dto = new CertificateResponseDto(
                1L, "2025-0001", "BHV", "Jan Jansen",
                LocalDate.now(), LocalDate.now().plusYears(1), "Trainer X"
        );

        Mockito.when(userRepository.findByEmail("jan@bedrijf.nl"))
                .thenReturn(Optional.of(user));

        Mockito.when(certificateService.getCertificatesByCursistEmail("jan@bedrijf.nl"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/certificates/mine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].certificateNumber").value("2025-0001"));
    }

    // ============================================================
    // DOWNLOAD PDF
    // ============================================================
    @Test
    @WithMockUser(username = "trainer@bedrijf.nl", roles = {"TRAINER"})
    @DisplayName("PDF van certificaat kan worden gedownload")
    void downloadCertificatePdf() throws Exception {

        var trainer = new Trainer();
        trainer.setId(3L);
        trainer.setEmail("trainer@bedrijf.nl");
        trainer.setRole(Role.TRAINER);

        var course = new Course();
        course.setId(10L);
        course.setTrainer(trainer);

        var certificate = new Certificate();
        certificate.setId(1L);
        certificate.setCourse(course);

        byte[] pdfBytes = "dummy-pdf-content".getBytes();

        Mockito.when(userRepository.findByEmail("trainer@bedrijf.nl"))
                .thenReturn(Optional.of(trainer));

        Mockito.when(certificateService.getCertificateEntity(1L))
                .thenReturn(certificate);

        Mockito.when(certificateService.getCertificatePdf(1L))
                .thenReturn(pdfBytes);

        mockMvc.perform(get("/api/certificates/1/download"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/pdf"))
                .andExpect(header().exists("Content-Disposition"));
    }

    // ============================================================
    // GET MY CERTIFICATES
    // ============================================================
    @Test
    @WithMockUser(username = "jan@bedrijf.nl", roles = {"CURSIST"})
    @DisplayName("Cursist kan eigen certificaten ophalen")
    void getMyCertificates() throws Exception {
        var dto = new nl.novi.eindopdracht_cursusadministratie.dto.certificate.CertificateResponseDto(
                1L, "2025-0001", "BHV", "Jan Jansen",
                LocalDate.now(), LocalDate.now().plusYears(1), "Trainer X"
        );

        var cursist = new Cursist();
        cursist.setId(1L);
        cursist.setEmail("jan@bedrijf.nl");
        cursist.setName("Jan Jansen");
        cursist.setRole(Role.CURSIST);

        Mockito.when(userRepository.findByEmail("jan@bedrijf.nl"))
                .thenReturn(Optional.of(cursist));

        Mockito.when(certificateService.getCertificatesByCursistEmail("jan@bedrijf.nl"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/certificates/mine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].certificateNumber").value("2025-0001"));
    }

    // ============================================================
    // UNAUTHORIZED REQUESTS (403)
    // ============================================================
    @Test
    @DisplayName("Aanroep zonder geldige token moet 403 geven")
    void generateCertificate_Unauthorized() throws Exception {
        mockMvc.perform(post("/api/certificates/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"courseId\":1,\"studentId\":2,\"trainerId\":3}"))
                .andExpect(status().isForbidden());
    }
}
