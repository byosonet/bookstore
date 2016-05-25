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
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.Visible;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "MENU", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NOMBRE"})})
@XmlRootElement
public class Menu extends EntityAbstract<Short> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;
    @Basic
    @Size(max = 50)
    @Column(name = "ICON", length = 50)
    private String icon;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS", nullable = false)
    @Enumerated
    private Status estatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VISIBLE", nullable = false)
    @Enumerated
    private Visible visible;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short orden;

    @NotNull
    @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sistema sistema;

    @OneToMany(mappedBy = "menuPadre", fetch = FetchType.LAZY)
    private List<SubMenu> menusHijos;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderColumn
    private List<MenuUrl> urls;

    @OneToOne(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SubMenu menuPadre;

    public Menu() {
    }

    public Menu(Short id) {
        this.id = id;
    }

    public Menu(Menu menu) {
        this.id = menu.getId();
        this.nombre = menu.getNombre();
        this.estatus = menu.getEstatus();
        this.orden = menu.getOrden();
        this.menusHijos = menu.getMenusHijos();
    }

    public Menu(Short id, String nombre, Status estatus, short orden) {
        this.id = id;
        this.nombre = nombre;
        this.estatus = estatus;
        this.orden = orden;
    }

    @Override
    public Short getId() {
        return id;
    }

    @Override
    public void setId(Short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

    public short getOrden() {
        return orden;
    }

    public void setOrden(short orden) {
        this.orden = orden;
    }

    public Visible getVisible() {
        return visible;
    }

    public void setVisible(Visible visible) {
        this.visible = visible;
    }

    @XmlTransient
    public List<SubMenu> getMenusHijos() {
        return menusHijos;
    }

    public void setMenusHijos(List<SubMenu> menusHijos) {
        this.menusHijos = menusHijos;
    }

    public SubMenu getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(SubMenu menuPadre) {
        this.menuPadre = menuPadre;
    }

    public List<MenuUrl> getUrls() {
        return urls;
    }

    public void setUrls(List<MenuUrl> urls) {
        this.urls = urls;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public String getUrl() {
        if (this.urls != null && this.urls.iterator().hasNext()) {
            return this.urls.iterator().next().getUrl();
        }
        return null;
    }

    /**
     * Enable the entity before is saved.
     */
    @PrePersist
    protected void enableStatus() {
        estatus = Status.ENABLED;
    }
}
