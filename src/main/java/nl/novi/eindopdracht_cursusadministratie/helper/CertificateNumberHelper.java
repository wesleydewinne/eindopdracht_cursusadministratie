package nl.novi.eindopdracht_cursusadministratie.helper;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class CertificateNumberHelper {

    private static final AtomicInteger counter = new AtomicInteger(1);

    public static String generateCertificateNumber() {
        String year = String.valueOf(LocalDate.now().getYear());
        String formattedCounter = String.format("%04d", counter.getAndIncrement());
        return year + "-" + formattedCounter;
    }
}
