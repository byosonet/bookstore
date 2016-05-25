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
public class PerfilOficinaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_OFICINA", nullable = false)
    private int idOficina;
    @Basic(optional = false)
    @Column(name = "ID_PERFIL", nullable = false)
    private int idPerfil;

    public PerfilOficinaPK() {
    }

    public PerfilOficinaPK(int idOficina, int idUsuario) {
        this.idOficina = idOficina;
        this.idPerfil = idUsuario;
    }

    @Override
    public int hashCode() {
        return idOficina + idPerfil;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PerfilOficinaPK ? equals((PerfilOficinaPK) object) : false;
    }

    public boolean equals(PerfilOficinaPK other) {
        return this.idOficina == other.idOficina
                && this.idPerfil == other.idPerfil;
    }

    @Override
    public String toString() {
        return "PerfilOficinaPK{" + "idOficina=" + idOficina + ", idPerfil=" + idPerfil + '}';
    }

    public int getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(int idOficina) {
        this.idOficina = idOficina;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

}
