/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.AreaFormacionJpaController;
import dao.MicrocurriculoJpaController;
import dao.TipoAsignaturaJpaController;
import dto.AreaFormacion;
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
    
    public void createPDF() throws DocumentException, BadElementException, IOException {
        this.doc.open();
        this.doc.add(this.getTablaInfo());
        
        for(SeccionMicrocurriculo sm : this.m.getSeccionMicrocurriculoList()){
            this.doc.add(getParapgraph(sm.getSeccionId().getNombre(), 11, Font.BOLD, Paragraph.ALIGN_CENTER));
            this.createBlank();
            if(sm.getSeccionId().getTipoSeccionId().getId() == 2){
                this.doc.add(this.getTabla(sm.getTablaMicrocurriculoList().get(0)));
            }else{
                this.doc.add(getParapgraph(sm.getContenidoList().get(0).getTexto(), 9, Font.NORMAL, Paragraph.ALIGN_JUSTIFIED));
            }
            this.createBlank();
        }
        
        this.doc.close();
    }
    
    public PdfPTable getTablaEnca() throws DocumentException, BadElementException, IOException {
        PdfPTable tab = new PdfPTable(3);
        tab.setWidths(new int[]{1, 4, 1});
        tab.getDefaultCell().setRowspan(3);
        tab.addCell(Image.getInstance("imgs/logoufps.png"));

        tab.getDefaultCell().setRowspan(2);
        tab.addCell(
            "UNIVERSIDAD FRANCISCO DE PAULA SANTANDER "+
            "FACULTAD DE "+this.m.getMateria().getPensum().getPrograma().getDepartamentoId().getFacultadId().getNombre().toUpperCase()+" "+
            "PROGRAMA "+this.m.getMateria().getPensum().getPrograma().getNombrePrograma().toUpperCase()
        );
        tab.getDefaultCell().setRowspan(3);
//        tab.addCell(this.m.getMateria().getPensum().getPrograma().getImgPrograma());
        tab.addCell("logoprograma");
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
            Phrase p = new Phrase(a.getNombre());
            if(this.m.getAreaDeFormacionId().getId().equals(a.getId())){
                p.add(this.getParapgraph(": X", 15, Font.BOLD, Paragraph.ALIGN_LEFT));
            }
            tab.addCell(p);
        }

        tab.addCell("Tipo de asignatura:");
        tab.getDefaultCell().setColspan(2);
        for(TipoAsignatura t: this.ta){
            Phrase p = new Phrase(t.getTipo());
            if(this.m.getAreaDeFormacionId().getId().equals(t.getId())){
                p.add(this.getParapgraph(": X", 15, Font.BOLD, Paragraph.ALIGN_LEFT));
            }
            tab.addCell(p);

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
        tab.setWidthPercentage(100);
        return tab;
    }

    private PdfPTable getTabla(TablaMicrocurriculo tm) throws DocumentException{
        PdfPTable tab = new PdfPTable(tm.getCantidadColumnas());
        if(tm.getTablaMicrocurriculoPK().getId()==1){
            tab.setWidths(new int[]{1, 2, 1, 1, 1});
        }
        tab.setWidthPercentage(100);
        this.configEnca(tab, tm);
        return tab;
    }
    
    private void configEnca(PdfPTable tab, TablaMicrocurriculo tm){
        int i = 0;
        for (EncabezadoTabla et : tm.getEncabezadoTablaList()) {
            if (i++ == 2 && tm.getTablaMicrocurriculoPK().getId()==1) {
                tab.getDefaultCell().setColspan(2);
                tab.addCell(this.getParapgraph("Dedicacion del estudiante (Horas)", 9, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.getDefaultCell().setColspan(1);
                tab.getDefaultCell().setRowspan(2);
                tab.addCell(this.getParapgraph(tm.getEncabezadoTablaList().get(++i).getEncabezadoId().getNombre(), 9, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.getDefaultCell().setRowspan(1);

                tab.addCell(this.getParapgraph("a)Trabajo Presencial", 9, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.addCell(this.getParapgraph("b)Trabajo Independiente", 9, Font.BOLD, Paragraph.ALIGN_CENTER));
                break;
            } else {
                if(tm.getTablaMicrocurriculoPK().getId()==1) tab.getDefaultCell().setRowspan(2);
                tab.addCell(this.getParapgraph(et.getEncabezadoId().getNombre(), 9, Font.BOLD, Paragraph.ALIGN_CENTER));
                tab.getDefaultCell().setRowspan(1);
            }
        }
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
        Microcurriculo m = new MicrocurriculoJpaController(Conexion.getConexion().getBd()).findMicrocurriculo(new MicrocurriculoPK(27, 1150505, 1));
        
        MicrocurriculoPDF t = new MicrocurriculoPDF(m);
        t.createPDF();    
    }
    
}
