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
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.entity.common.UModifInterface;
import com.r2r.core.db.entity.common.UModifListener;
import com.r2r.core.util.Regexp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "DIRECCION", schema = DbConstant.SCHEMA)
@XmlRootElement
@EntityListeners(UModifListener.class)
public class Direccion extends EntityAbstract<Long> implements UModifInterface {

    private static final long serialVersionUID = 1L;

    public static final NumberFormat FORMAT_CODIGO_POSTAL = new DecimalFormat("00000");

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "SEC_DIRECCION", sequenceName = "SEC_DIRECCION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_DIRECCION")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String calle;
    @Basic(optional = false)
    @NotNull
    @Size(max = 10)
    @Column(name = "NUMERO_EXT", nullable = false, length = 10)
    private String numeroExt;
    @Size(max = 10)
    @Column(name = "NUMERO_INT", length = 10)
    private String numeroInt;
    @Size(max = 250)
    @Column(length = 250)
    private String referencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_POSTAL", nullable = false)
    private Integer codigoPostal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_ALTA", nullable = false, updatable = false)
    private LocalDateTime fechaAlta;
    @JoinColumns({
        @JoinColumn(name = "ID_COLONIA_LOCALIDAD", referencedColumnName = "ID", nullable = false),
        @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", nullable = false),
        @JoinColumn(name = "ID_DELEGACION_MUNICIPIO", referencedColumnName = "ID_DELEGACION_MUNICIPIO", nullable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private ColoniaLocalidad coloniaLocalidad;
    @JoinColumns({
        @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ID_DELEGACION_MUNICIPIO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DelegacionMunicipio delegacionMunicipio;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estado estado;
    @NotNull
    @Embedded
    private UModif UModif;

    public Direccion() {
    }

    public Direccion(Long id) {
        this.id = id;
    }

    public Direccion(Long id, String calle, String numeroExt, Integer codigoPostal, LocalDateTime fechaAlta) {
        this.id = id;
        this.calle = calle;
        this.numeroExt = numeroExt;
        this.codigoPostal = codigoPostal;
        this.fechaAlta = fechaAlta;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExt() {
        return numeroExt;
    }

    public void setNumeroExt(String numeroExt) {
        this.numeroExt = numeroExt;
    }

    public String getNumeroInt() {
        return numeroInt;
    }

    public void setNumeroInt(String numeroInt) {
        this.numeroInt = numeroInt;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public ColoniaLocalidad getColoniaLocalidad() {
        return coloniaLocalidad;
    }

    public void setColoniaLocalidad(ColoniaLocalidad coloniaLocalidad) {
        this.coloniaLocalidad = coloniaLocalidad;
    }

    @Override
    public UModif getUModif() {
        return UModif;
    }

    @Override
    public void setUModif(UModif UModif) {
        this.UModif = UModif;
    }

    public DelegacionMunicipio getDelegacionMunicipio() {
        return delegacionMunicipio;
    }

    public void setDelegacionMunicipio(DelegacionMunicipio delegacionMunicipio) {
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
        return codigoPostal == null ? "" : FORMAT_CODIGO_POSTAL.format(codigoPostal);
    }

    public void setCodigoPostalAsString(String codigoPostal) {
        this.codigoPostal = Integer.valueOf(codigoPostal);
    }

    @PrePersist
    public void updateFechaAlta() {
        fechaAlta = LocalDateTime.now();
        id = 0L;
    }
}
