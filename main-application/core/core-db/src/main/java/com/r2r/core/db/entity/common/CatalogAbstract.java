/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.common;

import javax.persistence.MappedSuperclass;

/**
 * Catalog base class.
 * <p>
 * Like {@link EntityAbstract}, it normalize some conventions for the basic
 * catalogs and add some standard fields and operations, like adding the
 * {@link UModifListener} or changing the status of a new catalog to
 * {@link Status#ENABLED}</p>
 *
 * @author Alan Hdez
 * @param <ID> The class of the {@code Id} of the entity
 */
@MappedSuperclass
public abstract class CatalogAbstract<ID> extends ModifTrackAbstract<ID> {

    public abstract String getNombre();

    public abstract void setNombre(String nombre);
}
