/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dao_alternativo.MateriaJpaAlternativo;
import dao.PensumJpaController;
import dao.ProgramaJpaController;
import dto.Pensum;
import dto.PensumPK;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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

    public Pensum registrar(Integer id_programa, InputStream pensumFile) throws IOException, Exception {
        LectorPensum l = new LectorPensum();
        l.parsePDFDocument(cargarPensum(pensumFile, id_programa));

        int count = 1;
        ProgramaJpaController prjpa = new ProgramaJpaController(Conexion.getConexion().getBd());
        PensumJpaController pjpa = new PensumJpaController(Conexion.getConexion().getBd());
        List<Pensum> lp = pjpa.findPensumEntities();

        for (Pensum p : lp) {
            if (p.getPensumPK().getProgramaCodigo() == id_programa) {
                count++;
            }
        }

        Pensum p = new Pensum(new PensumPK(count, id_programa));
        p.setPrograma(prjpa.findPrograma(id_programa));
        pjpa.create(p);

        p.setMateriaList(l.getMaterias());
        new MateriaJpaAlternativo(MyConnection.getConnection()).create(p);
        
        return p;
    }

    private String cargarPensum(InputStream pensumeFile, Integer id_programa) throws IOException {
        File folder = new File(this.realPathServer);
        folder = new File(folder.getParentFile().getParentFile().getAbsolutePath() + "/temp");
        System.out.println(folder.getAbsolutePath() + "---" + folder.exists());
        InputStream fileContent = pensumeFile;
        File file = File.createTempFile("pensum-" + id_programa, ".pdf", folder);

        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file.getAbsolutePath();
    }

    public dto.Pensum obtenerPensum(int idPrograma, int codigo, int programaCodigo) {
        Conexion con = Conexion.getConexion();
        dao.PensumJpaController daoPensum = new dao.PensumJpaController(con.getBd());
        Pensum pensum= daoPensum.findPensum(new dto.PensumPK(codigo, programaCodigo));
        return pensum;
    }

}
