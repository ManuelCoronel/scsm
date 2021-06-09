/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.SeccionMicrocurriculo;
import dto.TablaMicrocurriculoInfo;
import java.util.ArrayList;
import java.util.List;
import dto.EncabezadoTabla;
import dto.TablaMicrocurriculo;
import dto.TablaMicrocurriculoPK;
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

    public void create(TablaMicrocurriculo tablaMicrocurriculo) throws PreexistingEntityException, Exception {
        if (tablaMicrocurriculo.getTablaMicrocurriculoPK() == null) {
            tablaMicrocurriculo.setTablaMicrocurriculoPK(new TablaMicrocurriculoPK());
        }
        if (tablaMicrocurriculo.getTablaMicrocurriculoInfoList() == null) {
            tablaMicrocurriculo.setTablaMicrocurriculoInfoList(new ArrayList<TablaMicrocurriculoInfo>());
        }
        if (tablaMicrocurriculo.getEncabezadoTablaList() == null) {
            tablaMicrocurriculo.setEncabezadoTablaList(new ArrayList<EncabezadoTabla>());
        }
        if (tablaMicrocurriculo.getEncabezadoTablaList1() == null) {
            tablaMicrocurriculo.setEncabezadoTablaList1(new ArrayList<EncabezadoTabla>());
        }
        tablaMicrocurriculo.getTablaMicrocurriculoPK().setSeccionMicrocurriculoId(tablaMicrocurriculo.getSeccionMicrocurriculo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeccionMicrocurriculo seccionMicrocurriculo = tablaMicrocurriculo.getSeccionMicrocurriculo();
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo = em.getReference(seccionMicrocurriculo.getClass(), seccionMicrocurriculo.getId());
                tablaMicrocurriculo.setSeccionMicrocurriculo(seccionMicrocurriculo);
            }
            List<TablaMicrocurriculoInfo> attachedTablaMicrocurriculoInfoList = new ArrayList<TablaMicrocurriculoInfo>();
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach : tablaMicrocurriculo.getTablaMicrocurriculoInfoList()) {
                tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach = em.getReference(tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach.getClass(), tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach.getTablaMicrocurriculoInfoPK());
                attachedTablaMicrocurriculoInfoList.add(tablaMicrocurriculoInfoListTablaMicrocurriculoInfoToAttach);
            }
            tablaMicrocurriculo.setTablaMicrocurriculoInfoList(attachedTablaMicrocurriculoInfoList);
            List<EncabezadoTabla> attachedEncabezadoTablaList = new ArrayList<EncabezadoTabla>();
            for (EncabezadoTabla encabezadoTablaListEncabezadoTablaToAttach : tablaMicrocurriculo.getEncabezadoTablaList()) {
                encabezadoTablaListEncabezadoTablaToAttach = em.getReference(encabezadoTablaListEncabezadoTablaToAttach.getClass(), encabezadoTablaListEncabezadoTablaToAttach.getId());
                attachedEncabezadoTablaList.add(encabezadoTablaListEncabezadoTablaToAttach);
            }
            tablaMicrocurriculo.setEncabezadoTablaList(attachedEncabezadoTablaList);
            List<EncabezadoTabla> attachedEncabezadoTablaList1 = new ArrayList<EncabezadoTabla>();
            for (EncabezadoTabla encabezadoTablaList1EncabezadoTablaToAttach : tablaMicrocurriculo.getEncabezadoTablaList1()) {
                encabezadoTablaList1EncabezadoTablaToAttach = em.getReference(encabezadoTablaList1EncabezadoTablaToAttach.getClass(), encabezadoTablaList1EncabezadoTablaToAttach.getId());
                attachedEncabezadoTablaList1.add(encabezadoTablaList1EncabezadoTablaToAttach);
            }
            tablaMicrocurriculo.setEncabezadoTablaList1(attachedEncabezadoTablaList1);
            em.persist(tablaMicrocurriculo);
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo.getTablaMicrocurriculoList().add(tablaMicrocurriculo);
                seccionMicrocurriculo = em.merge(seccionMicrocurriculo);
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
            for (EncabezadoTabla encabezadoTablaListEncabezadoTabla : tablaMicrocurriculo.getEncabezadoTablaList()) {
                TablaMicrocurriculo oldIdTablaOfEncabezadoTablaListEncabezadoTabla = encabezadoTablaListEncabezadoTabla.getIdTabla();
                encabezadoTablaListEncabezadoTabla.setIdTabla(tablaMicrocurriculo);
                encabezadoTablaListEncabezadoTabla = em.merge(encabezadoTablaListEncabezadoTabla);
                if (oldIdTablaOfEncabezadoTablaListEncabezadoTabla != null) {
                    oldIdTablaOfEncabezadoTablaListEncabezadoTabla.getEncabezadoTablaList().remove(encabezadoTablaListEncabezadoTabla);
                    oldIdTablaOfEncabezadoTablaListEncabezadoTabla = em.merge(oldIdTablaOfEncabezadoTablaListEncabezadoTabla);
                }
            }
            for (EncabezadoTabla encabezadoTablaList1EncabezadoTabla : tablaMicrocurriculo.getEncabezadoTablaList1()) {
                TablaMicrocurriculo oldIdSeccionOfEncabezadoTablaList1EncabezadoTabla = encabezadoTablaList1EncabezadoTabla.getIdSeccion();
                encabezadoTablaList1EncabezadoTabla.setIdSeccion(tablaMicrocurriculo);
                encabezadoTablaList1EncabezadoTabla = em.merge(encabezadoTablaList1EncabezadoTabla);
                if (oldIdSeccionOfEncabezadoTablaList1EncabezadoTabla != null) {
                    oldIdSeccionOfEncabezadoTablaList1EncabezadoTabla.getEncabezadoTablaList1().remove(encabezadoTablaList1EncabezadoTabla);
                    oldIdSeccionOfEncabezadoTablaList1EncabezadoTabla = em.merge(oldIdSeccionOfEncabezadoTablaList1EncabezadoTabla);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTablaMicrocurriculo(tablaMicrocurriculo.getTablaMicrocurriculoPK()) != null) {
                throw new PreexistingEntityException("TablaMicrocurriculo " + tablaMicrocurriculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TablaMicrocurriculo tablaMicrocurriculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        tablaMicrocurriculo.getTablaMicrocurriculoPK().setSeccionMicrocurriculoId(tablaMicrocurriculo.getSeccionMicrocurriculo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaMicrocurriculo persistentTablaMicrocurriculo = em.find(TablaMicrocurriculo.class, tablaMicrocurriculo.getTablaMicrocurriculoPK());
            SeccionMicrocurriculo seccionMicrocurriculoOld = persistentTablaMicrocurriculo.getSeccionMicrocurriculo();
            SeccionMicrocurriculo seccionMicrocurriculoNew = tablaMicrocurriculo.getSeccionMicrocurriculo();
            List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoListOld = persistentTablaMicrocurriculo.getTablaMicrocurriculoInfoList();
            List<TablaMicrocurriculoInfo> tablaMicrocurriculoInfoListNew = tablaMicrocurriculo.getTablaMicrocurriculoInfoList();
            List<EncabezadoTabla> encabezadoTablaListOld = persistentTablaMicrocurriculo.getEncabezadoTablaList();
            List<EncabezadoTabla> encabezadoTablaListNew = tablaMicrocurriculo.getEncabezadoTablaList();
            List<EncabezadoTabla> encabezadoTablaList1Old = persistentTablaMicrocurriculo.getEncabezadoTablaList1();
            List<EncabezadoTabla> encabezadoTablaList1New = tablaMicrocurriculo.getEncabezadoTablaList1();
            List<String> illegalOrphanMessages = null;
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListOldTablaMicrocurriculoInfo : tablaMicrocurriculoInfoListOld) {
                if (!tablaMicrocurriculoInfoListNew.contains(tablaMicrocurriculoInfoListOldTablaMicrocurriculoInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TablaMicrocurriculoInfo " + tablaMicrocurriculoInfoListOldTablaMicrocurriculoInfo + " since its tablaMicrocurriculo field is not nullable.");
                }
            }
            for (EncabezadoTabla encabezadoTablaListOldEncabezadoTabla : encabezadoTablaListOld) {
                if (!encabezadoTablaListNew.contains(encabezadoTablaListOldEncabezadoTabla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EncabezadoTabla " + encabezadoTablaListOldEncabezadoTabla + " since its idTabla field is not nullable.");
                }
            }
            for (EncabezadoTabla encabezadoTablaList1OldEncabezadoTabla : encabezadoTablaList1Old) {
                if (!encabezadoTablaList1New.contains(encabezadoTablaList1OldEncabezadoTabla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EncabezadoTabla " + encabezadoTablaList1OldEncabezadoTabla + " since its idSeccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (seccionMicrocurriculoNew != null) {
                seccionMicrocurriculoNew = em.getReference(seccionMicrocurriculoNew.getClass(), seccionMicrocurriculoNew.getId());
                tablaMicrocurriculo.setSeccionMicrocurriculo(seccionMicrocurriculoNew);
            }
            List<TablaMicrocurriculoInfo> attachedTablaMicrocurriculoInfoListNew = new ArrayList<TablaMicrocurriculoInfo>();
            for (TablaMicrocurriculoInfo tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach : tablaMicrocurriculoInfoListNew) {
                tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach = em.getReference(tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach.getClass(), tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach.getTablaMicrocurriculoInfoPK());
                attachedTablaMicrocurriculoInfoListNew.add(tablaMicrocurriculoInfoListNewTablaMicrocurriculoInfoToAttach);
            }
            tablaMicrocurriculoInfoListNew = attachedTablaMicrocurriculoInfoListNew;
            tablaMicrocurriculo.setTablaMicrocurriculoInfoList(tablaMicrocurriculoInfoListNew);
            List<EncabezadoTabla> attachedEncabezadoTablaListNew = new ArrayList<EncabezadoTabla>();
            for (EncabezadoTabla encabezadoTablaListNewEncabezadoTablaToAttach : encabezadoTablaListNew) {
                encabezadoTablaListNewEncabezadoTablaToAttach = em.getReference(encabezadoTablaListNewEncabezadoTablaToAttach.getClass(), encabezadoTablaListNewEncabezadoTablaToAttach.getId());
                attachedEncabezadoTablaListNew.add(encabezadoTablaListNewEncabezadoTablaToAttach);
            }
            encabezadoTablaListNew = attachedEncabezadoTablaListNew;
            tablaMicrocurriculo.setEncabezadoTablaList(encabezadoTablaListNew);
            List<EncabezadoTabla> attachedEncabezadoTablaList1New = new ArrayList<EncabezadoTabla>();
            for (EncabezadoTabla encabezadoTablaList1NewEncabezadoTablaToAttach : encabezadoTablaList1New) {
                encabezadoTablaList1NewEncabezadoTablaToAttach = em.getReference(encabezadoTablaList1NewEncabezadoTablaToAttach.getClass(), encabezadoTablaList1NewEncabezadoTablaToAttach.getId());
                attachedEncabezadoTablaList1New.add(encabezadoTablaList1NewEncabezadoTablaToAttach);
            }
            encabezadoTablaList1New = attachedEncabezadoTablaList1New;
            tablaMicrocurriculo.setEncabezadoTablaList1(encabezadoTablaList1New);
            tablaMicrocurriculo = em.merge(tablaMicrocurriculo);
            if (seccionMicrocurriculoOld != null && !seccionMicrocurriculoOld.equals(seccionMicrocurriculoNew)) {
                seccionMicrocurriculoOld.getTablaMicrocurriculoList().remove(tablaMicrocurriculo);
                seccionMicrocurriculoOld = em.merge(seccionMicrocurriculoOld);
            }
            if (seccionMicrocurriculoNew != null && !seccionMicrocurriculoNew.equals(seccionMicrocurriculoOld)) {
                seccionMicrocurriculoNew.getTablaMicrocurriculoList().add(tablaMicrocurriculo);
                seccionMicrocurriculoNew = em.merge(seccionMicrocurriculoNew);
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
            for (EncabezadoTabla encabezadoTablaListNewEncabezadoTabla : encabezadoTablaListNew) {
                if (!encabezadoTablaListOld.contains(encabezadoTablaListNewEncabezadoTabla)) {
                    TablaMicrocurriculo oldIdTablaOfEncabezadoTablaListNewEncabezadoTabla = encabezadoTablaListNewEncabezadoTabla.getIdTabla();
                    encabezadoTablaListNewEncabezadoTabla.setIdTabla(tablaMicrocurriculo);
                    encabezadoTablaListNewEncabezadoTabla = em.merge(encabezadoTablaListNewEncabezadoTabla);
                    if (oldIdTablaOfEncabezadoTablaListNewEncabezadoTabla != null && !oldIdTablaOfEncabezadoTablaListNewEncabezadoTabla.equals(tablaMicrocurriculo)) {
                        oldIdTablaOfEncabezadoTablaListNewEncabezadoTabla.getEncabezadoTablaList().remove(encabezadoTablaListNewEncabezadoTabla);
                        oldIdTablaOfEncabezadoTablaListNewEncabezadoTabla = em.merge(oldIdTablaOfEncabezadoTablaListNewEncabezadoTabla);
                    }
                }
            }
            for (EncabezadoTabla encabezadoTablaList1NewEncabezadoTabla : encabezadoTablaList1New) {
                if (!encabezadoTablaList1Old.contains(encabezadoTablaList1NewEncabezadoTabla)) {
                    TablaMicrocurriculo oldIdSeccionOfEncabezadoTablaList1NewEncabezadoTabla = encabezadoTablaList1NewEncabezadoTabla.getIdSeccion();
                    encabezadoTablaList1NewEncabezadoTabla.setIdSeccion(tablaMicrocurriculo);
                    encabezadoTablaList1NewEncabezadoTabla = em.merge(encabezadoTablaList1NewEncabezadoTabla);
                    if (oldIdSeccionOfEncabezadoTablaList1NewEncabezadoTabla != null && !oldIdSeccionOfEncabezadoTablaList1NewEncabezadoTabla.equals(tablaMicrocurriculo)) {
                        oldIdSeccionOfEncabezadoTablaList1NewEncabezadoTabla.getEncabezadoTablaList1().remove(encabezadoTablaList1NewEncabezadoTabla);
                        oldIdSeccionOfEncabezadoTablaList1NewEncabezadoTabla = em.merge(oldIdSeccionOfEncabezadoTablaList1NewEncabezadoTabla);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TablaMicrocurriculoPK id = tablaMicrocurriculo.getTablaMicrocurriculoPK();
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

    public void destroy(TablaMicrocurriculoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaMicrocurriculo tablaMicrocurriculo;
            try {
                tablaMicrocurriculo = em.getReference(TablaMicrocurriculo.class, id);
                tablaMicrocurriculo.getTablaMicrocurriculoPK();
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
            List<EncabezadoTabla> encabezadoTablaListOrphanCheck = tablaMicrocurriculo.getEncabezadoTablaList();
            for (EncabezadoTabla encabezadoTablaListOrphanCheckEncabezadoTabla : encabezadoTablaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TablaMicrocurriculo (" + tablaMicrocurriculo + ") cannot be destroyed since the EncabezadoTabla " + encabezadoTablaListOrphanCheckEncabezadoTabla + " in its encabezadoTablaList field has a non-nullable idTabla field.");
            }
            List<EncabezadoTabla> encabezadoTablaList1OrphanCheck = tablaMicrocurriculo.getEncabezadoTablaList1();
            for (EncabezadoTabla encabezadoTablaList1OrphanCheckEncabezadoTabla : encabezadoTablaList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TablaMicrocurriculo (" + tablaMicrocurriculo + ") cannot be destroyed since the EncabezadoTabla " + encabezadoTablaList1OrphanCheckEncabezadoTabla + " in its encabezadoTablaList1 field has a non-nullable idSeccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SeccionMicrocurriculo seccionMicrocurriculo = tablaMicrocurriculo.getSeccionMicrocurriculo();
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo.getTablaMicrocurriculoList().remove(tablaMicrocurriculo);
                seccionMicrocurriculo = em.merge(seccionMicrocurriculo);
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

    public TablaMicrocurriculo findTablaMicrocurriculo(TablaMicrocurriculoPK id) {
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
