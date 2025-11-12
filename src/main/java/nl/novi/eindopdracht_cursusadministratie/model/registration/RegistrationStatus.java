package nl.novi.eindopdracht_cursusadministratie.model.registration;

public enum RegistrationStatus {
    REGISTERED, // registered
    PENDING,    // ingeschreven, nog niet beoordeeld
    APPROVED,   // aanwezig én geslaagd
    CANCELLED,  // aanwezig maar niet geslaagd
    ABSENT,     // niet aanwezig
    COMPLETED   // cursus afgerond (door admin of trainer)
}
