/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.entity.common.Status;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Hdez
 */
@Entity
@Table(name = "USUARIO_PUESTO", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_USR_PUESTO_NOMBRE", columnNames = {"NOMBRE"})})
@XmlRootElement
public class UsuarioPuesto extends CatalogAbstract<Short> {

//    static {
//        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_SIS_USR_PUESTO_NOM", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("nombre", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<UsuarioPuesto>() {
//            @Override
//            public Object getValue(UsuarioPuesto entity) {
//                return entity.getNombre();
//            }
//        }
//        ));
//    }
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    public UsuarioPuesto() {
    }

    public UsuarioPuesto(Short id) {
        this.id = id;
    }

    public UsuarioPuesto(Short id, String nombre, Status estatus) {
        this.id = id;
        this.nombre = nombre;
        this.estatus = estatus;
    }

    @Override
    public Short getId() {
        return id;
    }

    @Override
    public void setId(Short id) {
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
}
