package nl.novi.eindopdracht_cursusadministratie.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import java.util.ArrayList;
import java.util.List;

/**
 * Subklasse van User die een trainer representeert.
 * Bevat extra velden zoals expertise en de cursussen die de trainer geeft.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("TRAINER")
public class Trainer extends User {

    private String expertise;

    @OneToMany(mappedBy = "trainer")
    @JsonManagedReference
    private List<Course> courses = new ArrayList<>();
}
