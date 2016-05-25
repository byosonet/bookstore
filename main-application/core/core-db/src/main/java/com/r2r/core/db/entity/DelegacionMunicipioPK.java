/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Alan Hdez
 */
@Embeddable
public class DelegacionMunicipioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_ESTADO", nullable = false)
    private short idEstado;
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private int id;

    public DelegacionMunicipioPK() {
    }

    public DelegacionMunicipioPK(short idEstado, int id) {
        this.idEstado = idEstado;
        this.id = id;
    }

    public short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(short idEstado) {
        this.idEstado = idEstado;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return idEstado + id;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof DelegacionMunicipioPK ? equals((DelegacionMunicipioPK) object) : false;
    }

    public boolean equals(DelegacionMunicipioPK other) {
        return this.idEstado == other.idEstado
                && this.id == other.id;
    }

    @Override
    public String toString() {
        return "DelegacionMunicipioPK[ idEstado=" + idEstado + ", id=" + id + " ]";
    }

}
