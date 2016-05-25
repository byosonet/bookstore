/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.common;

import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

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
@EntityListeners(UModifListener.class)
public abstract class EntityUModifAbstract<ID> extends EntityAbstract<ID> implements UModifInterface {

    @Embedded
    @NotNull
    protected UModif UModif;

    @Override
    public UModif getUModif() {
        return UModif;
    }

    @Override
    public void setUModif(UModif UModif) {
        this.UModif = UModif;
    }

}
