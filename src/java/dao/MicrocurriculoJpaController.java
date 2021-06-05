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
import dto.AreaFormacion;
import dto.Materia;
import dto.Microcurriculo;
import dto.TipoAsignatura;
import dto.SeccionMicrocurriculo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class MicrocurriculoJpaController implements Serializable {

    public MicrocurriculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Microcurriculo microcurriculo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (microcurriculo.getSeccionMicrocurriculoList() == null) {
            microcurriculo.setSeccionMicrocurriculoList(new ArrayList<SeccionMicrocurriculo>());
        }
        List<String> illegalOrphanMessages = null;
        Materia materiaOrphanCheck = microcurriculo.getMateria();
        if (materiaOrphanCheck != null) {
            Microcurriculo oldMicrocurriculoOfMateria = materiaOrphanCheck.getMicrocurriculo();
            if (oldMicrocurriculoOfMateria != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Materia " + materiaOrphanCheck + " already has an item of type Microcurriculo whose materia column cannot be null. Please make another selection for the materia field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AreaFormacion areaDeFormacionId = microcurriculo.getAreaDeFormacionId();
            if (areaDeFormacionId != null) {
                areaDeFormacionId = em.getReference(areaDeFormacionId.getClass(), areaDeFormacionId.getId());
                microcurriculo.setAreaDeFormacionId(areaDeFormacionId);
            }
            Materia materia = microcurriculo.getMateria();
            if (materia != null) {
                materia = em.getReference(materia.getClass(), materia.getCodigoMateria());
                microcurriculo.setMateria(materia);
            }
            TipoAsignatura tipoAsignaturaId = microcurriculo.getTipoAsignaturaId();
            if (tipoAsignaturaId != null) {
                tipoAsignaturaId = em.getReference(tipoAsignaturaId.getClass(), tipoAsignaturaId.getId());
                microcurriculo.setTipoAsignaturaId(tipoAsignaturaId);
            }
            List<SeccionMicrocurriculo> attachedSeccionMicrocurriculoList = new ArrayList<SeccionMicrocurriculo>();
            for (SeccionMicrocurriculo seccionMicrocurriculoListSeccionMicrocurriculoToAttach : microcurriculo.getSeccionMicrocurriculoList()) {
                seccionMicrocurriculoListSeccionMicrocurriculoToAttach = em.getReference(seccionMicrocurriculoListSeccionMicrocurriculoToAttach.getClass(), seccionMicrocurriculoListSeccionMicrocurriculoToAttach.getSeccionMicrocurriculoPK());
                attachedSeccionMicrocurriculoList.add(seccionMicrocurriculoListSeccionMicrocurriculoToAttach);
            }
            microcurriculo.setSeccionMicrocurriculoList(attachedSeccionMicrocurriculoList);
            em.persist(microcurriculo);
            if (areaDeFormacionId != null) {
                areaDeFormacionId.getMicrocurriculoList().add(microcurriculo);
                areaDeFormacionId = em.merge(areaDeFormacionId);
            }
            if (materia != null) {
                materia.setMicrocurriculo(microcurriculo);
                materia = em.merge(materia);
            }
            if (tipoAsignaturaId != null) {
                tipoAsignaturaId.getMicrocurriculoList().add(microcurriculo);
                tipoAsignaturaId = em.merge(tipoAsignaturaId);
            }
            for (SeccionMicrocurriculo seccionMicrocurriculoListSeccionMicrocurriculo : microcurriculo.getSeccionMicrocurriculoList()) {
                Microcurriculo oldMicrocurriculoOfSeccionMicrocurriculoListSeccionMicrocurriculo = seccionMicrocurriculoListSeccionMicrocurriculo.getMicrocurriculo();
                seccionMicrocurriculoListSeccionMicrocurriculo.setMicrocurriculo(microcurriculo);
                seccionMicrocurriculoListSeccionMicrocurriculo = em.merge(seccionMicrocurriculoListSeccionMicrocurriculo);
                if (oldMicrocurriculoOfSeccionMicrocurriculoListSeccionMicrocurriculo != null) {
                    oldMicrocurriculoOfSeccionMicrocurriculoListSeccionMicrocurriculo.getSeccionMicrocurriculoList().remove(seccionMicrocurriculoListSeccionMicrocurriculo);
                    oldMicrocurriculoOfSeccionMicrocurriculoListSeccionMicrocurriculo = em.merge(oldMicrocurriculoOfSeccionMicrocurriculoListSeccionMicrocurriculo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMicrocurriculo(microcurriculo.getCodigoMateria()) != null) {
                throw new PreexistingEntityException("Microcurriculo " + microcurriculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Microcurriculo microcurriculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Microcurriculo persistentMicrocurriculo = em.find(Microcurriculo.class, microcurriculo.getCodigoMateria());
            AreaFormacion areaDeFormacionIdOld = persistentMicrocurriculo.getAreaDeFormacionId();
            AreaFormacion areaDeFormacionIdNew = microcurriculo.getAreaDeFormacionId();
            Materia materiaOld = persistentMicrocurriculo.getMateria();
            Materia materiaNew = microcurriculo.getMateria();
            TipoAsignatura tipoAsignaturaIdOld = persistentMicrocurriculo.getTipoAsignaturaId();
            TipoAsignatura tipoAsignaturaIdNew = microcurriculo.getTipoAsignaturaId();
            List<SeccionMicrocurriculo> seccionMicrocurriculoListOld = persistentMicrocurriculo.getSeccionMicrocurriculoList();
            List<SeccionMicrocurriculo> seccionMicrocurriculoListNew = microcurriculo.getSeccionMicrocurriculoList();
            List<String> illegalOrphanMessages = null;
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                Microcurriculo oldMicrocurriculoOfMateria = materiaNew.getMicrocurriculo();
                if (oldMicrocurriculoOfMateria != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Materia " + materiaNew + " already has an item of type Microcurriculo whose materia column cannot be null. Please make another selection for the materia field.");
                }
            }
            for (SeccionMicrocurriculo seccionMicrocurriculoListOldSeccionMicrocurriculo : seccionMicrocurriculoListOld) {
                if (!seccionMicrocurriculoListNew.contains(seccionMicrocurriculoListOldSeccionMicrocurriculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SeccionMicrocurriculo " + seccionMicrocurriculoListOldSeccionMicrocurriculo + " since its microcurriculo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (areaDeFormacionIdNew != null) {
                areaDeFormacionIdNew = em.getReference(areaDeFormacionIdNew.getClass(), areaDeFormacionIdNew.getId());
                microcurriculo.setAreaDeFormacionId(areaDeFormacionIdNew);
            }
            if (materiaNew != null) {
                materiaNew = em.getReference(materiaNew.getClass(), materiaNew.getCodigoMateria());
                microcurriculo.setMateria(materiaNew);
            }
            if (tipoAsignaturaIdNew != null) {
                tipoAsignaturaIdNew = em.getReference(tipoAsignaturaIdNew.getClass(), tipoAsignaturaIdNew.getId());
                microcurriculo.setTipoAsignaturaId(tipoAsignaturaIdNew);
            }
            List<SeccionMicrocurriculo> attachedSeccionMicrocurriculoListNew = new ArrayList<SeccionMicrocurriculo>();
            for (SeccionMicrocurriculo seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach : seccionMicrocurriculoListNew) {
                seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach = em.getReference(seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach.getClass(), seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach.getSeccionMicrocurriculoPK());
                attachedSeccionMicrocurriculoListNew.add(seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach);
            }
            seccionMicrocurriculoListNew = attachedSeccionMicrocurriculoListNew;
            microcurriculo.setSeccionMicrocurriculoList(seccionMicrocurriculoListNew);
            microcurriculo = em.merge(microcurriculo);
            if (areaDeFormacionIdOld != null && !areaDeFormacionIdOld.equals(areaDeFormacionIdNew)) {
                areaDeFormacionIdOld.getMicrocurriculoList().remove(microcurriculo);
                areaDeFormacionIdOld = em.merge(areaDeFormacionIdOld);
            }
            if (areaDeFormacionIdNew != null && !areaDeFormacionIdNew.equals(areaDeFormacionIdOld)) {
                areaDeFormacionIdNew.getMicrocurriculoList().add(microcurriculo);
                areaDeFormacionIdNew = em.merge(areaDeFormacionIdNew);
            }
            if (materiaOld != null && !materiaOld.equals(materiaNew)) {
                materiaOld.setMicrocurriculo(null);
                materiaOld = em.merge(materiaOld);
            }
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                materiaNew.setMicrocurriculo(microcurriculo);
                materiaNew = em.merge(materiaNew);
            }
            if (tipoAsignaturaIdOld != null && !tipoAsignaturaIdOld.equals(tipoAsignaturaIdNew)) {
                tipoAsignaturaIdOld.getMicrocurriculoList().remove(microcurriculo);
                tipoAsignaturaIdOld = em.merge(tipoAsignaturaIdOld);
            }
            if (tipoAsignaturaIdNew != null && !tipoAsignaturaIdNew.equals(tipoAsignaturaIdOld)) {
                tipoAsignaturaIdNew.getMicrocurriculoList().add(microcurriculo);
                tipoAsignaturaIdNew = em.merge(tipoAsignaturaIdNew);
            }
            for (SeccionMicrocurriculo seccionMicrocurriculoListNewSeccionMicrocurriculo : seccionMicrocurriculoListNew) {
                if (!seccionMicrocurriculoListOld.contains(seccionMicrocurriculoListNewSeccionMicrocurriculo)) {
                    Microcurriculo oldMicrocurriculoOfSeccionMicrocurriculoListNewSeccionMicrocurriculo = seccionMicrocurriculoListNewSeccionMicrocurriculo.getMicrocurriculo();
                    seccionMicrocurriculoListNewSeccionMicrocurriculo.setMicrocurriculo(microcurriculo);
                    seccionMicrocurriculoListNewSeccionMicrocurriculo = em.merge(seccionMicrocurriculoListNewSeccionMicrocurriculo);
                    if (oldMicrocurriculoOfSeccionMicrocurriculoListNewSeccionMicrocurriculo != null && !oldMicrocurriculoOfSeccionMicrocurriculoListNewSeccionMicrocurriculo.equals(microcurriculo)) {
                        oldMicrocurriculoOfSeccionMicrocurriculoListNewSeccionMicrocurriculo.getSeccionMicrocurriculoList().remove(seccionMicrocurriculoListNewSeccionMicrocurriculo);
                        oldMicrocurriculoOfSeccionMicrocurriculoListNewSeccionMicrocurriculo = em.merge(oldMicrocurriculoOfSeccionMicrocurriculoListNewSeccionMicrocurriculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = microcurriculo.getCodigoMateria();
                if (findMicrocurriculo(id) == null) {
                    throw new NonexistentEntityException("The microcurriculo with id " + id + " no longer exists.");
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
            Microcurriculo microcurriculo;
            try {
                microcurriculo = em.getReference(Microcurriculo.class, id);
                microcurriculo.getCodigoMateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The microcurriculo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SeccionMicrocurriculo> seccionMicrocurriculoListOrphanCheck = microcurriculo.getSeccionMicrocurriculoList();
            for (SeccionMicrocurriculo seccionMicrocurriculoListOrphanCheckSeccionMicrocurriculo : seccionMicrocurriculoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Microcurriculo (" + microcurriculo + ") cannot be destroyed since the SeccionMicrocurriculo " + seccionMicrocurriculoListOrphanCheckSeccionMicrocurriculo + " in its seccionMicrocurriculoList field has a non-nullable microcurriculo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AreaFormacion areaDeFormacionId = microcurriculo.getAreaDeFormacionId();
            if (areaDeFormacionId != null) {
                areaDeFormacionId.getMicrocurriculoList().remove(microcurriculo);
                areaDeFormacionId = em.merge(areaDeFormacionId);
            }
            Materia materia = microcurriculo.getMateria();
            if (materia != null) {
                materia.setMicrocurriculo(null);
                materia = em.merge(materia);
            }
            TipoAsignatura tipoAsignaturaId = microcurriculo.getTipoAsignaturaId();
            if (tipoAsignaturaId != null) {
                tipoAsignaturaId.getMicrocurriculoList().remove(microcurriculo);
                tipoAsignaturaId = em.merge(tipoAsignaturaId);
            }
            em.remove(microcurriculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Microcurriculo> findMicrocurriculoEntities() {
        return findMicrocurriculoEntities(true, -1, -1);
    }

    public List<Microcurriculo> findMicrocurriculoEntities(int maxResults, int firstResult) {
        return findMicrocurriculoEntities(false, maxResults, firstResult);
    }

    private List<Microcurriculo> findMicrocurriculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Microcurriculo.class));
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

    public Microcurriculo findMicrocurriculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Microcurriculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getMicrocurriculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Microcurriculo> rt = cq.from(Microcurriculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
