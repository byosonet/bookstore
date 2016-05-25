/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.permisos;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.entity.navegacion.Menu;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "PERFIL_MENU", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_PERFIL", "ID_MENU"})})
@XmlRootElement
public class PerfilMenu extends EntityAbstract<PerfilMenuPK> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected PerfilMenuPK id;

    @NotNull
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Perfil perfil;

    @JoinColumn(name = "ID_MENU", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Menu menu;

    public PerfilMenu() {
    }

    public PerfilMenu(PerfilMenuPK id) {
        this.id = id;
    }

    public PerfilMenu(Perfil perfil, Menu menu) {
        this.perfil = perfil;
        this.menu = menu;
    }

    @Override
    public PerfilMenuPK getId() {
        return id;
    }

    @Override
    public void setId(PerfilMenuPK id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}
