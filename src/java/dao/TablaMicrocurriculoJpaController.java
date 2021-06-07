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
import dto.TablaMicrocurriculo;
import dto.TablaMicrocurriculoInfo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuel
 */
public class TablaMicrocurriculoJpaController implements Serializable {

    public TablaMicrocurriculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TablaMicrocurriculo tablaMicrocurriculo) {
        if (tablaMicrocurriculo.getTablaMicrocurriculoInfoList() == null) {
            tablaMicrocurriculo.setTablaMicrocurriculoInfoList(new ArrayList<TablaMicrocurriculoInfo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeccionMicrocurriculo seccionMicrocurriculoId = tablaMicrocurriculo.getSeccionMicrocurriculoId();
            if (seccionMicrocurriculoId != null) {
                seccionMicrocurriculoId = em.getReference(seccionMicrocurriculoId.getClass(), seccionMicrocurriculoId.getId());
                tablaMicrocurriculo.setSeccionMicrocurriculoId(seccionMicrocurriculoId);
            }
            List<TablaMicrocurriculoInfo> attachedTablaMicrocurriculoInfoList = new ArrayList<TablaMicrocurriculoInfo>();
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach : tablaMicrocurriculo.getTablaMicrocurriculoInfoList()) {
                tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach = em.getReference(tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach.getClass(), tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach.getTablaMicrocurriculoInfoPK());
                attachedTablaMicrocurriculoInfoList.add(tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach);
            }
            tablaMicrocurriculo.setTablaMicrocurriculoInfoList(attachedTablaMicrocurriculoInfoList);
            em.persist(tablaMicrocurriculo);
            if (seccionMicrocurriculoId != null) {
                seccionMicrocurriculoId.getTablaMicrocurriculoList().add(tablaMicrocurriculo);
                seccionMicrocurriculoId = em.merge(seccionMicrocurriculoId);
            }
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListTablaMicrocurriculoInfo : tablaMicrocurriculo.getTablaMicrocurriculoInfoList()) {
                TablaMicrocurriculo oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListTablaMicrocurriculoInfo = tablaMicrocurriculoInfoListTablaMicrocurriculoInfo.getTablaMicrocurriculo();
                tablaMicrocurriculoInfoListTablaMicrocurriculoInfo.setTablaMicrocurriculo(tablaMicrocurriculo);
                tablaMicrocurriculoInfoListTablaMicrocurriculoInfo = em.merge(tablaMicrocurriculoInfoListTablaMicrocurriculoInfo);
                if (oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListTablaMicrocurriculoInfo != null) {
                    oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListTablaMicrocurriculoInfo.getTablaMicrocurriculoInfoList().remove(tablaMicrocurriculoInfoListTablaMicrocurriculoInfo);
                    oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListTablaMicrocurriculoInfo = em.merge(oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListTablaMicrocurriculoInfo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TablaMicrocurriculo tablaMicrocurriculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaMicrocurriculo persistentTablaMicrocurriculo = em.find(TablaMicrocurriculo.class, tablaMicrocurriculo.getId());
            SeccionMicrocurriculo seccionMicrocurriculoIdOld = persistentTablaMicrocurriculo.getSeccionMicrocurriculoId();
            SeccionMicrocurriculo seccionMicrocurriculoIdNew = tablaMicrocurriculo.getSeccionMicrocurriculoId();
            List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoListOld = persistentTablaMicrocurriculo.getTablaMicrocurriculoInfoList();
            List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoListNew = tablaMicrocurriculo.getTablaMicrocurriculoInfoList();
            List<String> illegalOrphanMessages = null;
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListOldTablaMicrocurriculoInfo : tablaMicrocurriculoInfoListOld) {
                if (!tablaMicrocurriculoInfoListNew.contains(tablaMicrocurriculoInfoListOldTablaMicrocurriculoInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TablaMicrocurriculoInfo " + tablaMicrocurriculoInfoListOldTablaMicrocurriculoInfo + " since its tablaMicrocurriculo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (seccionMicrocurriculoIdNew != null) {
                seccionMicrocurriculoIdNew = em.getReference(seccionMicrocurriculoIdNew.getClass(), seccionMicrocurriculoIdNew.getId());
                tablaMicrocurriculo.setSeccionMicrocurriculoId(seccionMicrocurriculoIdNew);
            }
            List<TablaMicrocurriculoInfo> attachedTablaMicrocurriculoInfoListNew = new ArrayList<TablaMicrocurriculoInfo>();
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach : tablaMicrocurriculoInfoListNew) {
                tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach = em.getReference(tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach.getClass(), tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach.getTablaMicrocurriculoInfoPK());
                attachedTablaMicrocurriculoInfoListNew.add(tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach);
            }
            tablaMicrocurriculoInfoListNew = attachedTablaMicrocurriculoInfoListNew;
            tablaMicrocurriculo.setTablaMicrocurriculoInfoList(tablaMicrocurriculoInfoListNew);
            tablaMicrocurriculo = em.merge(tablaMicrocurriculo);
            if (seccionMicrocurriculoIdOld != null && !seccionMicrocurriculoIdOld.equals(seccionMicrocurriculoIdNew)) {
                seccionMicrocurriculoIdOld.getTablaMicrocurriculoList().remove(tablaMicrocurriculo);
                seccionMicrocurriculoIdOld = em.merge(seccionMicrocurriculoIdOld);
            }
            if (seccionMicrocurriculoIdNew != null && !seccionMicrocurriculoIdNew.equals(seccionMicrocurriculoIdOld)) {
                seccionMicrocurriculoIdNew.getTablaMicrocurriculoList().add(tablaMicrocurriculo);
                seccionMicrocurriculoIdNew = em.merge(seccionMicrocurriculoIdNew);
            }
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo : tablaMicrocurriculoInfoListNew) {
                if (!tablaMicrocurriculoInfoListOld.contains(tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo)) {
                    TablaMicrocurriculo oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo = tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo.getTablaMicrocurriculo();
                    tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo.setTablaMicrocurriculo(tablaMicrocurriculo);
                    tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo = em.merge(tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo);
                    if (oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo != null && !oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo.equals(tablaMicrocurriculo)) {
                        oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo.getTablaMicrocurriculoInfoList().remove(tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo);
                        oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo = em.merge(oldTablaMicrocurriculoOfTablaMicrocurriculoInfoListNewTablaMicrocurriculoInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tablaMicrocurriculo.getId();
                if (findTablaMicrocurriculo(id) == null) {
                    throw new NonexistentEntityException("The tablaMicrocurriculo with id " + id + " no longer exists.");
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
            TablaMicrocurriculo tablaMicrocurriculo;
            try {
                tablaMicrocurriculo = em.getReference(TablaMicrocurriculo.class, id);
                tablaMicrocurriculo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tablaMicrocurriculo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoListOrphanCheck = tablaMicrocurriculo.getTablaMicrocurriculoInfoList();
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListOrphanCheckTablaMicrocurriculoInfo : tablaMicrocurriculoInfoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TablaMicrocurriculo (" + tablaMicrocurriculo + ") cannot be destroyed since the TablaMicrocurriculoInfo " + tablaMicrocurriculoInfoListOrphanCheckTablaMicrocurriculoInfo + " in its tablaMicrocurriculoInfoList field has a non-nullable tablaMicrocurriculo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SeccionMicrocurriculo seccionMicrocurriculoId = tablaMicrocurriculo.getSeccionMicrocurriculoId();
            if (seccionMicrocurriculoId != null) {
                seccionMicrocurriculoId.getTablaMicrocurriculoList().remove(tablaMicrocurriculo);
                seccionMicrocurriculoId = em.merge(seccionMicrocurriculoId);
            }
            em.remove(tablaMicrocurriculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TablaMicrocurriculo> findTablaMicrocurriculoEntities() {
        return findTablaMicrocurriculoEntities(true, -1, -1);
    }

    public List<TablaMicrocurriculo> findTablaMicrocurriculoEntities(int maxResults, int firstResult) {
        return findTablaMicrocurriculoEntities(false, maxResults, firstResult);
    }

    private List<TablaMicrocurriculo> findTablaMicrocurriculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TablaMicrocurriculo.class));
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

    public TablaMicrocurriculo findTablaMicrocurriculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TablaMicrocurriculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTablaMicrocurriculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TablaMicrocurriculo> rt = cq.from(TablaMicrocurriculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
