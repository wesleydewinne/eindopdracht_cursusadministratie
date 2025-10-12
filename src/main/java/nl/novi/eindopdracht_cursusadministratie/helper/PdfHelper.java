package nl.novi.eindopdracht_cursusadministratie.helper;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

/**
 * Algemene helper voor consistente PDF-opmaak.
 * Wordt gebruikt door PdfCertificateService, PdfEvacuationReportService, etc.
 */
public class PdfHelper {

    // ==========================================
    // FONT STYLES
    // ==========================================
    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLACK);
    private static final Font HEADER_FONT = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(0, 51, 153));
    private static final Font TEXT_FONT = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.DARK_GRAY);
    private static final Font FOOTER_FONT = new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY);

    // ==========================================
    // TITEL
    // ==========================================
    /** Voeg een gecentreerde titel toe aan het document. */
    public static void addTitle(@NotNull Document doc, String titleText) throws DocumentException {
        Paragraph title = new Paragraph(titleText, TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);
        doc.add(title);
    }

    // ==========================================
    // TEKST EN LABELS
    // ==========================================
    /** Voeg een label + waarde toe (zoals 'Cursus: BHV Basistraining'). */
    public static void addKeyValue(@NotNull Document doc, String label, String value) throws DocumentException {
        Paragraph p = new Paragraph(label + ": " + (value != null ? value : "-"), TEXT_FONT);
        p.setSpacingAfter(6);
        doc.add(p);
    }

    // ==========================================
    // TABELLEN
    // ==========================================
    /** Voeg een eenvoudige tabel toe met headers en waarden. */
    public static void addTable(@NotNull Document doc, String[] headers, String[] values) throws DocumentException {
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Headers
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, HEADER_FONT));
            cell.setBackgroundColor(new Color(220, 230, 255));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Values
        for (String v : values) {
            PdfPCell cell = new PdfPCell(new Phrase(v != null ? v : "-", TEXT_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        doc.add(table);
    }

    // ==========================================
    // FOOTER
    // ==========================================
    /** Voeg een nette footer toe onderaan het document. */
    public static void addFooter(@NotNull Document doc, String text) throws DocumentException {
        Paragraph footer = new Paragraph(text != null ? text : "", FOOTER_FONT);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(30);
        doc.add(footer);
    }
}
