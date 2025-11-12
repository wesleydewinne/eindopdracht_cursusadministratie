package nl.novi.eindopdracht_cursusadministratie.service.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import nl.novi.eindopdracht_cursusadministratie.helper.PdfHelper;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

/**
 * Service-klasse voor het genereren van ontruimingsverslag-PDF's.
 * Retourneert een byte[] die veilig kan worden opgeslagen in de database (kolomtype BYTEA).
 */
@Service
public class PdfEvacuationReportService {

    private static final Logger logger = LoggerFactory.getLogger(PdfEvacuationReportService.class);

    /**
     * Genereert een professioneel PDF-verslag van een ontruimingsoefening.
     *
     * @param report het ontruimingsverslag
     * @return byte-array met de PDF-inhoud
     */
    public byte[] generateReportPdf(EvacuationReport report) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document doc = new Document(PageSize.A4, 50, 50, 70, 50);
            PdfWriter.getInstance(doc, out);
            doc.open();

            PdfHelper.addTitle(doc, "ONTRUIMINGSVERSLAG");

            PdfHelper.addKeyValue(doc, "Cursus", report.getCourse().getName());
            PdfHelper.addKeyValue(doc, "Trainer", report.getCreatedBy().getName());
            PdfHelper.addKeyValue(doc, "Fase", report.getPhase().getDescription());
            PdfHelper.addKeyValue(doc, "Evacuatietijd", report.getEvacuationTimeMinutes() + " minuten");
            PdfHelper.addKeyValue(doc, "Gebouwgrootte", report.getBuildingSize());
            PdfHelper.addKeyValue(doc, "Status", report.getStatus().name());
            PdfHelper.addKeyValue(doc, "Goedgekeurd door",
                    report.getApprovedBy() != null ? report.getApprovedBy().getName() : "Nog niet goedgekeurd");


            doc.add(Chunk.NEWLINE);

            // Observaties en evaluatie
            PdfHelper.addTitle(doc, "Waarnemingen & Advies");
            PdfHelper.addKeyValue(doc, "Waarnemingen", report.getObservations());
            PdfHelper.addKeyValue(doc, "Verbeterpunten", report.getImprovements());
            PdfHelper.addKeyValue(doc, "Evaluatieadvies", report.getEvaluationAdvice());

            PdfHelper.addFooter(doc, "© BHV Training BV - Ontruimingsverslag");

            doc.close();

            byte[] pdfBytes = out.toByteArray();
            logger.info("PDF-verslag succesvol gegenereerd ({} bytes)", pdfBytes.length);
            return pdfBytes;

        } catch (Exception e) {
            logger.error("Fout bij genereren van ontruimingsverslag PDF", e);
            throw new RuntimeException("Fout bij genereren van ontruimingsverslag PDF", e);
        }
    }
}
