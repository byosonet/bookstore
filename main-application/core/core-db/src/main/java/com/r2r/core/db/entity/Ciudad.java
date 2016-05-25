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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "CIUDAD", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_CIUDAD_NOMBRE", columnNames = {"NOMBRE", "ID_ESTADO"})})
public class Ciudad extends CatalogAbstract<CiudadPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CiudadPK id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estado estado;

    @Override
    public CiudadPK getId() {
        return id;
    }

    @Override
    public void setId(CiudadPK id) {
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
