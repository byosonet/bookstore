/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.permisos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Alan Hdez
 */
@Embeddable
public class UsuarioOficinaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_OFICINA", nullable = false)
    private int idOficina;
    @Basic(optional = false)
    @Column(name = "ID_USUARIO", nullable = false)
    private int idUsuario;

    public UsuarioOficinaPK() {
    }

    public UsuarioOficinaPK(int idOficina, int idUsuario) {
        this.idOficina = idOficina;
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        return idOficina + idUsuario;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UsuarioOficinaPK ? equals((UsuarioOficinaPK) object) : false;
    }

    public boolean equals(UsuarioOficinaPK other) {
        return this.idOficina == other.idOficina
                && this.idUsuario == other.idUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioOficinaPK{" + "idOficina=" + idOficina + ", idUsuario=" + idUsuario + '}';
    }

    public int getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(int idOficina) {
        this.idOficina = idOficina;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
