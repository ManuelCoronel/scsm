/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dao.EquivalenciaMateriaJpaController;
import dao.MateriaJpaController;
import dao_alternativo.MateriaJpaAlternativo;
import dao.PensumJpaController;
import dao.PrerrequisitoMateriaJpaController;
import dao.ProgramaJpaController;
import dto.EquivalenciaMateria;
import dto.Materia;
import dto.Pensum;
import dto.PensumPK;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import util.Conexion;
import util.MyConnection;

/**
 *
 * @author dunke
 */
public class AdministrarPensum {

    private String realPathServer;

    public AdministrarPensum(String realPathServer) {
        this.realPathServer = realPathServer;
    }

    public AdministrarPensum() {
    }
    

    public Pensum registrar(Integer id_programa, InputStream pensumFile) throws IOException, Exception {
        LectorPensum l = new LectorPensum();
        l.parsePDFDocument(cargarPensum(pensumFile, id_programa));

        int count = 1;
        EntityManagerFactory em = Conexion.getConexion().getBd();
        ProgramaJpaController prjpa = new ProgramaJpaController(em);
        PensumJpaController pjpa = new PensumJpaController(em);
        List<Pensum> lp = pjpa.findPensumEntities();

        for (Pensum p : lp) {
            if (p.getPensumPK().getProgramaCodigo() == id_programa) {
                count++;
            }
        }

        Pensum p = new Pensum(new PensumPK(count, id_programa));
        p.setPrograma(prjpa.findPrograma(id_programa));
        pjpa.create(p);

        MateriaJpaController mjpa = new MateriaJpaController(em);
        EquivalenciaMateriaJpaController ejpa = new EquivalenciaMateriaJpaController(em);
        PrerrequisitoMateriaJpaController prejpa = new PrerrequisitoMateriaJpaController(em);
        List<Materia> materias = l.getMaterias(count);
        for(Materia m: materias){
        }
        
//        p.setMateriaList(l.getMaterias(count));
        new MateriaJpaAlternativo(MyConnection.getConnection()).create(p);
        
        return p;
    }

    private String cargarPensum(InputStream pensumeFile, Integer id_programa) throws IOException {
        File folder = new File(this.realPathServer);
        folder = new File(folder.getParentFile().getParentFile().getAbsolutePath() + "/temp");
        InputStream fileContent = pensumeFile;
        File file = File.createTempFile("pensum-" + id_programa, ".pdf", folder);

        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file.getAbsolutePath();
    }

    public dto.Pensum obtenerPensum( int codigo, int programaCodigo) {
        Conexion con = Conexion.getConexion();
        dao.PensumJpaController daoPensum = new dao.PensumJpaController(con.getBd());
        Pensum pensum= daoPensum.findPensum(new dto.PensumPK(codigo, programaCodigo));
        return pensum;
    }

}
