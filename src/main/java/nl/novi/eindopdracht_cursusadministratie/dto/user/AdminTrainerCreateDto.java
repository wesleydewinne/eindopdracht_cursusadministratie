package nl.novi.eindopdracht_cursusadministratie.dto.user;

import lombok.Data;

@Data
public class AdminTrainerCreateDto {
    private String name;
    private String email;
    private String expertise;
    private String password;
}
