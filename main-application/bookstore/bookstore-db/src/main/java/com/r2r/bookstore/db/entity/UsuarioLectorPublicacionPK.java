/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Hustle
 */
@Embeddable
public class UsuarioLectorPublicacionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_USUARIO_LECTOR", nullable = false)
    private int idUsuarioLector;

    @Basic(optional = false)
    @Column(name = "ID_PUBLICACION", nullable = false)
    private int idPublicacion;

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public int getIdUsuarioLector() {
        return idUsuarioLector;
    }

    public void setIdUsuarioLector(int idUsuarioLector) {
        this.idUsuarioLector = idUsuarioLector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += idUsuarioLector;
        hash += idPublicacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UsuarioLectorPublicacionPK ? equals((UsuarioLectorPublicacionPK) object) : false;
    }

    public boolean equals(UsuarioLectorPublicacionPK other) {
        return this.idUsuarioLector == other.idUsuarioLector
                && this.idPublicacion == other.idPublicacion;
    }

    @Override
    public String toString() {
        return "UsuarioPublicacionPK{" + "idUsuarioLector=" + idUsuarioLector + ", idPublicacion=" + idPublicacion + '}';
    }
}
