package nl.novi.eindopdracht_cursusadministratie.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * De MailHelper is verantwoordelijk voor het versturen van e-mails vanuit de applicatie.
 * Deze klasse wordt gebruikt door services om gebruikers automatisch te informeren,
 * bijvoorbeeld na het genereren van een certificaat.
 */
@Component
public class MailHelper {

    private final JavaMailSender mailSender;

    // Afzenderadres, in te stellen via application.properties
    @Value("${spring.mail.username:no-reply@cursusadministratie.nl}")
    private String fromAddress;

    public MailHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Stuurt een eenvoudige e-mailmelding.
     *
     * @param to      e-mailadres van de ontvanger
     * @param subject onderwerp van de e-mail
     * @param message inhoud van de e-mail
     */
    public void sendMail(String to, String subject, String message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(fromAddress);
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(message);
            mailSender.send(mail);
        } catch (Exception e) {
            // In de placeholder-versie vangen we fouten af zodat het project blijft draaien
            System.err.println("⚠️ Mail kon niet worden verzonden: " + e.getMessage());
        }
    }
}
