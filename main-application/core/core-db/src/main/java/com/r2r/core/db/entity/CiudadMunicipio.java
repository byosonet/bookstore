/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "CIUDAD_MUNICIPIO", catalog = "", schema = DbConstant.SCHEMA)
public class CiudadMunicipio extends EntityAbstract<CiudadMunicipioPK> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
//    @NotNull
    private CiudadMunicipioPK id;
    @JoinColumns({
        @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", nullable = false),
        @JoinColumn(name = "ID_DELEGACION_MUNICIPIO", referencedColumnName = "ID", nullable = false)})
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private DelegacionMunicipio delegacionMunicipio;

    @JoinColumns({
        @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ID_CUIDAD", referencedColumnName = "ID", nullable = false)})
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Ciudad ciudad;

    @Override
    public CiudadMunicipioPK getId() {
        return id;
    }

    @Override
    public void setId(CiudadMunicipioPK id) {
        this.id = id;
    }

    public DelegacionMunicipio getDelegacionMunicipio() {
        return delegacionMunicipio;
    }

    public void setDelegacionMunicipio(DelegacionMunicipio delegacionMunicipio) {
        this.delegacionMunicipio = delegacionMunicipio;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
