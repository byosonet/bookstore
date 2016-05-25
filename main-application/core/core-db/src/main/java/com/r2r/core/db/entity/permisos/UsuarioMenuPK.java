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
public class UsuarioMenuPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_MENU", nullable = false)
    private short idMenu;
    @Basic(optional = false)
    @Column(name = "ID_USUARIO", nullable = false)
    private int idUsuario;

    public UsuarioMenuPK() {
    }

    public UsuarioMenuPK(short idMenu, int idUsuario) {
        this.idMenu = idMenu;
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        return idMenu + idUsuario;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UsuarioMenuPK ? equals((UsuarioMenuPK) object) : false;
    }

    public boolean equals(UsuarioMenuPK other) {
        return this.idMenu == other.idMenu
                && this.idUsuario == other.idUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioMenuPK{" + "idMenu=" + idMenu + ", idUsuario=" + idUsuario + '}';
    }

    public short getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(short idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
