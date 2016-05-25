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
public class UsuarioPerfilPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_PERFIL", nullable = false)
    private int idPerfil;
    @Basic(optional = false)
    @Column(name = "ID_USUARIO", nullable = false)
    private int idUsuario;

    public UsuarioPerfilPK() {
    }

    public UsuarioPerfilPK(int idPerfil, int idUsuario) {
        this.idPerfil = idPerfil;
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        return idPerfil + idUsuario;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UsuarioPerfilPK ? equals((UsuarioPerfilPK) object) : false;
    }

    public boolean equals(UsuarioPerfilPK other) {
        return this.idPerfil == other.idPerfil
                && this.idUsuario == other.idUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioPerfilPK{" + "idPerfil=" + idPerfil + ", idUsuario=" + idUsuario + '}';
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
