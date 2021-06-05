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
import dto.SeccionMicrocurriculo;
import dto.TipoSeccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class TipoSeccionJpaController implements Serializable {

    public TipoSeccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoSeccion tipoSeccion) {
        if (tipoSeccion.getSeccionMicrocurriculoList() == null) {
            tipoSeccion.setSeccionMicrocurriculoList(new ArrayList<SeccionMicrocurriculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SeccionMicrocurriculo> attachedSeccionMicrocurriculoList = new ArrayList<SeccionMicrocurriculo>();
            for (SeccionMicrocurriculo seccionMicrocurriculoListSeccionMicrocurriculoToAttach : tipoSeccion.getSeccionMicrocurriculoList()) {
                seccionMicrocurriculoListSeccionMicrocurriculoToAttach = em.getReference(seccionMicrocurriculoListSeccionMicrocurriculoToAttach.getClass(), seccionMicrocurriculoListSeccionMicrocurriculoToAttach.getSeccionMicrocurriculoPK());
                attachedSeccionMicrocurriculoList.add(seccionMicrocurriculoListSeccionMicrocurriculoToAttach);
            }
            tipoSeccion.setSeccionMicrocurriculoList(attachedSeccionMicrocurriculoList);
            em.persist(tipoSeccion);
            for (SeccionMicrocurriculo seccionMicrocurriculoListSeccionMicrocurriculo : tipoSeccion.getSeccionMicrocurriculoList()) {
                TipoSeccion oldTipoSeccionIdOfSeccionMicrocurriculoListSeccionMicrocurriculo = seccionMicrocurriculoListSeccionMicrocurriculo.getTipoSeccionId();
                seccionMicrocurriculoListSeccionMicrocurriculo.setTipoSeccionId(tipoSeccion);
                seccionMicrocurriculoListSeccionMicrocurriculo = em.merge(seccionMicrocurriculoListSeccionMicrocurriculo);
                if (oldTipoSeccionIdOfSeccionMicrocurriculoListSeccionMicrocurriculo != null) {
                    oldTipoSeccionIdOfSeccionMicrocurriculoListSeccionMicrocurriculo.getSeccionMicrocurriculoList().remove(seccionMicrocurriculoListSeccionMicrocurriculo);
                    oldTipoSeccionIdOfSeccionMicrocurriculoListSeccionMicrocurriculo = em.merge(oldTipoSeccionIdOfSeccionMicrocurriculoListSeccionMicrocurriculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoSeccion tipoSeccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoSeccion persistentTipoSeccion = em.find(TipoSeccion.class, tipoSeccion.getId());
            List<SeccionMicrocurriculo> seccionMicrocurriculoListOld = persistentTipoSeccion.getSeccionMicrocurriculoList();
            List<SeccionMicrocurriculo> seccionMicrocurriculoListNew = tipoSeccion.getSeccionMicrocurriculoList();
            List<String> illegalOrphanMessages = null;
            for (SeccionMicrocurriculo seccionMicrocurriculoListOldSeccionMicrocurriculo : seccionMicrocurriculoListOld) {
                if (!seccionMicrocurriculoListNew.contains(seccionMicrocurriculoListOldSeccionMicrocurriculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SeccionMicrocurriculo " + seccionMicrocurriculoListOldSeccionMicrocurriculo + " since its tipoSeccionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SeccionMicrocurriculo> attachedSeccionMicrocurriculoListNew = new ArrayList<SeccionMicrocurriculo>();
            for (SeccionMicrocurriculo seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach : seccionMicrocurriculoListNew) {
                seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach = em.getReference(seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach.getClass(), seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach.getSeccionMicrocurriculoPK());
                attachedSeccionMicrocurriculoListNew.add(seccionMicrocurriculoListNewSeccionMicrocurriculoToAttach);
            }
            seccionMicrocurriculoListNew = attachedSeccionMicrocurriculoListNew;
            tipoSeccion.setSeccionMicrocurriculoList(seccionMicrocurriculoListNew);
            tipoSeccion = em.merge(tipoSeccion);
            for (SeccionMicrocurriculo seccionMicrocurriculoListNewSeccionMicrocurriculo : seccionMicrocurriculoListNew) {
                if (!seccionMicrocurriculoListOld.contains(seccionMicrocurriculoListNewSeccionMicrocurriculo)) {
                    TipoSeccion oldTipoSeccionIdOfSeccionMicrocurriculoListNewSeccionMicrocurriculo = seccionMicrocurriculoListNewSeccionMicrocurriculo.getTipoSeccionId();
                    seccionMicrocurriculoListNewSeccionMicrocurriculo.setTipoSeccionId(tipoSeccion);
                    seccionMicrocurriculoListNewSeccionMicrocurriculo = em.merge(seccionMicrocurriculoListNewSeccionMicrocurriculo);
                    if (oldTipoSeccionIdOfSeccionMicrocurriculoListNewSeccionMicrocurriculo != null && !oldTipoSeccionIdOfSeccionMicrocurriculoListNewSeccionMicrocurriculo.equals(tipoSeccion)) {
                        oldTipoSeccionIdOfSeccionMicrocurriculoListNewSeccionMicrocurriculo.getSeccionMicrocurriculoList().remove(seccionMicrocurriculoListNewSeccionMicrocurriculo);
                        oldTipoSeccionIdOfSeccionMicrocurriculoListNewSeccionMicrocurriculo = em.merge(oldTipoSeccionIdOfSeccionMicrocurriculoListNewSeccionMicrocurriculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoSeccion.getId();
                if (findTipoSeccion(id) == null) {
                    throw new NonexistentEntityException("The tipoSeccion with id " + id + " no longer exists.");
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
            TipoSeccion tipoSeccion;
            try {
                tipoSeccion = em.getReference(TipoSeccion.class, id);
                tipoSeccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoSeccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SeccionMicrocurriculo> seccionMicrocurriculoListOrphanCheck = tipoSeccion.getSeccionMicrocurriculoList();
            for (SeccionMicrocurriculo seccionMicrocurriculoListOrphanCheckSeccionMicrocurriculo : seccionMicrocurriculoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoSeccion (" + tipoSeccion + ") cannot be destroyed since the SeccionMicrocurriculo " + seccionMicrocurriculoListOrphanCheckSeccionMicrocurriculo + " in its seccionMicrocurriculoList field has a non-nullable tipoSeccionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoSeccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoSeccion> findTipoSeccionEntities() {
        return findTipoSeccionEntities(true, -1, -1);
    }

    public List<TipoSeccion> findTipoSeccionEntities(int maxResults, int firstResult) {
        return findTipoSeccionEntities(false, maxResults, firstResult);
    }

    private List<TipoSeccion> findTipoSeccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoSeccion.class));
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

    public TipoSeccion findTipoSeccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoSeccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoSeccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoSeccion> rt = cq.from(TipoSeccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
