/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.exception;

import com.r2r.core.db.entity.common.EntityAbstract;

/**
 * Interface to deal with the wrong value.
 *
 * @author Alan Hdez
 * @param <E> The entity who caused the {@link DatabaseConstraintException}
 */
public interface InterfaceConstraintViolated<E extends EntityAbstract> {

    /**
     * @param entity The entity who caused the constraint violation
     * @return The value of the field causing the exception
     */
    public Object[] getValues(E entity);

    public String getLabel();
}
