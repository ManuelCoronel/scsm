/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.HtmlEncoder;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import dao.AreaFormacionJpaController;
import dao.MicrocurriculoJpaController;
import dao.TipoAsignaturaJpaController;
import dto.AreaFormacion;
import dto.Encabezado;
import dto.EncabezadoTabla;
import dto.Materia;
import dto.Microcurriculo;
import dto.MicrocurriculoPK;
import dto.PrerrequisitoMateria;
import dto.SeccionMicrocurriculo;
import dto.TablaMicrocurriculo;
import dto.TipoAsignatura;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import util.Conexion;

/**
 *
 * @author dunke
 */
public class MicrocurriculoPDF{
    private Microcurriculo m;
    private List<AreaFormacion> areas;
    private List<TipoAsignatura> ta;
    private Document doc;

    public MicrocurriculoPDF(Microcurriculo m) throws FileNotFoundException, DocumentException, BadElementException, IOException {
        EntityManagerFactory em = Conexion.getConexion().getBd();
        
        this.m = m;
        this.ta = new TipoAsignaturaJpaController(em).findTipoAsignaturaEntities();
        this.areas = new AreaFormacionJpaController(em).findAreaFormacionEntities();
        TableHeader th = new TableHeader(this.getTablaEnca());
        this.doc = new Document(PageSize.A4, 36, 36, 20 + th.getTableHeight(), 36);
        
        PdfWriter.getInstance(this.doc, new FileOutputStream(new File("").getAbsolutePath() + "\\temp\\micro.pdf")).setPageEvent(th);
    }
    
    public String createPDF() throws DocumentException, BadElementException, IOException {
        this.doc.open();
        
        this.doc.add(this.getTablaInfo());
        
        PdfPTable tab = new PdfPTable(1);
        int i=0;
        for(SeccionMicrocurriculo sm : this.m.getSeccionMicrocurriculoList()){
            this.doc.add(getParapgraph(sm.getSeccionId().getNombre(), 11, Font.BOLD, Paragraph.ALIGN_CENTER));
            this.createBlank();
            if(sm.getSeccionId().getTipoSeccionId().getId() == 2){
                this.doc.add(this.getTabla(sm.getTablaMicrocurriculoList().get(i++)));
            }else{
                this.doc.add(getParapgraph(sm.getContenidoList().get(0).getTexto(), 9, Font.NORMAL, Paragraph.ALIGN_JUSTIFIED));
            }
            this.createBlank();
        }
        tab.addCell("JUSTIFICACIÓN Y UBICACIÓN EN EL PROGRAMA");
        tab.addCell("OBJETIVO GENERAL");
        tab.addCell("COMPETENCIAS");

        this.doc.close();
        return "";
    }
    
    public PdfPTable getTablaEnca() throws DocumentException, BadElementException, IOException {
        PdfPTable tab = new PdfPTable(3);
        tab.setWidths(new int[]{1, 4, 1});
        tab.getDefaultCell().setRowspan(3);
        tab.addCell(Image.getInstance("imgs/logoufps.png"));

        tab.getDefaultCell().setRowspan(2);
        tab.addCell(
            "UNIVERSIDAD FRANCISCO DE PAULA SANTANDER "+
            this.m.getMateria().getPensum().getPrograma().getDepartamentoId().getFacultadId().getNombre().toUpperCase()+" "+
            this.m.getMateria().getPensum().getPrograma().getNombrePrograma().toUpperCase()
        );
        tab.getDefaultCell().setRowspan(3);
        tab.addCell("imgs/logoufps.png");
        tab.getDefaultCell().setRowspan(1);
        tab.addCell("Formato Syllabus");
        return tab;
    }

    public PdfPTable getTablaInfo() throws DocumentException, BadElementException, IOException {
        Materia ma = this.m.getMateria();
        PdfPTable tab = new PdfPTable(5);
        tab.setWidths(new int[]{1, 1, 1, 1, 1});

        tab.addCell("Asignatura");
        tab.getDefaultCell().setColspan(4);
        tab.addCell(ma.getNombre());
        tab.getDefaultCell().setColspan(1);
        tab.addCell("Código");
        tab.getDefaultCell().setColspan(4);
        tab.addCell(""+ma.getMateriaPK().getCodigoMateria());

        tab.getDefaultCell().setColspan(1);
        tab.addCell("Área de formación:");
        for(AreaFormacion a: this.areas){
            tab.addCell(a.getNombre());
            if(this.m.getAreaDeFormacionId().getId().equals(a.getId())){
                tab.addCell(this.getParapgraph(": X", 9, Font.BOLD, Paragraph.ALIGN_LEFT));
            }
        }

        tab.addCell("Tipo de asignatura:");
        tab.getDefaultCell().setColspan(2);
        for(TipoAsignatura t: this.ta){
            tab.addCell(t.getTipo());
            if(ma.getTipoAsignaturaId().getId().equals(t.getId())){
                tab.addCell(this.getParapgraph(": X", 9, Font.BOLD, Paragraph.ALIGN_LEFT));
            }
        }

        tab.getDefaultCell().setColspan(1);
        tab.addCell("Número de Créditos");
        tab.getDefaultCell().setColspan(4);
        tab.addCell(""+ma.getCreditos());

        tab.getDefaultCell().setColspan(1);
        tab.addCell("Prerrequisitos");
        tab.getDefaultCell().setColspan(4);
        String pr = "";
        for(PrerrequisitoMateria pm: ma.getPrerrequisitoMateriaList()){
            pr += pm.getMateria1().getMateriaPK().getCodigoMateria()+" - "+pm.getMateria1().getNombre();
        }
        tab.addCell(pr);        
        return tab;
    }

    public PdfPTable tablaContenidoUnidades() {
        PdfPTable tab = new PdfPTable(3);
        tab.addCell("CONTENIDOS POR UNIDADES");
        tab.addCell("Actividades Presenciales");
        tab.addCell("Trabajo independiente");

        return tab;
    }

    private PdfPTable getTabla(TablaMicrocurriculo tm){
        PdfPTable tab = new PdfPTable(tm.getCantColumnas());
        this.configEnca(tab, tm);
        return tab;
    }
    
    private void configEnca(PdfPTable tab, TablaMicrocurriculo tm){
        int i = 0;
        for (EncabezadoTabla et : tm.getEncabezadoTablaList()) {
            if (i++ == 1 && tm.getTablaMicrocurriculoPK().getId()==1) {
                tab.getDefaultCell().setColspan(2);
                tab.addCell(this.getParapgraph(et.getIdEncabezado().getNombre(), 11, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.getDefaultCell().setColspan(1);
                tab.getDefaultCell().setRowspan(2);
                tab.addCell(this.getParapgraph(tm.getEncabezadoTablaList().get(i++).getIdEncabezado().getNombre(), 11, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.getDefaultCell().setRowspan(1);

                tab.addCell(this.getParapgraph(tm.getEncabezadoTablaList().get(i++).getIdEncabezado().getNombre(), 11, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.addCell(this.getParapgraph(tm.getEncabezadoTablaList().get(i).getIdEncabezado().getNombre(), 11, Font.BOLD, Paragraph.ALIGN_CENTER));
                break;
            } else {
                if(tm.getTablaMicrocurriculoPK().getId()==1) tab.getDefaultCell().setRowspan(2);
                tab.addCell(this.getParapgraph(et.getIdEncabezado().getNombre(), 11, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.getDefaultCell().setRowspan(1);
            }
        }
    }
    
    public PdfPTable tablaUnidades() throws DocumentException {
        PdfPTable tab = new PdfPTable(5);
        tab.setWidths(new int[]{1, 2, 1, 1, 1});
        int i = 0;
        for (String e : new String[]{"1"}) {
            if (i++ == 2) {
                tab.getDefaultCell().setColspan(2);
                tab.addCell(e);
                tab.getDefaultCell().setColspan(1);
                tab.getDefaultCell().setRowspan(2);
                tab.addCell(new String[]{"1"}[i]);
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
        return tab;
    }

    public PdfPTable tablaInfo2() {
        PdfPTable tab = new PdfPTable(1);
        tab.addCell("PRINCIPALES PRÁCTICAS PEDAGÓGICAS A UTILIZAR – METODOLOGÍA");
        tab.addCell("ESTRATEGIAS DE EVALUACIÓN");
        tab.addCell("RECURSOS UTILIZADOS");
        tab.addCell("BIBLIOGRAFÍA");

        return tab;
    }

    public Paragraph getParapgraph(String e, int tam, int font, int orien) {
        Paragraph p = new Paragraph(e, FontFactory.getFont("arial", tam, font));
        p.setAlignment(orien);
        return p;
    }
    
    public void createBlank() throws DocumentException{
        this.doc.add(getParapgraph("\n", 9, Font.BOLD, Paragraph.ALIGN_CENTER));
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, DocumentException, IOException {
        Microcurriculo m = new MicrocurriculoJpaController(Conexion.getConexion().getBd()).findMicrocurriculo(new MicrocurriculoPK(2, 1150102, 1));
        
        MicrocurriculoPDF t = new MicrocurriculoPDF(m);
        t.createPDF();
        
//        MicrocurriculoPDF t = new MicrocurriculoPDF(null);
//        TableHeader th = new TableHeader(t.tablaEnca());
//        Document doc = new Document(PageSize.A4, 36, 36, 20 + th.getTableHeight(), 36);
//        PdfWriter.getInstance(doc, new FileOutputStream(new File("").getAbsolutePath() + "\\temp\\micro.pdf")).setPageEvent(th);
//        
//        doc.open();
//        doc.add(t.tablaInfoMicro());
//        doc.add(new Paragraph("\n"));
//
//
//        doc.add(t.tablaUnidades());
//        doc.add(new Paragraph("\n"));
//        doc.add(t.tablaInfo1());
//        doc.add(new Paragraph("\n"));
//        doc.add(t.tablaContenidoUnidades());
//        doc.add(new Paragraph("\n"));
//        doc.add(t.tablaInfo2());
//
//        doc.close();        
    }
    
}
