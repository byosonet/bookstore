/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.db.entity;

import com.r2r.bookstore.db.DbConstant;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.EntityAbstract;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Hustler
 */
@Entity
@Table(name = "USUARIO_LECTOR", schema = DbConstant.SCHEMA)
public class UsuarioLector extends EntityAbstract<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", updatable = false, insertable = false)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
