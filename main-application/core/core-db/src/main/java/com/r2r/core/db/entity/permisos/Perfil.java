/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.permisos;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.exception.DatabaseConstraintIndex;
import com.r2r.core.db.exception.InterfaceConstraintViolated;
import com.r2r.core.util.Triple;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Entity
@Table(name = "PERFIL", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_PERFIL", columnNames = {"NOMBRE"})})
@XmlRootElement
public class Perfil extends CatalogAbstract<Integer> {

    private static final long serialVersionUID = 1L;

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_PERFIL", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("nombre", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<Perfil>() {
            @Override
            public Object[] getValues(Perfil entity) {
                return new Object[]{entity.getNombre()};
            }

            @Override
            public String getLabel() {
                return "core.systemWithProfileName";
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
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;
    @Size(max = 300)
    @Column(name = "DESCRIPCION", length = 300)
    private String descripcion;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PerfilMenu> menus;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    private List<PerfilOficina> oficinas;

    public Perfil() {
    }

    public Perfil(Integer id) {
        this.id = id;
    }

    public Perfil(Integer id, String nombre) {
        this.id = id;
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

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<PerfilMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<PerfilMenu> menus) {
        this.menus = menus;
    }

    public List<PerfilOficina> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<PerfilOficina> oficinas) {
        this.oficinas = oficinas;
    }

}
