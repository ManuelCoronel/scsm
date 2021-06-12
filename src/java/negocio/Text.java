/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.HtmlEncoder;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author dunke
 */
public class Text {
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(5);
        table.setWidths(new int[]{1, 2, 1, 1, 1});
        table.addCell(createCell("SKU", 2, 1, Element.ALIGN_LEFT));
        table.addCell(createCell("Description", 2, 1, Element.ALIGN_LEFT));
        table.addCell(createCell("Unit Price", 2, 1, Element.ALIGN_LEFT));
        table.addCell(createCell("Quantity", 2, 1, Element.ALIGN_LEFT));
        table.addCell(createCell("Extension", 2, 1, Element.ALIGN_LEFT));
        String[][] data = {
            {"ABC123", "The descriptive text may be more than one line and the text should wrap automatically", "$5.00", "10", "$50.00"},
            {"QRS557", "Another description", "$100.00", "15", "$1,500.00"},
            {"XYZ999", "Some stuff", "$1.00", "2", "$2.00"}
        };
        for (String[] row : data) {
            table.addCell(createCell(row[0], 1, 1, Element.ALIGN_LEFT));
            table.addCell(createCell(row[1], 1, 1, Element.ALIGN_LEFT));
            table.addCell(createCell(row[2], 1, 1, Element.ALIGN_RIGHT));
            table.addCell(createCell(row[3], 1, 1, Element.ALIGN_RIGHT));
            table.addCell(createCell(row[4], 1, 1, Element.ALIGN_RIGHT));
        }
        table.addCell(createCell("Totals", 2, 4, Element.ALIGN_LEFT));
        table.addCell(createCell("$1,552.00", 2, 1, Element.ALIGN_RIGHT));
        document.add(table);
        document.close();
    }
    
    public PdfPCell createCell(String content, float borderWidth, int colspan, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setBorderWidth(borderWidth);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }
    
    public static void main(String[] args) throws IOException, DocumentException {
       new Text().createPdf("TableNestedDemo.pdf");
    }
}
