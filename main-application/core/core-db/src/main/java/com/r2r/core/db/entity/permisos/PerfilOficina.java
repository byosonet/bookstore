/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.permisos;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.entity.navegacion.Oficina;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "PERFIL_OFICINA", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_PERFIL", "ID_OFICINA"})})
@XmlRootElement
public class PerfilOficina extends EntityAbstract<PerfilOficinaPK> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected PerfilOficinaPK id;

    @NotNull
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Perfil perfil;

    @JoinColumn(name = "ID_OFICINA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Oficina oficina;

    public PerfilOficina() {
    }

    @Override
    public PerfilOficinaPK getId() {
        return id;
    }

    @Override
    public void setId(PerfilOficinaPK id) {
        this.id = id;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.id == null) {
            this.id = new PerfilOficinaPK(oficina.getId(), perfil.getId());
        }
    }

}
