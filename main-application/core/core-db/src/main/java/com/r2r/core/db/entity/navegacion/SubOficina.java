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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "SUB_OFICINA", schema = DbConstant.SCHEMA)
@XmlRootElement
public class SubOficina extends EntityAbstract<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @NotNull
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Oficina oficina;

    @JoinColumn(name = "ID_OFICINA_PADRE", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Oficina oficinaPadre;

    public SubOficina() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public Oficina getOficinaPadre() {
        return oficinaPadre;
    }

    public void setOficinaPadre(Oficina oficinaPadre) {
        this.oficinaPadre = oficinaPadre;
    }

}
