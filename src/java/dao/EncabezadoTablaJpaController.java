/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.EncabezadoTabla;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Manuel
 */
public class EncabezadoTablaJpaController implements Serializable {

    public EncabezadoTablaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EncabezadoTabla encabezadoTabla) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(encabezadoTabla);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEncabezadoTabla(encabezadoTabla.getId()) != null) {
                throw new PreexistingEntityException("EncabezadoTabla " + encabezadoTabla + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EncabezadoTabla encabezadoTabla) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            encabezadoTabla = em.merge(encabezadoTabla);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encabezadoTabla.getId();
                if (findEncabezadoTabla(id) == null) {
                    throw new NonexistentEntityException("The encabezadoTabla with id " + id + " no longer exists.");
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
            EncabezadoTabla encabezadoTabla;
            try {
                encabezadoTabla = em.getReference(EncabezadoTabla.class, id);
                encabezadoTabla.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encabezadoTabla with id " + id + " no longer exists.", enfe);
            }
            em.remove(encabezadoTabla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EncabezadoTabla> findEncabezadoTablaEntities() {
        return findEncabezadoTablaEntities(true, -1, -1);
    }

    public List<EncabezadoTabla> findEncabezadoTablaEntities(int maxResults, int firstResult) {
        return findEncabezadoTablaEntities(false, maxResults, firstResult);
    }

    private List<EncabezadoTabla> findEncabezadoTablaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EncabezadoTabla.class));
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

    public EncabezadoTabla findEncabezadoTabla(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EncabezadoTabla.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncabezadoTablaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EncabezadoTabla> rt = cq.from(EncabezadoTabla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
