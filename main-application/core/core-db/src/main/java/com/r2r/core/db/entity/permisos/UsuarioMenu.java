/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.permisos;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.navegacion.Menu;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "USUARIO_MENU", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_USUARIO", "ID_MENU"})})
@XmlRootElement
public class UsuarioMenu extends EntityAbstract<UsuarioMenuPK> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UsuarioMenuPK id;

    @NotNull
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    @JoinColumn(name = "ID_MENU", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Menu menu;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS", nullable = false)
    @Enumerated
    private Status estatus;

    public UsuarioMenu() {
    }

    public UsuarioMenu(UsuarioMenuPK id, Status estatus) {
        this.id = id;
        this.estatus = estatus;
    }

    @Override
    public UsuarioMenuPK getId() {
        return id;
    }

    @Override
    public void setId(UsuarioMenuPK id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

}
