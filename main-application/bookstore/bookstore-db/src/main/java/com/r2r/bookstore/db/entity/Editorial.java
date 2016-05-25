/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.db.entity;

import com.r2r.bookstore.db.DbConstant;
import com.r2r.core.db.entity.Direccion;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.entity.common.Status;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arturo
 */
@Entity
@Table(name = "EDITORIAL", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_EDIT_NOMBRE", columnNames = {"NOMBRE"})})
@XmlRootElement
public class Editorial extends CatalogAbstract<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Basic
    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID", nullable = false)
    private Direccion direccion;

    @Size(min = 10, max = 13)
    @Column(name = "RFC", length = 13)
    private String rfc;

    @Size(max = 30)
    @Column(name = "TELEFONO", length = 30)
    private String telefono;

    @Basic(optional = true)
    @Column(name = "EMAIL", nullable = true)
    private String email;

    
    public Editorial() {
    }
    
    public Editorial(Integer id) {
        this.id = id;
    }
    
    
    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @PrePersist
    public void prePersist() {
        estatus = Status.ENABLED;
    }

}
