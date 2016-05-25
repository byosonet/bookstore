/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.EntityUModifAbstract;
import com.r2r.core.db.exception.DatabaseConstraintIndex;
import com.r2r.core.db.exception.InterfaceConstraintViolated;
import com.r2r.core.util.Triple;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "AUTORIZACION_USUARIO", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_AUTH_USER", columnNames = {"ID_AUTORIZACION", "ID_USUARIO"})
})
@XmlRootElement
public class AutorizacionUsuario extends EntityUModifAbstract<AutorizacionUsuarioPK> {

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_AUTH_USER", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("usuario", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<AutorizacionUsuario>() {

            @Override
            public Object[] getValues(AutorizacionUsuario entity) {
                return new Object[]{entity.getUsuario(), entity.getAutorizacion()};
            }

            @Override
            public String getLabel() {
                return "infraction.authProcessEditFails";
            }
        }
        ));
    }

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AutorizacionUsuarioPK id;

    @NotNull
    @JoinColumn(name = "ID_AUTORIZACION", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Autorizacion autorizacion;

    @NotNull
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    @Override
    public AutorizacionUsuarioPK getId() {
        return id;
    }

    @Override
    public void setId(AutorizacionUsuarioPK id) {
        this.id = id;
    }

    public Autorizacion getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(Autorizacion autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @PrePersist
    protected void prePersist() {
        if (id == null) {
            id = new AutorizacionUsuarioPK(autorizacion.getId(), usuario.getId());
        }
    }

}
