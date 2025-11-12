package nl.novi.eindopdracht_cursusadministratie.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String role;
}
