package nl.novi.eindopdracht_cursusadministratie.dto.user;

import lombok.Data;

@Data
public class AdminCursistCreateDto {
    private String name;
    private String email;
    private boolean active;
    private String password;
}
