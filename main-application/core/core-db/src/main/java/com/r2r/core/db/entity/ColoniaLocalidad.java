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
import com.r2r.core.db.entity.common.Status;
import static com.r2r.core.db.entity.Direccion.FORMAT_CODIGO_POSTAL;
import com.r2r.core.db.exception.DatabaseConstraintIndex;
import com.r2r.core.db.exception.InterfaceConstraintViolated;
import com.r2r.core.util.Regexp;
import com.r2r.core.util.Triple;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "COLONIA_LOCALIDAD", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_COLONIA_LOC_NOMBRE", columnNames = {"NOMBRE", "ID_ESTADO", "ID_DELEGACION_MUNICIPIO", "CODIGO_POSTAL"})})
@XmlRootElement
public class ColoniaLocalidad extends CatalogAbstract<ColoniaLocalidadPK> {

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_COLONIA_LOC_NOMBRE", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("nombre", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<ColoniaLocalidad>() {
            @Override
            public Object[] getValues(ColoniaLocalidad entity) {
                return new Object[]{entity.getNombre(), entity.getEstado().getNombre(), entity.getDelegacionMunicipio().getNombre(), entity.getCodigoPostalAsString()};
            }

            @Override
            public String getLabel() {
                return "core.coloniesAlreadyExists";
            }
        }
        ));
    }

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ColoniaLocalidadPK id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_POSTAL", nullable = false)
    private int codigoPostal;
    @JoinColumns({
        @JoinColumn(name = "ID_DELEGACION_MUNICIPIO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DelegacionMunicipio delegacionMunicipio;
    @JoinColumns({
        @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estado estado;

    public ColoniaLocalidad() {
    }

    public ColoniaLocalidad(ColoniaLocalidadPK id) {
        this.id = id;
    }

    public ColoniaLocalidad(ColoniaLocalidadPK id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ColoniaLocalidad(ColoniaLocalidadPK id, String nombre, int codigoPostal) {
        this.id = id;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
    }

    public ColoniaLocalidad(ColoniaLocalidadPK id, String nombre, int codigoPostal, Status estatus) {
        this.id = id;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.estatus = estatus;
    }

    public ColoniaLocalidad(short idEstado, int idDelegacionMunicipio, int idColoniaLocalidad) {
        this.id = new ColoniaLocalidadPK(idEstado, idDelegacionMunicipio, idColoniaLocalidad);
    }

    @Override
    public ColoniaLocalidadPK getId() {
        return id;
    }

    @Override
    public void setId(ColoniaLocalidadPK id) {
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

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public DelegacionMunicipio getDelegacionMunicipio() {
        return delegacionMunicipio;
    }

    public void setDelegacionMunicipio(DelegacionMunicipio delegacionMunicipio) {
        this.delegacionMunicipio = delegacionMunicipio;
    }

    public void setCatDelegacionMunicipio(DelegacionMunicipio delegacionMunicipio) {
        this.delegacionMunicipio = delegacionMunicipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Size(min = 5, max = 5)
    @Pattern(regexp = Regexp.NUMBER_ONLY)
    @NotNull
    public String getCodigoPostalAsString() {
        return codigoPostal == 0 ? "" : FORMAT_CODIGO_POSTAL.format(codigoPostal);
    }

    public void setCodigoPostalAsString(String codigoPostal) {
        this.codigoPostal = Integer.valueOf(codigoPostal);
    }
}
