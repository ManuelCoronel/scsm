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
import dto.Encabezado;
import dto.EncabezadoTabla;
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

    public void create(EncabezadoTabla encabezadoTabla) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encabezado idEncabezado = encabezadoTabla.getIdEncabezado();
            if (idEncabezado != null) {
                idEncabezado = em.getReference(idEncabezado.getClass(), idEncabezado.getId());
                encabezadoTabla.setIdEncabezado(idEncabezado);
            }
            TablaMicrocurriculo tablaMicrocurriculo = encabezadoTabla.getTablaMicrocurriculo();
            if (tablaMicrocurriculo != null) {
                tablaMicrocurriculo = em.getReference(tablaMicrocurriculo.getClass(), tablaMicrocurriculo.getTablaMicrocurriculoPK());
                encabezadoTabla.setTablaMicrocurriculo(tablaMicrocurriculo);
            }
            em.persist(encabezadoTabla);
            if (idEncabezado != null) {
                idEncabezado.getEncabezadoTablaList().add(encabezadoTabla);
                idEncabezado = em.merge(idEncabezado);
            }
            if (tablaMicrocurriculo != null) {
                tablaMicrocurriculo.getEncabezadoTablaList().add(encabezadoTabla);
                tablaMicrocurriculo = em.merge(tablaMicrocurriculo);
            }
            em.getTransaction().commit();
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
            EncabezadoTabla persistentEncabezadoTabla = em.find(EncabezadoTabla.class, encabezadoTabla.getId());
            Encabezado idEncabezadoOld = persistentEncabezadoTabla.getIdEncabezado();
            Encabezado idEncabezadoNew = encabezadoTabla.getIdEncabezado();
            TablaMicrocurriculo tablaMicrocurriculoOld = persistentEncabezadoTabla.getTablaMicrocurriculo();
            TablaMicrocurriculo tablaMicrocurriculoNew = encabezadoTabla.getTablaMicrocurriculo();
            if (idEncabezadoNew != null) {
                idEncabezadoNew = em.getReference(idEncabezadoNew.getClass(), idEncabezadoNew.getId());
                encabezadoTabla.setIdEncabezado(idEncabezadoNew);
            }
            if (tablaMicrocurriculoNew != null) {
                tablaMicrocurriculoNew = em.getReference(tablaMicrocurriculoNew.getClass(), tablaMicrocurriculoNew.getTablaMicrocurriculoPK());
                encabezadoTabla.setTablaMicrocurriculo(tablaMicrocurriculoNew);
            }
            encabezadoTabla = em.merge(encabezadoTabla);
            if (idEncabezadoOld != null && !idEncabezadoOld.equals(idEncabezadoNew)) {
                idEncabezadoOld.getEncabezadoTablaList().remove(encabezadoTabla);
                idEncabezadoOld = em.merge(idEncabezadoOld);
            }
            if (idEncabezadoNew != null && !idEncabezadoNew.equals(idEncabezadoOld)) {
                idEncabezadoNew.getEncabezadoTablaList().add(encabezadoTabla);
                idEncabezadoNew = em.merge(idEncabezadoNew);
            }
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
            Encabezado idEncabezado = encabezadoTabla.getIdEncabezado();
            if (idEncabezado != null) {
                idEncabezado.getEncabezadoTablaList().remove(encabezadoTabla);
                idEncabezado = em.merge(idEncabezado);
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
