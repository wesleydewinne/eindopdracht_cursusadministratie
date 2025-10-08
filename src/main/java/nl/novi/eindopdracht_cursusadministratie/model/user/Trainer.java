package nl.novi.eindopdracht_cursusadministratie.model.user;

import jakarta.persistence.*;
import lombok.*;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("TRAINER")
public class Trainer extends User {

    private String expertise;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();
}

