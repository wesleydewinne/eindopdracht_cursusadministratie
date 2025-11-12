package nl.novi.eindopdracht_cursusadministratie.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO voor het bijwerken van trainergegevens.
 * Wordt gebruikt door AdminController en TrainerController.
 */
@Getter
@Setter
public class TrainerUpdateRequestDto {
    private String name;
    private String email;
    private String expertise;
    private String password;
}
