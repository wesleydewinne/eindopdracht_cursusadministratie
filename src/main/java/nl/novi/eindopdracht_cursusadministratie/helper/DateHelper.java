package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Utility class for handling date calculations related to certificates and courses.
 *
 * Includes methods for calculating expiry dates, checking certificate validity,
 * and determining days remaining until expiry.
 */
public class DateHelper {

    private DateHelper() {
    }

    // ============================================================
    //  CERTIFICATE EXPIRY CALCULATION
    // ============================================================

    /**
     * Calculates the expiry date of a certificate based on the issue date
     * and the validity period defined by the TrainingType.
     *
     * If the training type has no validity period (e.g., evacuation drill),
     * the issue date itself is returned.
     *
     * @param issueDate  the date the certificate was issued
     * @param type       the type of training (BHV, EHBO, ONTRUIMINGSOEFENING)
     * @return expiry date or issue date if no validity period applies
     */
    public static LocalDate calculateExpiryDateOrIssue(LocalDate issueDate, TrainingType type) {
        Objects.requireNonNull(issueDate, "issueDate must not be null");
        Objects.requireNonNull(type, "type must not be null");

        int years = type.getValidityYears();
        return years > 0 ? issueDate.plusYears(years) : issueDate;
    }

    // ============================================================
    //  CERTIFICATE VALIDATION
    // ============================================================

    /**
     * Checks whether a certificate has expired based on its expiry date.
     *
     * @param expiryDate the expiry date of the certificate
     * @return true if the certificate has expired, false otherwise
     */
    public static boolean isCertificateExpired(LocalDate expiryDate) {
        return expiryDate != null && expiryDate.isBefore(LocalDate.now());
    }

    /**
     * Checks whether a certificate is still valid today.
     *
     * @param expiryDate the expiry date of the certificate
     * @return true if the certificate is still valid, false otherwise
     */
    public static boolean isCertificateValid(LocalDate expiryDate) {
        return expiryDate != null && !expiryDate.isBefore(LocalDate.now());
    }

    // ============================================================
    //  DATE DIFFERENCE CALCULATIONS
    // ============================================================

    /**
     * Calculates how many days remain until a certificate expires.
     *
     * @param expiryDate the expiry date of the certificate
     * @return number of days until expiry (negative if already expired)
     */
    public static long daysUntilExpiry(LocalDate expiryDate) {
        if (expiryDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }

    /**
     * Calculates how many days have passed since the issue date.
     *
     * @param issueDate the date the certificate was issued
     * @return number of days since issue (0 if null)
     */
    public static long daysSinceIssue(LocalDate issueDate) {
        if (issueDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(issueDate, LocalDate.now());
    }
}
