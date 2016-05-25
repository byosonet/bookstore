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
import com.r2r.core.db.entity.common.ModifTrackAbstract;
import com.r2r.core.db.exception.DatabaseConstraintIndex;
import com.r2r.core.db.exception.InterfaceConstraintViolated;
import com.r2r.core.util.Triple;
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
import org.joda.time.LocalDate;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "HOLLIDAY", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_HOLLIDAY_DATE", columnNames = {"HOLLIDAY", "ID_SISTEMA"})
})
@XmlRootElement
public class Holliday extends ModifTrackAbstract<Integer> {

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_HOLLIDAY_DATE", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("clave", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<Holliday>() {
            @Override
            public Object[] getValues(Holliday entity) {
                return new Object[]{entity.getHolliday()};
            }

            @Override
            public String getLabel() {
                return "core.hollidayAlreadyRegistered";
            }
        }
        ));
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sistema sistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOLLIDAY", nullable = false)
    protected LocalDate holliday;

    public Holliday() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public LocalDate getHolliday() {
        return holliday;
    }

    public void setHolliday(LocalDate holliday) {
        this.holliday = holliday;
    }
}
