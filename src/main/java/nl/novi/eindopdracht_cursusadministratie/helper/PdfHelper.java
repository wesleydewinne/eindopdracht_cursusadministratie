package nl.novi.eindopdracht_cursusadministratie.helper;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.*;

/**
 * Algemene PDF helper voor opmaak en stijl.
 */
public class PdfHelper {

    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLACK);
    private static final Font TEXT_FONT = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.DARK_GRAY);
    private static final Font HEADER_FONT = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0, 51, 153));

    public static void addTitle(Document doc, String titleText) throws DocumentException {
        Paragraph title = new Paragraph(titleText, TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        doc.add(title);
    }

    public static void addKeyValue(Document doc, String label, String value) throws DocumentException {
        Paragraph p = new Paragraph(label + ": " + value, TEXT_FONT);
        p.setSpacingAfter(8);
        doc.add(p);
    }

    public static void addTable(Document doc, String[] headers, String[] values) throws DocumentException {
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);

        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, HEADER_FONT));
            cell.setBackgroundColor(new Color(220, 230, 255));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        for (String v : values) {
            PdfPCell cell = new PdfPCell(new Phrase(v, TEXT_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        doc.add(table);
    }

    public static void addFooter(Document doc, String text) throws DocumentException {
        Paragraph footer = new Paragraph(text, new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(30);
        doc.add(footer);
    }
}
