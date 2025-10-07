package nl.novi.eindopdracht_cursusadministratie.helper;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * De {@code CertificateNumberHelper} genereert unieke certificaatnummers.
 * <p>
 * Elk certificaatnummer bestaat uit het huidige jaar, gevolgd door een
 * viercijferig oplopend volgnummer (bijv. 2025-0001).
 */
public class CertificateNumberHelper {

    private static final AtomicInteger counter = new AtomicInteger(1);

    /**
     * Genereert een uniek certificaatnummer.
     * <p>
     * Het nummer heeft het formaat {@code JAAR-XXXX}, waarbij {@code XXXX}
     * automatisch wordt opgehoogd per gegenereerd certificaat.
     *
     * @return een nieuw certificaatnummer, bijvoorbeeld {@code 2025-0001}
     */
    public static String generateCertificateNumber() {
        String year = String.valueOf(LocalDate.now().getYear());
        String formattedCounter = String.format("%04d", counter.getAndIncrement());
        return year + "-" + formattedCounter;
    }
}
