/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity;

import com.r2r.core.db.entity.navegacion.Sistema;
import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.exception.DatabaseConstraintIndex;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "AUTORIZACION", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_AUTH_NAME", columnNames = {"NOMBRE"})})
@XmlRootElement
public class Autorizacion extends CatalogAbstract<Short> {

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_AUTH_NAME", DatabaseConstraintIndex.tripleCatalogName);
    }

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "NOMBRE", nullable = false)
    @Basic(optional = false)
    @NotNull
    private String nombre;

    @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Sistema sistema;

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Short getId() {
        return id;
    }

    @Override
    public void setId(Short id) {
        this.id = id;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

}
