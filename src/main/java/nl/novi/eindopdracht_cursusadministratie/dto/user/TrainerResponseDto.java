package nl.novi.eindopdracht_cursusadministratie.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrainerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String expertise;
}
