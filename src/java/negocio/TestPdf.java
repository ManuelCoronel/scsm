/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
public class TestPdf {

    private static String[] encabezados = {"Unidad No",
        "Nombre de las Unidades",
        "Dedicación del estudiante (Horas)",
        "Horas Totales (a+b)"};

    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, DocumentException, IOException {
        File f = new File("C:\\Users\\dunke\\OneDrive\\Documentos\\NetBeansProjects\\scsm\\temp");
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(f.getAbsolutePath() + "\\micro.pdf"));
        doc.open();
        doc.add(new Phrase("lol"));
        PdfPTable tab = new PdfPTable(5);
        tab.setWidths(new int[]{1, 2, 1, 1, 1});
        int i=0;
        for (String e : encabezados) {
            if (i++ == 2) {
                tab.getDefaultCell().setColspan(2);
                tab.addCell(e);
                tab.getDefaultCell().setColspan(1);
                tab.getDefaultCell().setRowspan(2);
                tab.addCell(encabezados[i]);
                tab.getDefaultCell().setRowspan(1);
                
                tab.addCell("TP");
                tab.addCell("TI");
                break;
            } else {
                tab.getDefaultCell().setRowspan(2);
                tab.addCell(e);
                tab.getDefaultCell().setRowspan(1);
            }
        }
        tab.addCell("1");
        tab.addCell("2");
        tab.addCell("3");
        tab.addCell("4");
        tab.addCell("5");

        doc.add(tab);
        doc.close();
    }
    
    /*
    for (String e : encabezados) {
            if (i++ == 2) {
                PdfPTable t1 = new PdfPTable(1);
                t1.addCell(new Paragraph(e, FontFactory.getFont("arial",
                        9,
                        Font.BOLD
                )));
                PdfPTable t2 = new PdfPTable(2);
                t2.addCell(new Paragraph("a)Trabajo Presencial", FontFactory.getFont("arial",
                        5,
                        Font.BOLD
                )));
                t2.addCell(new Paragraph("b)Trabajo Independiente", FontFactory.getFont("arial",
                        5,
                        Font.BOLD
                )));
                tab.addCell(t1);
                tab.addCell(t2);
            } else {
                tab.addCell(new Paragraph(e, FontFactory.getFont("arial",
                        9,
                        Font.BOLD
                )));
            }
        }
    */
}
