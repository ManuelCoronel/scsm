/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Pensum;
import dto.Microcurriculo;
import dto.Materia;
import java.util.ArrayList;
import java.util.List;
import dto.EquivalenciaMateria;
import dto.MateriaPeriodo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class MateriaJpaController implements Serializable {

    public MateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) throws PreexistingEntityException, Exception {
        if (materia.getMateriaList() == null) {
            materia.setMateriaList(new ArrayList<Materia>());
        }
        if (materia.getMateriaList1() == null) {
            materia.setMateriaList1(new ArrayList<Materia>());
        }
        if (materia.getEquivalenciaMateriaList() == null) {
            materia.setEquivalenciaMateriaList(new ArrayList<EquivalenciaMateria>());
        }
        if (materia.getMateriaPeriodoList() == null) {
            materia.setMateriaPeriodoList(new ArrayList<MateriaPeriodo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pensum pensum = materia.getPensum();
            if (pensum != null) {
                pensum = em.getReference(pensum.getClass(), pensum.getPensumPK());
                materia.setPensum(pensum);
            }
            Microcurriculo microcurriculo = materia.getMicrocurriculo();
            if (microcurriculo != null) {
                microcurriculo = em.getReference(microcurriculo.getClass(), microcurriculo.getCodigoMateria());
                materia.setMicrocurriculo(microcurriculo);
            }
            List<Materia> attachedMateriaList = new ArrayList<Materia>();
            for (Materia materiaListMateriaToAttach : materia.getMateriaList()) {
                materiaListMateriaToAttach = em.getReference(materiaListMateriaToAttach.getClass(), materiaListMateriaToAttach.getCodigoMateria());
                attachedMateriaList.add(materiaListMateriaToAttach);
            }
            materia.setMateriaList(attachedMateriaList);
            List<Materia> attachedMateriaList1 = new ArrayList<Materia>();
            for (Materia materiaList1MateriaToAttach : materia.getMateriaList1()) {
                materiaList1MateriaToAttach = em.getReference(materiaList1MateriaToAttach.getClass(), materiaList1MateriaToAttach.getCodigoMateria());
                attachedMateriaList1.add(materiaList1MateriaToAttach);
            }
            materia.setMateriaList1(attachedMateriaList1);
            List<EquivalenciaMateria> attachedEquivalenciaMateriaList = new ArrayList<EquivalenciaMateria>();
            for (EquivalenciaMateria equivalenciaMateriaListEquivalenciaMateriaToAttach : materia.getEquivalenciaMateriaList()) {
                equivalenciaMateriaListEquivalenciaMateriaToAttach = em.getReference(equivalenciaMateriaListEquivalenciaMateriaToAttach.getClass(), equivalenciaMateriaListEquivalenciaMateriaToAttach.getEquivalenciaMateriaPK());
                attachedEquivalenciaMateriaList.add(equivalenciaMateriaListEquivalenciaMateriaToAttach);
            }
            materia.setEquivalenciaMateriaList(attachedEquivalenciaMateriaList);
            List<MateriaPeriodo> attachedMateriaPeriodoList = new ArrayList<MateriaPeriodo>();
            for (MateriaPeriodo materiaPeriodoListMateriaPeriodoToAttach : materia.getMateriaPeriodoList()) {
                materiaPeriodoListMateriaPeriodoToAttach = em.getReference(materiaPeriodoListMateriaPeriodoToAttach.getClass(), materiaPeriodoListMateriaPeriodoToAttach.getMateriaPeriodoPK());
                attachedMateriaPeriodoList.add(materiaPeriodoListMateriaPeriodoToAttach);
            }
            materia.setMateriaPeriodoList(attachedMateriaPeriodoList);
            em.persist(materia);
            if (pensum != null) {
                pensum.getMateriaList().add(materia);
                pensum = em.merge(pensum);
            }
            if (microcurriculo != null) {
                Materia oldMateriaOfMicrocurriculo = microcurriculo.getMateria();
                if (oldMateriaOfMicrocurriculo != null) {
                    oldMateriaOfMicrocurriculo.setMicrocurriculo(null);
                    oldMateriaOfMicrocurriculo = em.merge(oldMateriaOfMicrocurriculo);
                }
                microcurriculo.setMateria(materia);
                microcurriculo = em.merge(microcurriculo);
            }
            for (Materia materiaListMateria : materia.getMateriaList()) {
                materiaListMateria.getMateriaList().add(materia);
                materiaListMateria = em.merge(materiaListMateria);
            }
            for (Materia materiaList1Materia : materia.getMateriaList1()) {
                materiaList1Materia.getMateriaList().add(materia);
                materiaList1Materia = em.merge(materiaList1Materia);
            }
            for (EquivalenciaMateria equivalenciaMateriaListEquivalenciaMateria : materia.getEquivalenciaMateriaList()) {
                Materia oldMateriaOfEquivalenciaMateriaListEquivalenciaMateria = equivalenciaMateriaListEquivalenciaMateria.getMateria();
                equivalenciaMateriaListEquivalenciaMateria.setMateria(materia);
                equivalenciaMateriaListEquivalenciaMateria = em.merge(equivalenciaMateriaListEquivalenciaMateria);
                if (oldMateriaOfEquivalenciaMateriaListEquivalenciaMateria != null) {
                    oldMateriaOfEquivalenciaMateriaListEquivalenciaMateria.getEquivalenciaMateriaList().remove(equivalenciaMateriaListEquivalenciaMateria);
                    oldMateriaOfEquivalenciaMateriaListEquivalenciaMateria = em.merge(oldMateriaOfEquivalenciaMateriaListEquivalenciaMateria);
                }
            }
            for (MateriaPeriodo materiaPeriodoListMateriaPeriodo : materia.getMateriaPeriodoList()) {
                Materia oldMateriaOfMateriaPeriodoListMateriaPeriodo = materiaPeriodoListMateriaPeriodo.getMateria();
                materiaPeriodoListMateriaPeriodo.setMateria(materia);
                materiaPeriodoListMateriaPeriodo = em.merge(materiaPeriodoListMateriaPeriodo);
                if (oldMateriaOfMateriaPeriodoListMateriaPeriodo != null) {
                    oldMateriaOfMateriaPeriodoListMateriaPeriodo.getMateriaPeriodoList().remove(materiaPeriodoListMateriaPeriodo);
                    oldMateriaOfMateriaPeriodoListMateriaPeriodo = em.merge(oldMateriaOfMateriaPeriodoListMateriaPeriodo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMateria(materia.getCodigoMateria()) != null) {
                throw new PreexistingEntityException("Materia " + materia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia persistentMateria = em.find(Materia.class, materia.getCodigoMateria());
            Pensum pensumOld = persistentMateria.getPensum();
            Pensum pensumNew = materia.getPensum();
            Microcurriculo microcurriculoOld = persistentMateria.getMicrocurriculo();
            Microcurriculo microcurriculoNew = materia.getMicrocurriculo();
            List<Materia> materiaListOld = persistentMateria.getMateriaList();
            List<Materia> materiaListNew = materia.getMateriaList();
            List<Materia> materiaList1Old = persistentMateria.getMateriaList1();
            List<Materia> materiaList1New = materia.getMateriaList1();
            List<EquivalenciaMateria> equivalenciaMateriaListOld = persistentMateria.getEquivalenciaMateriaList();
            List<EquivalenciaMateria> equivalenciaMateriaListNew = materia.getEquivalenciaMateriaList();
            List<MateriaPeriodo> materiaPeriodoListOld = persistentMateria.getMateriaPeriodoList();
            List<MateriaPeriodo> materiaPeriodoListNew = materia.getMateriaPeriodoList();
            List<String> illegalOrphanMessages = null;
            if (microcurriculoOld != null && !microcurriculoOld.equals(microcurriculoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Microcurriculo " + microcurriculoOld + " since its materia field is not nullable.");
            }
            for (EquivalenciaMateria equivalenciaMateriaListOldEquivalenciaMateria : equivalenciaMateriaListOld) {
                if (!equivalenciaMateriaListNew.contains(equivalenciaMateriaListOldEquivalenciaMateria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EquivalenciaMateria " + equivalenciaMateriaListOldEquivalenciaMateria + " since its materia field is not nullable.");
                }
            }
            for (MateriaPeriodo materiaPeriodoListOldMateriaPeriodo : materiaPeriodoListOld) {
                if (!materiaPeriodoListNew.contains(materiaPeriodoListOldMateriaPeriodo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MateriaPeriodo " + materiaPeriodoListOldMateriaPeriodo + " since its materia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pensumNew != null) {
                pensumNew = em.getReference(pensumNew.getClass(), pensumNew.getPensumPK());
                materia.setPensum(pensumNew);
            }
            if (microcurriculoNew != null) {
                microcurriculoNew = em.getReference(microcurriculoNew.getClass(), microcurriculoNew.getCodigoMateria());
                materia.setMicrocurriculo(microcurriculoNew);
            }
            List<Materia> attachedMateriaListNew = new ArrayList<Materia>();
            for (Materia materiaListNewMateriaToAttach : materiaListNew) {
                materiaListNewMateriaToAttach = em.getReference(materiaListNewMateriaToAttach.getClass(), materiaListNewMateriaToAttach.getCodigoMateria());
                attachedMateriaListNew.add(materiaListNewMateriaToAttach);
            }
            materiaListNew = attachedMateriaListNew;
            materia.setMateriaList(materiaListNew);
            List<Materia> attachedMateriaList1New = new ArrayList<Materia>();
            for (Materia materiaList1NewMateriaToAttach : materiaList1New) {
                materiaList1NewMateriaToAttach = em.getReference(materiaList1NewMateriaToAttach.getClass(), materiaList1NewMateriaToAttach.getCodigoMateria());
                attachedMateriaList1New.add(materiaList1NewMateriaToAttach);
            }
            materiaList1New = attachedMateriaList1New;
            materia.setMateriaList1(materiaList1New);
            List<EquivalenciaMateria> attachedEquivalenciaMateriaListNew = new ArrayList<EquivalenciaMateria>();
            for (EquivalenciaMateria equivalenciaMateriaListNewEquivalenciaMateriaToAttach : equivalenciaMateriaListNew) {
                equivalenciaMateriaListNewEquivalenciaMateriaToAttach = em.getReference(equivalenciaMateriaListNewEquivalenciaMateriaToAttach.getClass(), equivalenciaMateriaListNewEquivalenciaMateriaToAttach.getEquivalenciaMateriaPK());
                attachedEquivalenciaMateriaListNew.add(equivalenciaMateriaListNewEquivalenciaMateriaToAttach);
            }
            equivalenciaMateriaListNew = attachedEquivalenciaMateriaListNew;
            materia.setEquivalenciaMateriaList(equivalenciaMateriaListNew);
            List<MateriaPeriodo> attachedMateriaPeriodoListNew = new ArrayList<MateriaPeriodo>();
            for (MateriaPeriodo materiaPeriodoListNewMateriaPeriodoToAttach : materiaPeriodoListNew) {
                materiaPeriodoListNewMateriaPeriodoToAttach = em.getReference(materiaPeriodoListNewMateriaPeriodoToAttach.getClass(), materiaPeriodoListNewMateriaPeriodoToAttach.getMateriaPeriodoPK());
                attachedMateriaPeriodoListNew.add(materiaPeriodoListNewMateriaPeriodoToAttach);
            }
            materiaPeriodoListNew = attachedMateriaPeriodoListNew;
            materia.setMateriaPeriodoList(materiaPeriodoListNew);
            materia = em.merge(materia);
            if (pensumOld != null && !pensumOld.equals(pensumNew)) {
                pensumOld.getMateriaList().remove(materia);
                pensumOld = em.merge(pensumOld);
            }
            if (pensumNew != null && !pensumNew.equals(pensumOld)) {
                pensumNew.getMateriaList().add(materia);
                pensumNew = em.merge(pensumNew);
            }
            if (microcurriculoNew != null && !microcurriculoNew.equals(microcurriculoOld)) {
                Materia oldMateriaOfMicrocurriculo = microcurriculoNew.getMateria();
                if (oldMateriaOfMicrocurriculo != null) {
                    oldMateriaOfMicrocurriculo.setMicrocurriculo(null);
                    oldMateriaOfMicrocurriculo = em.merge(oldMateriaOfMicrocurriculo);
                }
                microcurriculoNew.setMateria(materia);
                microcurriculoNew = em.merge(microcurriculoNew);
            }
            for (Materia materiaListOldMateria : materiaListOld) {
                if (!materiaListNew.contains(materiaListOldMateria)) {
                    materiaListOldMateria.getMateriaList().remove(materia);
                    materiaListOldMateria = em.merge(materiaListOldMateria);
                }
            }
            for (Materia materiaListNewMateria : materiaListNew) {
                if (!materiaListOld.contains(materiaListNewMateria)) {
                    materiaListNewMateria.getMateriaList().add(materia);
                    materiaListNewMateria = em.merge(materiaListNewMateria);
                }
            }
            for (Materia materiaList1OldMateria : materiaList1Old) {
                if (!materiaList1New.contains(materiaList1OldMateria)) {
                    materiaList1OldMateria.getMateriaList().remove(materia);
                    materiaList1OldMateria = em.merge(materiaList1OldMateria);
                }
            }
            for (Materia materiaList1NewMateria : materiaList1New) {
                if (!materiaList1Old.contains(materiaList1NewMateria)) {
                    materiaList1NewMateria.getMateriaList().add(materia);
                    materiaList1NewMateria = em.merge(materiaList1NewMateria);
                }
            }
            for (EquivalenciaMateria equivalenciaMateriaListNewEquivalenciaMateria : equivalenciaMateriaListNew) {
                if (!equivalenciaMateriaListOld.contains(equivalenciaMateriaListNewEquivalenciaMateria)) {
                    Materia oldMateriaOfEquivalenciaMateriaListNewEquivalenciaMateria = equivalenciaMateriaListNewEquivalenciaMateria.getMateria();
                    equivalenciaMateriaListNewEquivalenciaMateria.setMateria(materia);
                    equivalenciaMateriaListNewEquivalenciaMateria = em.merge(equivalenciaMateriaListNewEquivalenciaMateria);
                    if (oldMateriaOfEquivalenciaMateriaListNewEquivalenciaMateria != null && !oldMateriaOfEquivalenciaMateriaListNewEquivalenciaMateria.equals(materia)) {
                        oldMateriaOfEquivalenciaMateriaListNewEquivalenciaMateria.getEquivalenciaMateriaList().remove(equivalenciaMateriaListNewEquivalenciaMateria);
                        oldMateriaOfEquivalenciaMateriaListNewEquivalenciaMateria = em.merge(oldMateriaOfEquivalenciaMateriaListNewEquivalenciaMateria);
                    }
                }
            }
            for (MateriaPeriodo materiaPeriodoListNewMateriaPeriodo : materiaPeriodoListNew) {
                if (!materiaPeriodoListOld.contains(materiaPeriodoListNewMateriaPeriodo)) {
                    Materia oldMateriaOfMateriaPeriodoListNewMateriaPeriodo = materiaPeriodoListNewMateriaPeriodo.getMateria();
                    materiaPeriodoListNewMateriaPeriodo.setMateria(materia);
                    materiaPeriodoListNewMateriaPeriodo = em.merge(materiaPeriodoListNewMateriaPeriodo);
                    if (oldMateriaOfMateriaPeriodoListNewMateriaPeriodo != null && !oldMateriaOfMateriaPeriodoListNewMateriaPeriodo.equals(materia)) {
                        oldMateriaOfMateriaPeriodoListNewMateriaPeriodo.getMateriaPeriodoList().remove(materiaPeriodoListNewMateriaPeriodo);
                        oldMateriaOfMateriaPeriodoListNewMateriaPeriodo = em.merge(oldMateriaOfMateriaPeriodoListNewMateriaPeriodo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materia.getCodigoMateria();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getCodigoMateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Microcurriculo microcurriculoOrphanCheck = materia.getMicrocurriculo();
            if (microcurriculoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the Microcurriculo " + microcurriculoOrphanCheck + " in its microcurriculo field has a non-nullable materia field.");
            }
            List<EquivalenciaMateria> equivalenciaMateriaListOrphanCheck = materia.getEquivalenciaMateriaList();
            for (EquivalenciaMateria equivalenciaMateriaListOrphanCheckEquivalenciaMateria : equivalenciaMateriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the EquivalenciaMateria " + equivalenciaMateriaListOrphanCheckEquivalenciaMateria + " in its equivalenciaMateriaList field has a non-nullable materia field.");
            }
            List<MateriaPeriodo> materiaPeriodoListOrphanCheck = materia.getMateriaPeriodoList();
            for (MateriaPeriodo materiaPeriodoListOrphanCheckMateriaPeriodo : materiaPeriodoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the MateriaPeriodo " + materiaPeriodoListOrphanCheckMateriaPeriodo + " in its materiaPeriodoList field has a non-nullable materia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pensum pensum = materia.getPensum();
            if (pensum != null) {
                pensum.getMateriaList().remove(materia);
                pensum = em.merge(pensum);
            }
            List<Materia> materiaList = materia.getMateriaList();
            for (Materia materiaListMateria : materiaList) {
                materiaListMateria.getMateriaList().remove(materia);
                materiaListMateria = em.merge(materiaListMateria);
            }
            List<Materia> materiaList1 = materia.getMateriaList1();
            for (Materia materiaList1Materia : materiaList1) {
                materiaList1Materia.getMateriaList().remove(materia);
                materiaList1Materia = em.merge(materiaList1Materia);
            }
            em.remove(materia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Materia findMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
