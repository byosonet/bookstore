/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.common;

import com.r2r.core.db.SystemConstant;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.CatalogVersionAbstract;
import com.r2r.core.db.entity.common.CatalogVersionAbstract_;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.entity.common.EntityUModifAbstract;
import com.r2r.core.db.entity.common.ModifTrackAbstract;
import com.r2r.core.db.entity.common.ModifTrackAbstract_;
import com.r2r.core.db.entity.common.ModifTrackVersionableAbstract;
import com.r2r.core.db.entity.common.ModifTrackVersionableAbstract_;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.exception.DatabaseConstraintException;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.db.exception.JpaConstraintException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Alan Hdez
 */
public class EntityDaoEjb implements Serializable {

    private static final String SQL_SERVER_EXCEPTION_NAME = "com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException";

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    protected EntityManager entityManager;

    public void saveNewEntity(EntityAbstract entity) throws DatabaseException {
        try {
            entityManager.persist(entity);
            entityManager.flush();
        } catch (PersistenceException pe) {
            if (pe.getCause() != null && pe.getCause() instanceof org.eclipse.persistence.exceptions.DatabaseException) {
                org.eclipse.persistence.exceptions.DatabaseException eclipseException = (org.eclipse.persistence.exceptions.DatabaseException) pe.getCause();
                //<editor-fold desc="En caso de ser mysql u oracle">
//                if (eclipseException.getInternalException() != null && eclipseException.getInternalException() instanceof SQLIntegrityConstraintViolationException) {
//                    SQLIntegrityConstraintViolationException constraintException = (SQLIntegrityConstraintViolationException) eclipseException.getInternalException();
//                    throw new DatabaseConstraintException(constraintException, entity);
//                }
                //</editor-fold>
                //<editor-fold desc="En caso de ser sqlserver">
                throw new DatabaseConstraintException(eclipseException, entity);
                //</editor-fold>
            }
            throw new DatabaseException(pe);
        } catch (ConstraintViolationException cve) {
            throw new JpaConstraintException(cve);
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    public void editEntity(EntityAbstract entity) throws DatabaseException {
        try {
            entityManager.merge(entity);
            entityManager.flush();
        } catch (PersistenceException pe) {
            if (pe.getCause() != null && pe.getCause() instanceof org.eclipse.persistence.exceptions.DatabaseException) {
                org.eclipse.persistence.exceptions.DatabaseException eclipseException = (org.eclipse.persistence.exceptions.DatabaseException) pe.getCause();
                //<editor-fold desc="En caso de ser mysql u oracle">
//                if (eclipseException.getInternalException() != null && eclipseException.getInternalException() instanceof SQLIntegrityConstraintViolationException) {
//                    SQLIntegrityConstraintViolationException constraintException = (SQLIntegrityConstraintViolationException) eclipseException.getInternalException();
//                    throw new DatabaseConstraintException(constraintException, entity);
//                }
                //</editor-fold>
                //<editor-fold desc="En caso de ser sqlserver">
                if (eclipseException.getInternalException() != null && eclipseException.getInternalException().getClass().getName().equals(SQL_SERVER_EXCEPTION_NAME)) {
                    throw new DatabaseConstraintException(eclipseException, entity);
                }
                //</editor-fold>
            }
            throw new DatabaseException(pe);
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    public void saveNewEntity(EntityUModifAbstract entity, Usuario user) throws DatabaseException {
        entity.setUModif(new UModif(user));
        saveNewEntity(entity);
    }

    public void editEntity(EntityUModifAbstract entity, Usuario user) throws DatabaseException {
        entity.setUModif(new UModif(user));
        editEntity(entity);
    }

    public <E extends CatalogVersionAbstract> void saveNewVersionEntity(E entity, Usuario user) throws DatabaseException {
        entity.setVersion(getVersion(entity.getClass()) + 1);
        saveNewEntity(entity, user);
    }

    public <E extends CatalogVersionAbstract> void saveNewVersionEntity(E entity) throws DatabaseException {
        entity.setVersion(getVersion(entity.getClass()) + 1);
        saveNewEntity(entity);
    }

    public <E extends CatalogVersionAbstract> void editVersionEntity(E entity) throws DatabaseException {
        entity.setVersion(getVersion(entity.getClass()) + 1);
        editEntity(entity);
    }

    public <E extends CatalogVersionAbstract> void editVersionEntity(E entity, Usuario user) throws DatabaseException {
        entity.setVersion(getVersion(entity.getClass()) + 1);
        editEntity(entity, user);
    }

    public <E extends ModifTrackVersionableAbstract> void saveNewVersionEntity(E entity, Usuario user) throws DatabaseException {
        entity.setVersion(getVersionTrack(entity.getClass()) + 1);
        saveNewEntity(entity, user);
    }

    public <E extends ModifTrackVersionableAbstract> void editVersionEntity(E entity, Usuario user) throws DatabaseException {
        entity.setVersion(getVersionTrack(entity.getClass()) + 1);
        editEntity(entity, user);
    }

    public <ID, E extends EntityAbstract<ID>> E getForEdit(Class<E> entityClass, ID id) {
        try {
            return entityManager.createNamedQuery(entityClass.getSimpleName() + ".getForEdit", entityClass)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public <ID, E extends EntityAbstract<ID>> E getForEditSimple(Class<E> entityClass, ID id) {
        try {
            return entityManager.getReference(entityClass, id);
        } catch (NoResultException nre) {
            return null;
        }
    }

    public <C extends EntityAbstract> List<C> getListEntity(Class<C> entityClass) {
        return entityManager.createNamedQuery(entityClass.getSimpleName() + ".findAll", entityClass)
                .getResultList();
    }

    public <C extends ModifTrackAbstract> List<C> getListModifTrackAbstract(Class<C> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<C> cq = cb.createQuery(entityClass);
        Root<? extends ModifTrackAbstract> r = cq.from(entityClass);
        cq.where(cb.equal(r.get(ModifTrackAbstract_.estatus), Status.ENABLED));
        return entityManager.createQuery(cq).getResultList();
    }

    public <C extends EntityAbstract> List<C> getListEntityAbstract(Class<C> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<C> cq = cb.createQuery(entityClass);
        Root<? extends EntityAbstract> r = cq.from(entityClass);
        return entityManager.createQuery(cq).getResultList();
    }

    public <E extends CatalogVersionAbstract> Long getVersion(Class<E> entityClass) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<? extends CatalogVersionAbstract> cq = cb.createQuery(entityClass);
            Root<? extends CatalogVersionAbstract> r = cq.from(entityClass);
            cq.orderBy(cb.desc(r.get(CatalogVersionAbstract_.version)));
            return entityManager.createQuery(cq).setMaxResults(1).getSingleResult().getVersion();

        } catch (NoResultException nre) {
            return 0L;
        }
    }

    public <E extends ModifTrackVersionableAbstract> Long getVersionTrack(Class<E> entityClass) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<? extends ModifTrackVersionableAbstract> cq = cb.createQuery(entityClass);
            Root<? extends ModifTrackVersionableAbstract> r = cq.from(entityClass);
            cq.orderBy(cb.desc(r.get(ModifTrackVersionableAbstract_.version)));
            return entityManager.createQuery(cq).setMaxResults(1).getSingleResult().getVersion();

        } catch (NoResultException nre) {
            return 0L;
        }
    }

    public <ID, C extends ModifTrackAbstract<ID>> void changeStatusModifTrack(Class<C> entityClass, ID id, Status status) {
        C catalog = entityManager.getReference(entityClass, id);
        catalog.setEstatus(status);
        entityManager.persist(catalog);
    }

    public <ID, C extends ModifTrackAbstract<ID>> void changeStatusModifTrack(Class<C> entityClass, ID id, Usuario user, Status status) {
        C catalog = entityManager.getReference(entityClass, id);
        catalog.setEstatus(status);
        catalog.setUModif(new UModif(user));
        entityManager.persist(catalog);
    }

    public <ID, C extends ModifTrackVersionableAbstract<ID>> void changeStatusModifTrackVersion(Class<C> entityClass, ID id, Usuario user, Status status) {
        C catalog = entityManager.getReference(entityClass, id);
        catalog.setEstatus(status);
        catalog.setUModif(new UModif(user));
        catalog.setVersion(getVersionTrack(entityClass) + 1);
        entityManager.persist(catalog);
    }

    public <ID, C extends CatalogVersionAbstract<ID>> void changeStatusCatalogVersion(Class<C> entityClass, ID id, Usuario user, Status status) {
        C catalog = entityManager.getReference(entityClass, id);
        catalog.setEstatus(status);
        catalog.setUModif(new UModif(user));
        catalog.setVersion(getVersion(entityClass) + 1);
        entityManager.persist(catalog);
    }
}
