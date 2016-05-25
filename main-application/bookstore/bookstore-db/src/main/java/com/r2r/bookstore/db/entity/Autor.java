/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.db.entity;

import com.r2r.bookstore.db.DbConstant;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.entity.common.Status;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arturo
 */
@Entity
@Table(name = "AUTOR", schema = DbConstant.SCHEMA)
@XmlRootElement
public class Autor extends CatalogAbstract<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "AP_PATERNO", nullable = false, length = 50)
    private String aPaterno;

    @Size(min = 2, max = 50)
    @Column(name = "AP_MATERNO", length = 50)
    private String aMaterno;

    @Size(max = 30)
    @Column(name = "TELEFONO", length = 30)
    private String telefono;

    @Basic(optional = true)
    @Column(name = "EMAIL")
    private String email;

    @Size(min = 10, max = 13)
    @Column(name = "RFC", length = 13)
    private String rfc;

    
    public Autor() {
    }
    
    public Autor(Integer id) {
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

    public String getaPaterno() {
        return aPaterno;
    }

    public void setaPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public String getaMaterno() {
        return aMaterno;
    }

    public void setaMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
   
    @PrePersist
    public void prePersist() {
        estatus = Status.ENABLED;
    }
    
    public String getNombreCompleto() {
        return nombre + (aPaterno != null ? " " + aPaterno : "") + (aMaterno != null ? " " + aMaterno : "");
    }
    
}
