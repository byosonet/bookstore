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
import javax.persistence.OneToOne;
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
@Table(name = "DELEGACION_MUNICIPIO", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_DELEG_MUN_NOMBRE", columnNames = {"NOMBRE", "ID_ESTADO"})})
@XmlRootElement
public class DelegacionMunicipio extends CatalogAbstract<DelegacionMunicipioPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DelegacionMunicipioPK id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(max = 5)
    @Column(name = "ABREVIATURA", nullable = false, length = 5)
    private String abreviatura;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estado estado;
    @OneToOne(mappedBy = "delegacionMunicipio", fetch = FetchType.LAZY)
    private CiudadMunicipio ciudadMunicipio;

    public DelegacionMunicipio() {
    }

    public DelegacionMunicipio(DelegacionMunicipioPK id) {
        this.id = id;
    }

    public DelegacionMunicipio(DelegacionMunicipioPK id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public DelegacionMunicipioPK getId() {
        return id;
    }

    @Override
    public void setId(DelegacionMunicipioPK id) {
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public CiudadMunicipio getCiudadMunicipio() {
        return ciudadMunicipio;
    }

    public void setCiudadMunicipio(CiudadMunicipio ciudadMunicipio) {
        this.ciudadMunicipio = ciudadMunicipio;
    }
}
