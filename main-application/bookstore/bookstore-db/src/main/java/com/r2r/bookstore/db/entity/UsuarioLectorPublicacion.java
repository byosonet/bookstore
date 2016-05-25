/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.db.entity;

import com.r2r.bookstore.db.DbConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Hustler
 */
@Entity
@Table(name = "USUARIO_LECTOR_PUBLICACION", schema = DbConstant.SCHEMA)
@XmlRootElement
public class UsuarioLectorPublicacion extends EntityAbstract<UsuarioLectorPublicacionPK> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UsuarioLectorPublicacionPK id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_COMPRA", nullable = false)
    private LocalDateTime fechaCompra;

    @Override
    public UsuarioLectorPublicacionPK getId() {
        return this.id;
    }

    @Override
    public void setId(UsuarioLectorPublicacionPK id) {
        this.id = id;
    }

    @PrePersist
    public void updateFechaCompra() {
        fechaCompra = LocalDateTime.now();
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

}
