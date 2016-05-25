/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.common;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import javax.persistence.TypedQuery;

/**
 * Base entity class.
 * <p>
 * It normalize the {@code entity classes}, helping to create
 * {@link TypedQuery type safe queries}.</p>
 *
 * @author Alan Hdez
 * @param <ID> The class of the {@code Id} of the entity
 */
@MappedSuperclass
public abstract class EntityAbstract<ID> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @return The {@code Id} of the entity
     */
    public abstract ID getId();

    /**
     * @param id {@code Id} of the entity
     */
    public abstract void setId(ID id);

    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : getId().hashCode();
    }

    /**
     * Checks if an entity is equal to other.
     * <p>
     * To check the if the objects are equals, the method try comparing the id's
     * of the object, so both objects must have the id other than null.</p>
     *
     * @param object The object to make the comparison
     * @return {@code true} if both id's are the same, {@code false} if the id's
     * are not equal, or if some id is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object object) {
        if (!(object instanceof EntityAbstract<?>) || !getClass().isInstance(object)) {
            return false;
        }
        EntityAbstract<ID> other = (EntityAbstract<ID>) object;
        return getId() != null && other.getId() != null && getId().equals(other.getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + "]";
    }
}
