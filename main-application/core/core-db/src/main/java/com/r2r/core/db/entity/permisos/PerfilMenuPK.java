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
public class PerfilMenuPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_PERFIL", nullable = false, updatable = false, insertable = false)
    private int idPerfil;

    @Basic(optional = false)
    @Column(name = "ID_MENU", nullable = false, updatable = false, insertable = false)
    private short idMenu;

    public PerfilMenuPK() {
    }

    public PerfilMenuPK(int idPerfil, short idMenu) {
        this.idMenu = idMenu;
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        return idMenu + idPerfil;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PerfilMenuPK ? equals((PerfilMenuPK) object) : false;
    }

    public boolean equals(PerfilMenuPK other) {
        return this.idMenu == other.idMenu
                && this.idPerfil == other.idPerfil;
    }

    @Override
    public String toString() {
        return "PerfilMenuPK{" + "idMenu=" + idMenu + ", idPerfil=" + idPerfil + '}';
    }

    public short getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(short idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

}
