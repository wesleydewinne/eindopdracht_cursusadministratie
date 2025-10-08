package nl.novi.eindopdracht_cursusadministratie.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Centrale helper voor het versturen van e-mails in de applicatie.
 * <p>
 * Bevat standaardmethodes voor veelgebruikte mailtypes zoals:
 * - Certificaat melding aan cursist
 * - Inschrijvingsbevestiging
 * - Herinneringen
 */
@Slf4j
@Component
public class MailHelper {

    private final JavaMailSender mailSender;

    public MailHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Verzendt een eenvoudige e-mail.
     *
     * @param to      Ontvanger
     * @param subject Onderwerpregel
     * @param message Berichtinhoud
     */
    public void sendMail(String to, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailSender.send(mailMessage);

            log.info(" Mail succesvol verzonden naar {}", to);
        } catch (Exception e) {
            log.error("Fout bij het verzenden van mail naar {}: {}", to, e.getMessage());
        }
    }

    /**
     * Stuurt een standaardbericht wanneer een certificaat is gegenereerd.
     *
     * @param toEmail    e-mailadres van de cursist
     * @param studentName naam van de cursist
     * @param courseName  naam van de cursus
     * @param certNumber  certificaatnummer
     * @param expiryDate  vervaldatum
     * @param issuedBy    naam van de trainer/organisatie
     */
    public void sendCertificateNotification(
            String toEmail,
            String studentName,
            String courseName,
            String certNumber,
            String expiryDate,
            String issuedBy
    ) {
        String subject = "Je certificaat voor " + courseName + " is beschikbaar";
        String body = String.format("""
                Beste %s,

                Gefeliciteerd! Je hebt de training "%s" succesvol afgerond.
                Je certificaat met nummer %s is nu beschikbaar in het systeem.

                Je kunt het downloaden door in te loggen op je account.

                Geldig tot: %s
                Uitgegeven door: %s

                Met vriendelijke groet,
                BHV Training
                """,
                studentName, courseName, certNumber, expiryDate, issuedBy
        );

        sendMail(toEmail, subject, body);
    }
}
