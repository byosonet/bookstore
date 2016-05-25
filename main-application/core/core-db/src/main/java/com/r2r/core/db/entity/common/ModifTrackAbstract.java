/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.common;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alan Hdez
 * @param <ID>
 */
@MappedSuperclass
public abstract class ModifTrackAbstract<ID> extends EntityUModifAbstract<ID> {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS", nullable = false)
    @Enumerated
    protected Status estatus;

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

    @PrePersist
    public void enableStatus() {
        estatus = Status.ENABLED;
    }
}
