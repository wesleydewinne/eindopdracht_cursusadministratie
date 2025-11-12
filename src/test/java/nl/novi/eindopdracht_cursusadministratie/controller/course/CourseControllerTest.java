package nl.novi.eindopdracht_cursusadministratie.controller.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integratietest voor de CourseController.
 * Test de belangrijkste REST-acties met MockMvc.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    // ============================================================
    // GET ALL COURSES
    // ============================================================
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Admin kan alle cursussen ophalen")
    void getAllCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("BHV Basiscursus");
        course1.setType(TrainingType.BHV);
        course1.setStartDate(LocalDate.now());
        course1.setEndDate(LocalDate.now().plusDays(1));

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("EHBO Herhaling");
        course2.setType(TrainingType.EHBO);
        course2.setStartDate(LocalDate.now());
        course2.setEndDate(LocalDate.now().plusDays(1));

        Mockito.when(courseService.getAllCourses()).thenReturn(List.of(course1, course2));

        mockMvc.perform(get("/api/courses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ============================================================
    // CREATE COURSE
    // ============================================================
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Admin kan een nieuwe cursus aanmaken")
    void createCourse() throws Exception {
        Course dummyCourse = new Course();
        dummyCourse.setId(1L);
        dummyCourse.setName("BHV Basiscursus");
        dummyCourse.setType(TrainingType.BHV);

        Mockito.when(courseService.createCourse(any(Course.class))).thenReturn(dummyCourse);

        String jsonBody = """
        {
          "name": "BHV Basiscursus",
          "description": "Basistraining Bedrijfshulpverlening",
          "startDate": "2025-12-01",
          "endDate": "2025-12-02",
          "maxParticipants": 10,
          "adminOverrideAllowed": false,
          "type": "BHV"
        }
        """;

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("BHV Basiscursus"));
    }

    // ============================================================
    // UPDATE COURSE
    // ============================================================
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Admin kan een bestaande cursus bijwerken")
    void updateCourse() throws Exception {
        Course updated = new Course();
        updated.setId(1L);
        updated.setName("BHV Herhaling");

        Mockito.when(courseService.updateCourse(anyLong(), any(Course.class))).thenReturn(updated);

        String updateJson = """
                {
                  "name": "BHV Herhaling",
                  "description": "Herhalingscursus BHV",
                  "type": "BHV",
                  "adminOverrideAllowed": true
                }
                """;

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BHV Herhaling"));
    }

    // ============================================================
    // DELETE COURSE
    // ============================================================
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Admin kan een cursus verwijderen")
    void deleteCourse() throws Exception {
        Mockito.doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Cursus met ID 1 is succesvol verwijderd."));
    }

    // ============================================================
    // GET COURSE BY ID
    // ============================================================
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Trainer of admin kan een cursus opvragen via ID")
    void getCourseById() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("BHV Basiscursus");
        course.setDescription("Basisopleiding bedrijfshulpverlening");
        course.setType(TrainingType.BHV);

        Mockito.when(courseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BHV Basiscursus"))
                .andExpect(jsonPath("$.description").value("Basisopleiding bedrijfshulpverlening"))
                .andExpect(jsonPath("$.type").value("BHV"));
    }

}
