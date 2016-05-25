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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hustler
 */
@Entity
@Table(name = "TEMA", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_TEMA_NOMBRE", columnNames = {"NOMBRE"})})
@XmlRootElement
public class Tema extends CatalogAbstract<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    
    public Tema() {
    }
    
    public Tema(Integer id) {
        this.id = id;
    }
    
    
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
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

    @PrePersist
    public void prePersist() {
        estatus = Status.ENABLED;
    }
    
}
