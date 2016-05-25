/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.navegacion;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "SUB_MENU", schema = DbConstant.SCHEMA)
@XmlRootElement
public class SubMenu extends EntityAbstract<Short> {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, insertable = false, updatable = false)
    private Short id;

    @NotNull
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Menu menu;

    @NotNull
    @JoinColumn(name = "ID_MENU_PADRE", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Menu menuPadre;

    public SubMenu() {
    }

    public SubMenu(Menu menuPadre) {
        this.menuPadre = menuPadre;
    }

    public SubMenu(Menu menu, Menu menuPadre) {
        this.menu = menu;
        this.menuPadre = menuPadre;
    }

    @Override
    public Short getId() {
        return id;
    }

    @Override
    public void setId(Short id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(Menu menuPadre) {
        this.menuPadre = menuPadre;
    }

}
