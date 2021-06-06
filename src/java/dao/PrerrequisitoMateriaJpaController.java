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
import dto.Materia;
import dto.PrerrequisitoMateria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class PrerrequisitoMateriaJpaController implements Serializable {

    public PrerrequisitoMateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrerrequisitoMateria prerrequisitoMateria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia materiaCodigoMateria = prerrequisitoMateria.getMateriaCodigoMateria();
            if (materiaCodigoMateria != null) {
                materiaCodigoMateria = em.getReference(materiaCodigoMateria.getClass(), materiaCodigoMateria.getMateriaPK());
                prerrequisitoMateria.setMateriaCodigoMateria(materiaCodigoMateria);
            }
            Materia materiaCodigoPrerrequisito = prerrequisitoMateria.getMateriaCodigoPrerrequisito();
            if (materiaCodigoPrerrequisito != null) {
                materiaCodigoPrerrequisito = em.getReference(materiaCodigoPrerrequisito.getClass(), materiaCodigoPrerrequisito.getMateriaPK());
                prerrequisitoMateria.setMateriaCodigoPrerrequisito(materiaCodigoPrerrequisito);
            }
            em.persist(prerrequisitoMateria);
            if (materiaCodigoMateria != null) {
                materiaCodigoMateria.getPrerrequisitoMateriaList().add(prerrequisitoMateria);
                materiaCodigoMateria = em.merge(materiaCodigoMateria);
            }
            if (materiaCodigoPrerrequisito != null) {
                materiaCodigoPrerrequisito.getPrerrequisitoMateriaList().add(prerrequisitoMateria);
                materiaCodigoPrerrequisito = em.merge(materiaCodigoPrerrequisito);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrerrequisitoMateria prerrequisitoMateria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrerrequisitoMateria persistentPrerrequisitoMateria = em.find(PrerrequisitoMateria.class, prerrequisitoMateria.getId());
            Materia materiaCodigoMateriaOld = persistentPrerrequisitoMateria.getMateriaCodigoMateria();
            Materia materiaCodigoMateriaNew = prerrequisitoMateria.getMateriaCodigoMateria();
            Materia materiaCodigoPrerrequisitoOld = persistentPrerrequisitoMateria.getMateriaCodigoPrerrequisito();
            Materia materiaCodigoPrerrequisitoNew = prerrequisitoMateria.getMateriaCodigoPrerrequisito();
            if (materiaCodigoMateriaNew != null) {
                materiaCodigoMateriaNew = em.getReference(materiaCodigoMateriaNew.getClass(), materiaCodigoMateriaNew.getMateriaPK());
                prerrequisitoMateria.setMateriaCodigoMateria(materiaCodigoMateriaNew);
            }
            if (materiaCodigoPrerrequisitoNew != null) {
                materiaCodigoPrerrequisitoNew = em.getReference(materiaCodigoPrerrequisitoNew.getClass(), materiaCodigoPrerrequisitoNew.getMateriaPK());
                prerrequisitoMateria.setMateriaCodigoPrerrequisito(materiaCodigoPrerrequisitoNew);
            }
            prerrequisitoMateria = em.merge(prerrequisitoMateria);
            if (materiaCodigoMateriaOld != null && !materiaCodigoMateriaOld.equals(materiaCodigoMateriaNew)) {
                materiaCodigoMateriaOld.getPrerrequisitoMateriaList().remove(prerrequisitoMateria);
                materiaCodigoMateriaOld = em.merge(materiaCodigoMateriaOld);
            }
            if (materiaCodigoMateriaNew != null && !materiaCodigoMateriaNew.equals(materiaCodigoMateriaOld)) {
                materiaCodigoMateriaNew.getPrerrequisitoMateriaList().add(prerrequisitoMateria);
                materiaCodigoMateriaNew = em.merge(materiaCodigoMateriaNew);
            }
            if (materiaCodigoPrerrequisitoOld != null && !materiaCodigoPrerrequisitoOld.equals(materiaCodigoPrerrequisitoNew)) {
                materiaCodigoPrerrequisitoOld.getPrerrequisitoMateriaList().remove(prerrequisitoMateria);
                materiaCodigoPrerrequisitoOld = em.merge(materiaCodigoPrerrequisitoOld);
            }
            if (materiaCodigoPrerrequisitoNew != null && !materiaCodigoPrerrequisitoNew.equals(materiaCodigoPrerrequisitoOld)) {
                materiaCodigoPrerrequisitoNew.getPrerrequisitoMateriaList().add(prerrequisitoMateria);
                materiaCodigoPrerrequisitoNew = em.merge(materiaCodigoPrerrequisitoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prerrequisitoMateria.getId();
                if (findPrerrequisitoMateria(id) == null) {
                    throw new NonexistentEntityException("The prerrequisitoMateria with id " + id + " no longer exists.");
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
            PrerrequisitoMateria prerrequisitoMateria;
            try {
                prerrequisitoMateria = em.getReference(PrerrequisitoMateria.class, id);
                prerrequisitoMateria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prerrequisitoMateria with id " + id + " no longer exists.", enfe);
            }
            Materia materiaCodigoMateria = prerrequisitoMateria.getMateriaCodigoMateria();
            if (materiaCodigoMateria != null) {
                materiaCodigoMateria.getPrerrequisitoMateriaList().remove(prerrequisitoMateria);
                materiaCodigoMateria = em.merge(materiaCodigoMateria);
            }
            Materia materiaCodigoPrerrequisito = prerrequisitoMateria.getMateriaCodigoPrerrequisito();
            if (materiaCodigoPrerrequisito != null) {
                materiaCodigoPrerrequisito.getPrerrequisitoMateriaList().remove(prerrequisitoMateria);
                materiaCodigoPrerrequisito = em.merge(materiaCodigoPrerrequisito);
            }
            em.remove(prerrequisitoMateria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrerrequisitoMateria> findPrerrequisitoMateriaEntities() {
        return findPrerrequisitoMateriaEntities(true, -1, -1);
    }

    public List<PrerrequisitoMateria> findPrerrequisitoMateriaEntities(int maxResults, int firstResult) {
        return findPrerrequisitoMateriaEntities(false, maxResults, firstResult);
    }

    private List<PrerrequisitoMateria> findPrerrequisitoMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrerrequisitoMateria.class));
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

    public PrerrequisitoMateria findPrerrequisitoMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrerrequisitoMateria.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrerrequisitoMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrerrequisitoMateria> rt = cq.from(PrerrequisitoMateria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
