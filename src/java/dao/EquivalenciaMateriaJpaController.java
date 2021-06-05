/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.EquivalenciaMateria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Materia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class EquivalenciaMateriaJpaController implements Serializable {

    public EquivalenciaMateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EquivalenciaMateria equivalenciaMateria) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Materia materiaOrphanCheck = equivalenciaMateria.getMateria();
        if (materiaOrphanCheck != null) {
            EquivalenciaMateria oldEquivalenciaMateriaOfMateria = materiaOrphanCheck.getEquivalenciaMateria();
            if (oldEquivalenciaMateriaOfMateria != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Materia " + materiaOrphanCheck + " already has an item of type EquivalenciaMateria whose materia column cannot be null. Please make another selection for the materia field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia materia = equivalenciaMateria.getMateria();
            if (materia != null) {
                materia = em.getReference(materia.getClass(), materia.getCodigoMateria());
                equivalenciaMateria.setMateria(materia);
            }
            em.persist(equivalenciaMateria);
            if (materia != null) {
                materia.setEquivalenciaMateria(equivalenciaMateria);
                materia = em.merge(materia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquivalenciaMateria(equivalenciaMateria.getMateriaCodigoMateria()) != null) {
                throw new PreexistingEntityException("EquivalenciaMateria " + equivalenciaMateria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EquivalenciaMateria equivalenciaMateria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EquivalenciaMateria persistentEquivalenciaMateria = em.find(EquivalenciaMateria.class, equivalenciaMateria.getMateriaCodigoMateria());
            Materia materiaOld = persistentEquivalenciaMateria.getMateria();
            Materia materiaNew = equivalenciaMateria.getMateria();
            List<String> illegalOrphanMessages = null;
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                EquivalenciaMateria oldEquivalenciaMateriaOfMateria = materiaNew.getEquivalenciaMateria();
                if (oldEquivalenciaMateriaOfMateria != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Materia " + materiaNew + " already has an item of type EquivalenciaMateria whose materia column cannot be null. Please make another selection for the materia field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (materiaNew != null) {
                materiaNew = em.getReference(materiaNew.getClass(), materiaNew.getCodigoMateria());
                equivalenciaMateria.setMateria(materiaNew);
            }
            equivalenciaMateria = em.merge(equivalenciaMateria);
            if (materiaOld != null && !materiaOld.equals(materiaNew)) {
                materiaOld.setEquivalenciaMateria(null);
                materiaOld = em.merge(materiaOld);
            }
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                materiaNew.setEquivalenciaMateria(equivalenciaMateria);
                materiaNew = em.merge(materiaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equivalenciaMateria.getMateriaCodigoMateria();
                if (findEquivalenciaMateria(id) == null) {
                    throw new NonexistentEntityException("The equivalenciaMateria with id " + id + " no longer exists.");
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
            EquivalenciaMateria equivalenciaMateria;
            try {
                equivalenciaMateria = em.getReference(EquivalenciaMateria.class, id);
                equivalenciaMateria.getMateriaCodigoMateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equivalenciaMateria with id " + id + " no longer exists.", enfe);
            }
            Materia materia = equivalenciaMateria.getMateria();
            if (materia != null) {
                materia.setEquivalenciaMateria(null);
                materia = em.merge(materia);
            }
            em.remove(equivalenciaMateria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EquivalenciaMateria> findEquivalenciaMateriaEntities() {
        return findEquivalenciaMateriaEntities(true, -1, -1);
    }

    public List<EquivalenciaMateria> findEquivalenciaMateriaEntities(int maxResults, int firstResult) {
        return findEquivalenciaMateriaEntities(false, maxResults, firstResult);
    }

    private List<EquivalenciaMateria> findEquivalenciaMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EquivalenciaMateria.class));
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

    public EquivalenciaMateria findEquivalenciaMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EquivalenciaMateria.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquivalenciaMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EquivalenciaMateria> rt = cq.from(EquivalenciaMateria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
