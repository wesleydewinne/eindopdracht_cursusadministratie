package nl.novi.eindopdracht_cursusadministratie.service.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import nl.novi.eindopdracht_cursusadministratie.helper.PdfHelper;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfEvacuationReportService {

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

            doc.add(new Paragraph(" ")); // lege regel

            PdfHelper.addTitle(doc, "Waarnemingen & Advies");
            PdfHelper.addKeyValue(doc, "Waarnemingen", report.getObservations());
            PdfHelper.addKeyValue(doc, "Verbeterpunten", report.getImprovements());
            PdfHelper.addKeyValue(doc, "Evaluatieadvies", report.getEvaluationAdvice());

            PdfHelper.addFooter(doc, "© BHV Training BV - Ontruimingsverslag");

            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Fout bij genereren van ontruimingsverslag PDF", e);
        }
    }
}
