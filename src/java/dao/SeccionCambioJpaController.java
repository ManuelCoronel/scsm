/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Cambio;
import dto.SeccionCambio;
import dto.SeccionMicrocurriculo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class SeccionCambioJpaController implements Serializable {

    public SeccionCambioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SeccionCambio seccionCambio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cambio cambioId = seccionCambio.getCambioId();
            if (cambioId != null) {
                cambioId = em.getReference(cambioId.getClass(), cambioId.getId());
                seccionCambio.setCambioId(cambioId);
            }
            SeccionMicrocurriculo seccionMicrocurriculo = seccionCambio.getSeccionMicrocurriculo();
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo = em.getReference(seccionMicrocurriculo.getClass(), seccionMicrocurriculo.getSeccionMicrocurriculoPK());
                seccionCambio.setSeccionMicrocurriculo(seccionMicrocurriculo);
            }
            SeccionMicrocurriculo seccionMicrocurriculo1 = seccionCambio.getSeccionMicrocurriculo1();
            if (seccionMicrocurriculo1 != null) {
                seccionMicrocurriculo1 = em.getReference(seccionMicrocurriculo1.getClass(), seccionMicrocurriculo1.getSeccionMicrocurriculoPK());
                seccionCambio.setSeccionMicrocurriculo1(seccionMicrocurriculo1);
            }
            em.persist(seccionCambio);
            if (cambioId != null) {
                cambioId.getSeccionCambioList().add(seccionCambio);
                cambioId = em.merge(cambioId);
            }
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo.getSeccionCambioList().add(seccionCambio);
                seccionMicrocurriculo = em.merge(seccionMicrocurriculo);
            }
            if (seccionMicrocurriculo1 != null) {
                seccionMicrocurriculo1.getSeccionCambioList().add(seccionCambio);
                seccionMicrocurriculo1 = em.merge(seccionMicrocurriculo1);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SeccionCambio seccionCambio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeccionCambio persistentSeccionCambio = em.find(SeccionCambio.class, seccionCambio.getId());
            Cambio cambioIdOld = persistentSeccionCambio.getCambioId();
            Cambio cambioIdNew = seccionCambio.getCambioId();
            SeccionMicrocurriculo seccionMicrocurriculoOld = persistentSeccionCambio.getSeccionMicrocurriculo();
            SeccionMicrocurriculo seccionMicrocurriculoNew = seccionCambio.getSeccionMicrocurriculo();
            SeccionMicrocurriculo seccionMicrocurriculo1Old = persistentSeccionCambio.getSeccionMicrocurriculo1();
            SeccionMicrocurriculo seccionMicrocurriculo1New = seccionCambio.getSeccionMicrocurriculo1();
            if (cambioIdNew != null) {
                cambioIdNew = em.getReference(cambioIdNew.getClass(), cambioIdNew.getId());
                seccionCambio.setCambioId(cambioIdNew);
            }
            if (seccionMicrocurriculoNew != null) {
                seccionMicrocurriculoNew = em.getReference(seccionMicrocurriculoNew.getClass(), seccionMicrocurriculoNew.getSeccionMicrocurriculoPK());
                seccionCambio.setSeccionMicrocurriculo(seccionMicrocurriculoNew);
            }
            if (seccionMicrocurriculo1New != null) {
                seccionMicrocurriculo1New = em.getReference(seccionMicrocurriculo1New.getClass(), seccionMicrocurriculo1New.getSeccionMicrocurriculoPK());
                seccionCambio.setSeccionMicrocurriculo1(seccionMicrocurriculo1New);
            }
            seccionCambio = em.merge(seccionCambio);
            if (cambioIdOld != null && !cambioIdOld.equals(cambioIdNew)) {
                cambioIdOld.getSeccionCambioList().remove(seccionCambio);
                cambioIdOld = em.merge(cambioIdOld);
            }
            if (cambioIdNew != null && !cambioIdNew.equals(cambioIdOld)) {
                cambioIdNew.getSeccionCambioList().add(seccionCambio);
                cambioIdNew = em.merge(cambioIdNew);
            }
            if (seccionMicrocurriculoOld != null && !seccionMicrocurriculoOld.equals(seccionMicrocurriculoNew)) {
                seccionMicrocurriculoOld.getSeccionCambioList().remove(seccionCambio);
                seccionMicrocurriculoOld = em.merge(seccionMicrocurriculoOld);
            }
            if (seccionMicrocurriculoNew != null && !seccionMicrocurriculoNew.equals(seccionMicrocurriculoOld)) {
                seccionMicrocurriculoNew.getSeccionCambioList().add(seccionCambio);
                seccionMicrocurriculoNew = em.merge(seccionMicrocurriculoNew);
            }
            if (seccionMicrocurriculo1Old != null && !seccionMicrocurriculo1Old.equals(seccionMicrocurriculo1New)) {
                seccionMicrocurriculo1Old.getSeccionCambioList().remove(seccionCambio);
                seccionMicrocurriculo1Old = em.merge(seccionMicrocurriculo1Old);
            }
            if (seccionMicrocurriculo1New != null && !seccionMicrocurriculo1New.equals(seccionMicrocurriculo1Old)) {
                seccionMicrocurriculo1New.getSeccionCambioList().add(seccionCambio);
                seccionMicrocurriculo1New = em.merge(seccionMicrocurriculo1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = seccionCambio.getId();
                if (findSeccionCambio(id) == null) {
                    throw new NonexistentEntityException("The seccionCambio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeccionCambio seccionCambio;
            try {
                seccionCambio = em.getReference(SeccionCambio.class, id);
                seccionCambio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seccionCambio with id " + id + " no longer exists.", enfe);
            }
            Cambio cambioId = seccionCambio.getCambioId();
            if (cambioId != null) {
                cambioId.getSeccionCambioList().remove(seccionCambio);
                cambioId = em.merge(cambioId);
            }
            SeccionMicrocurriculo seccionMicrocurriculo = seccionCambio.getSeccionMicrocurriculo();
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo.getSeccionCambioList().remove(seccionCambio);
                seccionMicrocurriculo = em.merge(seccionMicrocurriculo);
            }
            SeccionMicrocurriculo seccionMicrocurriculo1 = seccionCambio.getSeccionMicrocurriculo1();
            if (seccionMicrocurriculo1 != null) {
                seccionMicrocurriculo1.getSeccionCambioList().remove(seccionCambio);
                seccionMicrocurriculo1 = em.merge(seccionMicrocurriculo1);
            }
            em.remove(seccionCambio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SeccionCambio> findSeccionCambioEntities() {
        return findSeccionCambioEntities(true, -1, -1);
    }

    public List<SeccionCambio> findSeccionCambioEntities(int maxResults, int firstResult) {
        return findSeccionCambioEntities(false, maxResults, firstResult);
    }

    private List<SeccionCambio> findSeccionCambioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SeccionCambio.class));
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

    public SeccionCambio findSeccionCambio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SeccionCambio.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeccionCambioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SeccionCambio> rt = cq.from(SeccionCambio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
