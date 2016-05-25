/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.exception.DatabaseConstraintIndex;
import com.r2r.core.db.exception.InterfaceConstraintViolated;
import com.r2r.core.util.Regexp;
import com.r2r.core.util.Triple;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "USUARIO", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_USER_LOGIN", columnNames = {"LOGIN"})})
@XmlRootElement
public class Usuario extends EntityAbstract<Integer> {

    private static final long serialVersionUID = 1L;

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_USER_LOGIN", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("login", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<Usuario>() {
            @Override
            public Object[] getValues(Usuario entity) {
                return new Object[]{entity.getLogin()};
            }

            @Override
            public String getLabel() {
                return "core.userLoginAlreadyInSystem";
            }
        }
        ));
    }

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "LOGIN", nullable = false, length = 30)
    @Pattern(regexp = Regexp.BASIC_CHARACTERS)
    private String login;

    @Basic(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @Size(max = 100)
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    @Pattern(regexp = Regexp.SPANISH_WORDS_DOT)
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "A_PATERNO", nullable = false, length = 50)
    @Pattern(regexp = Regexp.SPANISH_WORDS_DOT)
    private String APaterno;

    @Size(max = 50)
    @Column(name = "A_MATERNO", length = 50)
    @Pattern(regexp = Regexp.SPANISH_WORDS_DOT)
    private String AMaterno;

    @Lob
    @Column(name = "FOTO")
    private byte[] foto;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS", nullable = false)
    @Enumerated
    private Status estatus;

    @Basic(optional = true)
    @Column(name = "EMAIL", nullable = true)
    private String email;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String nombre, String APaterno, String AMaterno, String password) {
        this.id = id;
        this.nombre = nombre;
        this.APaterno = APaterno;
        this.AMaterno = AMaterno;
        this.password = password;
    }

    public Usuario(Integer id, String nombre, String APaterno, String AMaterno, String password, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.APaterno = APaterno;
        this.AMaterno = AMaterno;
        this.password = password;
        this.foto = foto;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAPaterno() {
        return APaterno;
    }

    public void setAPaterno(String APaterno) {
        this.APaterno = APaterno;
    }

    public String getAMaterno() {
        return AMaterno;
    }

    public void setAMaterno(String AMaterno) {
        this.AMaterno = AMaterno;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

    public String getUserLoginName() {
        return nombre + (APaterno != null ? " " + APaterno : "") + (AMaterno != null ? " " + AMaterno : "") + " (" + login + ")";
    }

    /**
     * @return The name of the user considering the last names.
     */
    public String getFullName() {
        return nombre + (APaterno != null ? " " + APaterno : "") + (AMaterno != null ? " " + AMaterno : "");
    }

    public String getShowedName() {
        return nombre + (APaterno != null ? " " + APaterno : "");
    }

    @PrePersist
    public void prePersist() {
        estatus = Status.ENABLED;
        password = new BasicPasswordEncryptor().encryptPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
