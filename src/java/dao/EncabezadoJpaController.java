/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Encabezado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.EncabezadoTabla;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    public void create(Encabezado encabezado) {
        if (encabezado.getEncabezadoTablaList() == null) {
            encabezado.setEncabezadoTablaList(new ArrayList<EncabezadoTabla>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EncabezadoTabla> attachedEncabezadoTablaList = new ArrayList<EncabezadoTabla>();
            for (EncabezadoTabla encabezadoTablaListEncabezadoTablaToAttach : encabezado.getEncabezadoTablaList()) {
                encabezadoTablaListEncabezadoTablaToAttach = em.getReference(encabezadoTablaListEncabezadoTablaToAttach.getClass(), encabezadoTablaListEncabezadoTablaToAttach.getId());
                attachedEncabezadoTablaList.add(encabezadoTablaListEncabezadoTablaToAttach);
            }
            encabezado.setEncabezadoTablaList(attachedEncabezadoTablaList);
            em.persist(encabezado);
            for (EncabezadoTabla encabezadoTablaListEncabezadoTabla : encabezado.getEncabezadoTablaList()) {
                Encabezado oldIdEncabezadoOfEncabezadoTablaListEncabezadoTabla = encabezadoTablaListEncabezadoTabla.getIdEncabezado();
                encabezadoTablaListEncabezadoTabla.setIdEncabezado(encabezado);
                encabezadoTablaListEncabezadoTabla = em.merge(encabezadoTablaListEncabezadoTabla);
                if (oldIdEncabezadoOfEncabezadoTablaListEncabezadoTabla != null) {
                    oldIdEncabezadoOfEncabezadoTablaListEncabezadoTabla.getEncabezadoTablaList().remove(encabezadoTablaListEncabezadoTabla);
                    oldIdEncabezadoOfEncabezadoTablaListEncabezadoTabla = em.merge(oldIdEncabezadoOfEncabezadoTablaListEncabezadoTabla);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Encabezado encabezado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encabezado persistentEncabezado = em.find(Encabezado.class, encabezado.getId());
            List<EncabezadoTabla> encabezadoTablaListOld = persistentEncabezado.getEncabezadoTablaList();
            List<EncabezadoTabla> encabezadoTablaListNew = encabezado.getEncabezadoTablaList();
            List<String> illegalOrphanMessages = null;
            for (EncabezadoTabla encabezadoTablaListOldEncabezadoTabla : encabezadoTablaListOld) {
                if (!encabezadoTablaListNew.contains(encabezadoTablaListOldEncabezadoTabla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EncabezadoTabla " + encabezadoTablaListOldEncabezadoTabla + " since its idEncabezado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EncabezadoTabla> attachedEncabezadoTablaListNew = new ArrayList<EncabezadoTabla>();
            for (EncabezadoTabla encabezadoTablaListNewEncabezadoTablaToAttach : encabezadoTablaListNew) {
                encabezadoTablaListNewEncabezadoTablaToAttach = em.getReference(encabezadoTablaListNewEncabezadoTablaToAttach.getClass(), encabezadoTablaListNewEncabezadoTablaToAttach.getId());
                attachedEncabezadoTablaListNew.add(encabezadoTablaListNewEncabezadoTablaToAttach);
            }
            encabezadoTablaListNew = attachedEncabezadoTablaListNew;
            encabezado.setEncabezadoTablaList(encabezadoTablaListNew);
            encabezado = em.merge(encabezado);
            for (EncabezadoTabla encabezadoTablaListNewEncabezadoTabla : encabezadoTablaListNew) {
                if (!encabezadoTablaListOld.contains(encabezadoTablaListNewEncabezadoTabla)) {
                    Encabezado oldIdEncabezadoOfEncabezadoTablaListNewEncabezadoTabla = encabezadoTablaListNewEncabezadoTabla.getIdEncabezado();
                    encabezadoTablaListNewEncabezadoTabla.setIdEncabezado(encabezado);
                    encabezadoTablaListNewEncabezadoTabla = em.merge(encabezadoTablaListNewEncabezadoTabla);
                    if (oldIdEncabezadoOfEncabezadoTablaListNewEncabezadoTabla != null && !oldIdEncabezadoOfEncabezadoTablaListNewEncabezadoTabla.equals(encabezado)) {
                        oldIdEncabezadoOfEncabezadoTablaListNewEncabezadoTabla.getEncabezadoTablaList().remove(encabezadoTablaListNewEncabezadoTabla);
                        oldIdEncabezadoOfEncabezadoTablaListNewEncabezadoTabla = em.merge(oldIdEncabezadoOfEncabezadoTablaListNewEncabezadoTabla);
                    }
                }
            }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<EncabezadoTabla> encabezadoTablaListOrphanCheck = encabezado.getEncabezadoTablaList();
            for (EncabezadoTabla encabezadoTablaListOrphanCheckEncabezadoTabla : encabezadoTablaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Encabezado (" + encabezado + ") cannot be destroyed since the EncabezadoTabla " + encabezadoTablaListOrphanCheckEncabezadoTabla + " in its encabezadoTablaList field has a non-nullable idEncabezado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
