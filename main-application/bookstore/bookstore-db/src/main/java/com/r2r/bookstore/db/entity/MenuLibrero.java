/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.db.entity;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "MENU", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_MENU_NOMBRE", columnNames = {"NOMBRE"})})
@XmlRootElement
public class MenuLibrero extends EntityAbstract<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "ID_TEMA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)})
    @NotNull
    private Tema tema;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    @SuppressWarnings("null")
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuLibrero)) {
            return false;
        }
        MenuLibrero other = (MenuLibrero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.r2r.bookstore.db.entity.MenuLibrero[ id=" + id + " ]";
    }
}
