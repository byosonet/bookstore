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
public class CiudadMunicipioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_ESTADO", nullable = false, insertable = false, updatable = false)
    private short idEstado;
    @Basic(optional = false)
    @Column(name = "ID_DELEGACION_MUNICIPIO", nullable = false, insertable = false, updatable = false)
    private int idDelegacionMunicipio;
    @Basic(optional = false)
    @Column(name = "ID_CUIDAD", nullable = false, insertable = false, updatable = false)
    private int idCiudad;

    public CiudadMunicipioPK() {
    }

    public CiudadMunicipioPK(short idEstado, int idDelegacionMunicipio, int idCiudad) {
        this.idEstado = idEstado;
        this.idDelegacionMunicipio = idDelegacionMunicipio;
        this.idCiudad = idCiudad;
    }

    public short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(short idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdDelegacionMunicipio() {
        return idDelegacionMunicipio;
    }

    public void setIdDelegacionMunicipio(int idDelegacionMunicipio) {
        this.idDelegacionMunicipio = idDelegacionMunicipio;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    @Override
    public int hashCode() {
        return idEstado + idDelegacionMunicipio + idCiudad;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CiudadMunicipioPK ? equals((CiudadMunicipioPK) object) : false;
    }

    public boolean equals(CiudadMunicipioPK other) {
        return this.idEstado == other.idEstado
                && this.idDelegacionMunicipio == other.idDelegacionMunicipio
                && this.idCiudad == other.idCiudad;
    }

    @Override
    public String toString() {
        return "CiudadMunicipioPK{" + "idEstado=" + idEstado + ", idDelegacionMunicipio=" + idDelegacionMunicipio + ", idCiudad=" + idCiudad + '}';
    }

}
