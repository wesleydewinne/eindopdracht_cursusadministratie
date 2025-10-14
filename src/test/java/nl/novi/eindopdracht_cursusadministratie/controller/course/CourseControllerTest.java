package nl.novi.eindopdracht_cursusadministratie.controller.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    @WithMockUser(username = "admin@bhv.nl", roles = {"ADMIN"})
    void shouldReturnListOfCourses_whenAdminCallsGetAll() throws Exception {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setName("BHV Basis");
        course.setDescription("Basistraining BHV");

        when(courseService.getAllCourses()).thenReturn(List.of(course));

        // Act & Assert
        mockMvc.perform(get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("BHV Basis"))
                .andExpect(jsonPath("$[0].description").value("Basistraining BHV"));
    }

    @Test
    @WithMockUser(username = "trainer@bhv.nl", roles = {"TRAINER"})
    void shouldDenyAccess_whenTrainerCallsAdminEndpoint() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }
}