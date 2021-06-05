/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.EncabezadoTabla;
import dto.EncabezadoTablaPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.TablaMicrocurriculo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (encabezadoTabla.getEncabezadoTablaPK() == null) {
            encabezadoTabla.setEncabezadoTablaPK(new EncabezadoTablaPK());
        }
        encabezadoTabla.getEncabezadoTablaPK().setTablaMicrocurriculoId(encabezadoTabla.getTablaMicrocurriculo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaMicrocurriculo tablaMicrocurriculo = encabezadoTabla.getTablaMicrocurriculo();
            if (tablaMicrocurriculo != null) {
                tablaMicrocurriculo = em.getReference(tablaMicrocurriculo.getClass(), tablaMicrocurriculo.getId());
                encabezadoTabla.setTablaMicrocurriculo(tablaMicrocurriculo);
            }
            em.persist(encabezadoTabla);
            if (tablaMicrocurriculo != null) {
                tablaMicrocurriculo.getEncabezadoTablaList().add(encabezadoTabla);
                tablaMicrocurriculo = em.merge(tablaMicrocurriculo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEncabezadoTabla(encabezadoTabla.getEncabezadoTablaPK()) != null) {
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
        encabezadoTabla.getEncabezadoTablaPK().setTablaMicrocurriculoId(encabezadoTabla.getTablaMicrocurriculo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncabezadoTabla persistentEncabezadoTabla = em.find(EncabezadoTabla.class, encabezadoTabla.getEncabezadoTablaPK());
            TablaMicrocurriculo tablaMicrocurriculoOld = persistentEncabezadoTabla.getTablaMicrocurriculo();
            TablaMicrocurriculo tablaMicrocurriculoNew = encabezadoTabla.getTablaMicrocurriculo();
            if (tablaMicrocurriculoNew != null) {
                tablaMicrocurriculoNew = em.getReference(tablaMicrocurriculoNew.getClass(), tablaMicrocurriculoNew.getId());
                encabezadoTabla.setTablaMicrocurriculo(tablaMicrocurriculoNew);
            }
            encabezadoTabla = em.merge(encabezadoTabla);
            if (tablaMicrocurriculoOld != null && !tablaMicrocurriculoOld.equals(tablaMicrocurriculoNew)) {
                tablaMicrocurriculoOld.getEncabezadoTablaList().remove(encabezadoTabla);
                tablaMicrocurriculoOld = em.merge(tablaMicrocurriculoOld);
            }
            if (tablaMicrocurriculoNew != null && !tablaMicrocurriculoNew.equals(tablaMicrocurriculoOld)) {
                tablaMicrocurriculoNew.getEncabezadoTablaList().add(encabezadoTabla);
                tablaMicrocurriculoNew = em.merge(tablaMicrocurriculoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EncabezadoTablaPK id = encabezadoTabla.getEncabezadoTablaPK();
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

    public void destroy(EncabezadoTablaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncabezadoTabla encabezadoTabla;
            try {
                encabezadoTabla = em.getReference(EncabezadoTabla.class, id);
                encabezadoTabla.getEncabezadoTablaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encabezadoTabla with id " + id + " no longer exists.", enfe);
            }
            TablaMicrocurriculo tablaMicrocurriculo = encabezadoTabla.getTablaMicrocurriculo();
            if (tablaMicrocurriculo != null) {
                tablaMicrocurriculo.getEncabezadoTablaList().remove(encabezadoTabla);
                tablaMicrocurriculo = em.merge(tablaMicrocurriculo);
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

    public EncabezadoTabla findEncabezadoTabla(EncabezadoTablaPK id) {
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
