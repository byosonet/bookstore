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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "USUARIO_INFO", schema = DbConstant.SCHEMA)
@XmlRootElement
public class UsuarioInfo extends EntityUModifAbstract<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 13)
    @Column(name = "RFC", length = 13)
    private String rfc;

    @Size(max = 19)
    @Column(name = "CURP", length = 19)
    private String curp;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_ALTA", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "FECHA_BAJA")
    private LocalDateTime fechaBaja;

    @JoinColumn(name = "ID_USUARIO_TITULO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioTitulo usuarioTitulo;

    @NotNull
    @JoinColumn(name = "ID_USUARIO_PUESTO", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UsuarioPuesto usuarioPuesto;

    @NotNull
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;

    public UsuarioInfo() {
    }

//    public UsuarioInfo(Integer idUsuario, String nombre, String login) {
//        this.id = idUsuario;
//        this.nombre = nombre;
//        this.login = login;
//    }
//
//    public UsuarioInfo(Integer id, String nombre, String APaterno, String AMaterno, String nombrePuesto, String tituloAbreviatura) {
//        this.id = id;
//        this.nombre = nombre;
//        this.APaterno = APaterno;
//        this.AMaterno = AMaterno;
//        this.usuarioPuesto = new UsuarioPuesto();
//        this.usuarioPuesto.setNombre(nombrePuesto);
//        this.usuarioTitulo = new UsuarioTitulo();
//        this.usuarioTitulo.setAbreviatura(tituloAbreviatura);
//    }
//    public UsuarioInfo(Integer id, String nombre, String APaterno, String AMaterno, String password) {
//        this.id = id;
//        this.nombre = nombre;
//        this.APaterno = APaterno;
//        this.AMaterno = AMaterno;
//        this.password = password;
//    }
    public UsuarioInfo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public UsuarioTitulo getUsuarioTitulo() {
        return usuarioTitulo;
    }

    public void setUsuarioTitulo(UsuarioTitulo usuarioTitulo) {
        this.usuarioTitulo = usuarioTitulo;
    }

    public UsuarioPuesto getUsuarioPuesto() {
        return usuarioPuesto;
    }

    public void setUsuarioPuesto(UsuarioPuesto usuarioPuesto) {
        this.usuarioPuesto = usuarioPuesto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @PrePersist
    public void prePersist() {
        fechaAlta = LocalDateTime.now();
        if (id == null) {
            id = usuario.getId();
        }
    }
}
