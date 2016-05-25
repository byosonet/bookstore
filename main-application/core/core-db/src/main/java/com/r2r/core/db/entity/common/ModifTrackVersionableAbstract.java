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
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alan Hdez
 * @param <ID>
 */
@MappedSuperclass
public abstract class ModifTrackVersionableAbstract<ID> extends ModifTrackAbstract<ID> {

    @Column(name = "VERSION", nullable = false)
    @Basic(optional = false)
    @NotNull
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
