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
@Table(name = "USUARIO_PERFIL", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_USUARIO", "ID_PERFIL"})})
@XmlRootElement
public class UsuarioPerfil extends EntityAbstract<UsuarioPerfilPK> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UsuarioPerfilPK id;

    @NotNull
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Perfil perfil;

    public UsuarioPerfil() {
    }

    @Override
    public UsuarioPerfilPK getId() {
        return id;
    }

    @Override
    public void setId(UsuarioPerfilPK id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.id == null) {
            this.id = new UsuarioPerfilPK(perfil.getId(), usuario.getId());
        }
    }
}
