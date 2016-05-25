/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.navegacion;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.Holliday;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.util.Regexp;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "SISTEMA", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NOMBRE"})})
@XmlRootElement
public class Sistema extends EntityAbstract<Short> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = Regexp.SPANISH_WORDS)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS", nullable = false)
    @Enumerated
    private Status estatus;
    @OneToMany(mappedBy = "sistema", fetch = FetchType.LAZY)
    private Collection<Holliday> hollidays;

    public Sistema() {
    }

    public Sistema(Short id) {
        this.id = id;
    }

    public Sistema(Short id, String nombre) {
        this.id = id;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

    public Collection<Holliday> getHollidays() {
        return hollidays;
    }

    public void setHollidays(Collection<Holliday> hollidays) {
        this.hollidays = hollidays;
    }
}
