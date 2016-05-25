/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.entity.common.UModif;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PAIS", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_PAIS_NOMBRE", columnNames = {"NOMBRE"})})
@XmlRootElement
public class Pais extends CatalogAbstract<Short> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;
    @Size(max = 2)
    @Column(length = 2)
    private String abreviatura2;
    @Size(max = 3)
    @Column(length = 3)
    private String abreviatura3;

    public Pais() {
    }

    public Pais(Short id, String nombre, String abreviatura2, String abreviatura3, UModif UModif) {
        this.id = id;
        this.nombre = nombre;
        this.abreviatura2 = abreviatura2;
        this.abreviatura3 = abreviatura3;
        this.UModif = UModif;
    }

    public Pais(Short id) {
        this.id = id;
    }

    @Override
    public Short getId() {
        return id;
    }

    @Override
    public void setId(Short id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura2() {
        return abreviatura2;
    }

    public void setAbreviatura2(String abreviatura2) {
        this.abreviatura2 = abreviatura2;
    }

    public String getAbreviatura3() {
        return abreviatura3;
    }

    public void setAbreviatura3(String abreviatura3) {
        this.abreviatura3 = abreviatura3;
    }
}
