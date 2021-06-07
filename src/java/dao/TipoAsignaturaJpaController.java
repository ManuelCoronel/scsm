/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Microcurriculo;
import dto.TipoAsignatura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class TipoAsignaturaJpaController implements Serializable {

    public TipoAsignaturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAsignatura tipoAsignatura) {
        if (tipoAsignatura.getMicrocurriculoList() == null) {
            tipoAsignatura.setMicrocurriculoList(new ArrayList<Microcurriculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Microcurriculo> attachedMicrocurriculoList = new ArrayList<Microcurriculo>();
            for (Microcurriculo microcurriculoListMicrocurriculoToAttach : tipoAsignatura.getMicrocurriculoList()) {
                microcurriculoListMicrocurriculoToAttach = em.getReference(microcurriculoListMicrocurriculoToAttach.getClass(), microcurriculoListMicrocurriculoToAttach.getId());
                attachedMicrocurriculoList.add(microcurriculoListMicrocurriculoToAttach);
            }
            tipoAsignatura.setMicrocurriculoList(attachedMicrocurriculoList);
            em.persist(tipoAsignatura);
            for (Microcurriculo microcurriculoListMicrocurriculo : tipoAsignatura.getMicrocurriculoList()) {
                TipoAsignatura oldTipoAsignaturaIdOfMicrocurriculoListMicrocurriculo = microcurriculoListMicrocurriculo.getTipoAsignaturaId();
                microcurriculoListMicrocurriculo.setTipoAsignaturaId(tipoAsignatura);
                microcurriculoListMicrocurriculo = em.merge(microcurriculoListMicrocurriculo);
                if (oldTipoAsignaturaIdOfMicrocurriculoListMicrocurriculo != null) {
                    oldTipoAsignaturaIdOfMicrocurriculoListMicrocurriculo.getMicrocurriculoList().remove(microcurriculoListMicrocurriculo);
                    oldTipoAsignaturaIdOfMicrocurriculoListMicrocurriculo = em.merge(oldTipoAsignaturaIdOfMicrocurriculoListMicrocurriculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAsignatura tipoAsignatura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAsignatura persistentTipoAsignatura = em.find(TipoAsignatura.class, tipoAsignatura.getId());
            List<Microcurriculo> microcurriculoListOld = persistentTipoAsignatura.getMicrocurriculoList();
            List<Microcurriculo> microcurriculoListNew = tipoAsignatura.getMicrocurriculoList();
            List<String> illegalOrphanMessages = null;
            for (Microcurriculo microcurriculoListOldMicrocurriculo : microcurriculoListOld) {
                if (!microcurriculoListNew.contains(microcurriculoListOldMicrocurriculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Microcurriculo " + microcurriculoListOldMicrocurriculo + " since its tipoAsignaturaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Microcurriculo> attachedMicrocurriculoListNew = new ArrayList<Microcurriculo>();
            for (Microcurriculo microcurriculoListNewMicrocurriculoToAttach : microcurriculoListNew) {
                microcurriculoListNewMicrocurriculoToAttach = em.getReference(microcurriculoListNewMicrocurriculoToAttach.getClass(), microcurriculoListNewMicrocurriculoToAttach.getId());
                attachedMicrocurriculoListNew.add(microcurriculoListNewMicrocurriculoToAttach);
            }
            microcurriculoListNew = attachedMicrocurriculoListNew;
            tipoAsignatura.setMicrocurriculoList(microcurriculoListNew);
            tipoAsignatura = em.merge(tipoAsignatura);
            for (Microcurriculo microcurriculoListNewMicrocurriculo : microcurriculoListNew) {
                if (!microcurriculoListOld.contains(microcurriculoListNewMicrocurriculo)) {
                    TipoAsignatura oldTipoAsignaturaIdOfMicrocurriculoListNewMicrocurriculo = microcurriculoListNewMicrocurriculo.getTipoAsignaturaId();
                    microcurriculoListNewMicrocurriculo.setTipoAsignaturaId(tipoAsignatura);
                    microcurriculoListNewMicrocurriculo = em.merge(microcurriculoListNewMicrocurriculo);
                    if (oldTipoAsignaturaIdOfMicrocurriculoListNewMicrocurriculo != null && !oldTipoAsignaturaIdOfMicrocurriculoListNewMicrocurriculo.equals(tipoAsignatura)) {
                        oldTipoAsignaturaIdOfMicrocurriculoListNewMicrocurriculo.getMicrocurriculoList().remove(microcurriculoListNewMicrocurriculo);
                        oldTipoAsignaturaIdOfMicrocurriculoListNewMicrocurriculo = em.merge(oldTipoAsignaturaIdOfMicrocurriculoListNewMicrocurriculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAsignatura.getId();
                if (findTipoAsignatura(id) == null) {
                    throw new NonexistentEntityException("The tipoAsignatura with id " + id + " no longer exists.");
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
            TipoAsignatura tipoAsignatura;
            try {
                tipoAsignatura = em.getReference(TipoAsignatura.class, id);
                tipoAsignatura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAsignatura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Microcurriculo> microcurriculoListOrphanCheck = tipoAsignatura.getMicrocurriculoList();
            for (Microcurriculo microcurriculoListOrphanCheckMicrocurriculo : microcurriculoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoAsignatura (" + tipoAsignatura + ") cannot be destroyed since the Microcurriculo " + microcurriculoListOrphanCheckMicrocurriculo + " in its microcurriculoList field has a non-nullable tipoAsignaturaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoAsignatura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAsignatura> findTipoAsignaturaEntities() {
        return findTipoAsignaturaEntities(true, -1, -1);
    }

    public List<TipoAsignatura> findTipoAsignaturaEntities(int maxResults, int firstResult) {
        return findTipoAsignaturaEntities(false, maxResults, firstResult);
    }

    private List<TipoAsignatura> findTipoAsignaturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAsignatura.class));
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

    public TipoAsignatura findTipoAsignatura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAsignatura.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAsignaturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAsignatura> rt = cq.from(TipoAsignatura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
