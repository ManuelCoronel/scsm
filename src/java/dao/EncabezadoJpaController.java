/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Encabezado;
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
public class EncabezadoJpaController implements Serializable {

    public EncabezadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Encabezado encabezado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(encabezado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEncabezado(encabezado.getId()) != null) {
                throw new PreexistingEntityException("Encabezado " + encabezado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Encabezado encabezado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            encabezado = em.merge(encabezado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encabezado.getId();
                if (findEncabezado(id) == null) {
                    throw new NonexistentEntityException("The encabezado with id " + id + " no longer exists.");
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
            Encabezado encabezado;
            try {
                encabezado = em.getReference(Encabezado.class, id);
                encabezado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encabezado with id " + id + " no longer exists.", enfe);
            }
            em.remove(encabezado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Encabezado> findEncabezadoEntities() {
        return findEncabezadoEntities(true, -1, -1);
    }

    public List<Encabezado> findEncabezadoEntities(int maxResults, int firstResult) {
        return findEncabezadoEntities(false, maxResults, firstResult);
    }

    private List<Encabezado> findEncabezadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encabezado.class));
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

    public Encabezado findEncabezado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encabezado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncabezadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Encabezado> rt = cq.from(Encabezado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
