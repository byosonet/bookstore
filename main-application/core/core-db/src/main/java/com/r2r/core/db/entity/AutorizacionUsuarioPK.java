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
public class AutorizacionUsuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_AUTORIZACION", nullable = false)
    private short idAutorizacion;

    @Basic(optional = false)
    @Column(name = "ID_USUARIO", nullable = false)
    private int idUsuario;

    public AutorizacionUsuarioPK() {
    }

    public AutorizacionUsuarioPK(short idAutorizacion, int idUsuario) {
        this.idAutorizacion = idAutorizacion;
        this.idUsuario = idUsuario;
    }

    public short getIdAutorizacion() {
        return idAutorizacion;
    }

    public void setIdAutorizacion(short idAutorizacion) {
        this.idAutorizacion = idAutorizacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        return idAutorizacion + idUsuario;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof AutorizacionUsuarioPK ? equals((AutorizacionUsuarioPK) object) : false;
    }

    public boolean equals(AutorizacionUsuarioPK other) {
        return this.idAutorizacion == other.idAutorizacion
                && this.idUsuario == other.idUsuario;
    }

    @Override
    public String toString() {
        return "AutorizacionUsuarioPK[ idAutorizacion=" + idAutorizacion + ", idUsuario=" + idUsuario + " ]";
    }
}
