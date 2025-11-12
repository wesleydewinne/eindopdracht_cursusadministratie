package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.dto.user.*;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;

public class UserMapperHelper {

    private UserMapperHelper() {}

    public static AdminUserResponseDto toAdminDto(User user) {
        return new AdminUserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    public static TrainerResponseDto toTrainerDto(Trainer trainer) {
        return new TrainerResponseDto(
                trainer.getId(),
                trainer.getName(),
                trainer.getEmail(),
                trainer.getRole().name(),
                trainer.getExpertise()
        );
    }

    public static CursistResponseDto toCursistDto(Cursist cursist) {
        return new CursistResponseDto(
                cursist.getId(),
                cursist.getName(),
                cursist.getEmail(),
                cursist.isActive()
        );
    }
}
