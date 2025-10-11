package nl.novi.eindopdracht_cursusadministratie.service.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import nl.novi.eindopdracht_cursusadministratie.helper.PdfHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfCertificateService {

    public byte[] generateCertificatePdf(Certificate certificate) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4, 50, 50, 70, 50);
            PdfWriter.getInstance(doc, out);
            doc.open();

            PdfHelper.addTitle(doc, "CERTIFICAAT VAN DEELNAME");
            PdfHelper.addKeyValue(doc, "Naam cursist", certificate.getStudent().getName());
            PdfHelper.addKeyValue(doc, "Cursus", certificate.getCourse().getName());
            PdfHelper.addKeyValue(doc, "Trainer", certificate.getCourse().getTrainer().getName());
            PdfHelper.addKeyValue(doc, "Uitgegeven door", certificate.getIssuedBy());
            PdfHelper.addKeyValue(doc, "Datum uitgifte", certificate.getIssueDate().toString());
            PdfHelper.addKeyValue(doc, "Geldig tot", certificate.getExpiryDate().toString());
            PdfHelper.addKeyValue(doc, "Certificaatnummer", certificate.getCertificateNumber());
            PdfHelper.addFooter(doc, "© Safety First BV - Officiële uitgifte");

            doc.close();
            return out.toByteArray();

        } catch (DocumentException e) {
            throw new RuntimeException("Fout bij genereren PDF", e);
        } catch (Exception e) {
            throw new RuntimeException("Onbekende fout bij PDF-generatie", e);
        }
    }
}
