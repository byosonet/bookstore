/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.navegacion;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.Direccion;
import com.r2r.core.db.entity.Usuario;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
@Table(name = "OFICINA", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_OFIC_NOMBRE", columnNames = {"NOMBRE"}),
    @UniqueConstraint(name = "UNQ_OFIC_CLV_MOD", columnNames = {"CLAVE"})})
@XmlRootElement
public class Oficina extends CatalogAbstract<Integer> {

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_OFIC_NOMBRE", DatabaseConstraintIndex.tripleCatalogName);
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_OFIC_CLV_MOD", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("clave", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<Oficina>() {
            @Override
            public Object[] getValues(Oficina entity) {
                return new Object[]{entity.getClave()};
            }

            @Override
            public String getLabel() {
                return "core.officeAlreadyRegistered";
            }
        }
        ));
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 4)
    @Column(name = "CLAVE", nullable = false, length = 4)
    private String clave;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Size(max = 10)
    @Column(name = "ABREVIATURA", length = 10)
    private String abreviatura;
    @Size(max = 30)
    @Column(name = "TELEFONO", length = 30)
    private String telefono;

    @NotNull
    @JoinColumn(name = "ID_USUARIO_RESPONSABLE_OFICINA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario responsableOficina;

    @OneToOne(mappedBy = "oficina", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SubOficina oficinaPadre;
    @OneToMany(mappedBy = "oficinaPadre", fetch = FetchType.LAZY)
    private List<SubOficina> oficinasHijas;

    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    private Direccion direccion;

    public Oficina() {
    }

    public Oficina(Integer idOficina) {
        this.id = idOficina;
    }

    public Oficina(Integer idOficina, String nombre) {
        this.id = idOficina;
        this.nombre = nombre;
    }

    public Oficina(Integer id, String nombre, String abreviatura, Usuario responsableOficina, Direccion direccion) {
        this.id = id;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.responsableOficina = responsableOficina;
        this.direccion = direccion;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Usuario getResponsableOficina() {
        return responsableOficina;
    }

    public void setResponsableOficina(Usuario responsableOficina) {
        this.responsableOficina = responsableOficina;
    }

    public SubOficina getOficinaPadre() {
        return oficinaPadre;
    }

    public void setOficinaPadre(SubOficina oficinaPadre) {
        this.oficinaPadre = oficinaPadre;
    }

    public Oficina getOficinaPadreOficina() {
        if (oficinaPadre != null) {
            return oficinaPadre.getOficinaPadre();
        } else {
            return null;
        }
    }

    public void setOficinaPadreOficina(Oficina oficina) {
        if (oficinaPadre == null) {
            this.oficinaPadre = new SubOficina();
            this.oficinaPadre.setOficina(this);
        }
        this.oficinaPadre.setOficinaPadre(oficina);
    }

    public List<SubOficina> getOficinasHijas() {
        return oficinasHijas;
    }

    public void setOficinasHijas(List<SubOficina> oficinasHijas) {
        this.oficinasHijas = oficinasHijas;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @PrePersist
    public void idWorkaround() {
        id = 0;
    }
}
