/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.permisos;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.navegacion.Oficina;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "USUARIO_OFICINA", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_USUARIO", "ID_OFICINA"})})
@XmlRootElement
public class UsuarioOficina extends EntityAbstract<UsuarioOficinaPK> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UsuarioOficinaPK id;

    @NotNull
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    @JoinColumn(name = "ID_OFICINA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Oficina oficina;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS", nullable = false)
    @Enumerated
    private Status estatus;

    public UsuarioOficina() {
    }

    public UsuarioOficina(UsuarioOficinaPK id, Status estatus) {
        this.id = id;
        this.estatus = estatus;
    }

    @Override
    public UsuarioOficinaPK getId() {
        return id;
    }

    @Override
    public void setId(UsuarioOficinaPK id) {
        this.id = id;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

}
