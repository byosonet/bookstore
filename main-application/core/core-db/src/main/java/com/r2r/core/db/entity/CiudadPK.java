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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alan Hdez
 */
@Embeddable
public class CiudadPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO", nullable = false)
    private short idEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private short id;

    public CiudadPK() {
    }

    public CiudadPK(short idEstado, short id) {
        this.idEstado = idEstado;
        this.id = id;
    }

    public short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(short idEstado) {
        this.idEstado = idEstado;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return idEstado + id;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CiudadPK ? equals((CiudadPK) object) : false;
    }

    public boolean equals(CiudadPK other) {
        return this.idEstado == other.idEstado && this.id == other.id;
    }

    @Override
    public String toString() {
        return "CiudadPK[ idEstado=" + idEstado + ", id=" + id + " ]";
    }

}
