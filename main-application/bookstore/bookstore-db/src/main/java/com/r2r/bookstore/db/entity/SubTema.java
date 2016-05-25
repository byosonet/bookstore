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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "SUBTEMA", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_SUBTEMA_NOMBRE", columnNames = {"NOMBRE"})})
@XmlRootElement
public class SubTema extends EntityAbstract<Integer> {

    /**
     *
     */
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

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TEMA", referencedColumnName = "ID", nullable = false)
    private Tema tema;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
