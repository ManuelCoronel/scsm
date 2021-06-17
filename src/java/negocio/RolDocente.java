/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dto.MateriaPeriodo;
import dto.MateriaPeriodoGrupo;
import dto.Programa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jhoser
 */
public class RolDocente {

    public RolDocente() {
    }

    public List<MateriaPeriodo> microcurriculosDocentes(Programa p, int codigoDocente) {
        MateriaPeriodoGrupo mpg = new MateriaPeriodoGrupo();
        AdministrarGrupos ag = new AdministrarGrupos();
        List<MateriaPeriodoGrupo> lmg = ag.obtenerMateriaPeriodoGrupo(p);
        List<MateriaPeriodo> mp = new ArrayList<>();
        for (MateriaPeriodoGrupo materiaGrupo : lmg) {
            System.out.println(materiaGrupo.getDocente().getCodigoDocente() + " " + codigoDocente);
            if (materiaGrupo.getDocente().getCodigoDocente() == codigoDocente) {
                System.out.println(materiaGrupo.getDocente().getCodigoDocente() + " " + codigoDocente);
                mp.add(materiaGrupo.getMateriaPeriodo());
            }
        }
        return mp;
    }

}
