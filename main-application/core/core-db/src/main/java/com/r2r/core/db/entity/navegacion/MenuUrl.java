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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "MENU_URL", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID", "URL"})})
@XmlRootElement
public class MenuUrl extends EntityAbstract<Short> {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @NotNull
    @JoinColumn(name = "ID_MENU", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Menu menu;

    @Basic(optional = false)
    @NotNull
    @Size(max = 150)
    @Column(name = "URL", nullable = false, length = 150)
    private String url;

    public MenuUrl() {
    }

    public MenuUrl(Menu menu, String url) {
        this.menu = menu;
        this.url = url;
    }

    @Override
    public Short getId() {
        return this.id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
