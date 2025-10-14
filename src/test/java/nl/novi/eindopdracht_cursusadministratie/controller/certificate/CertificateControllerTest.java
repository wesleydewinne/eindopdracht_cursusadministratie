package nl.novi.eindopdracht_cursusadministratie.controller.certificate;

import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.service.certificate.CertificateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CertificateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificateService certificateService;

    @Test
    @WithMockUser(username = "admin@bhv.nl", roles = {"ADMIN"})
    void shouldReturnListOfCertificates_whenAdminCallsGetAll() throws Exception {
        // Arrange
        Certificate cert = new Certificate();
        cert.setId(1L);
        cert.setCertificateNumber("CERT-123");

        when(certificateService.getAllCertificates()).thenReturn(List.of(cert));

        // Act & Assert
        mockMvc.perform(get("/api/certificates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].certificateNumber").value("CERT-123"));
    }

    @Test
    @WithMockUser(username = "student@bhvtraining.nl", roles = {"CURSIST"})
    void shouldDenyAccess_whenStudentTriesToAccessAdminCertificates() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/admin/certificates"))
                .andExpect(status().isForbidden());
    }
}