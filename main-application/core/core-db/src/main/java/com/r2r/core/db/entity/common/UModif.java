/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.common;

import com.r2r.core.db.entity.Usuario;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.joda.time.LocalDateTime;

/**
 * Encapsulates the last modification data.
 * <p>
 * Keeps together the last modification data, saving some space in the classes
 * who need it.</p>
 *
 * @author Alan Hdez
 */
@Embeddable
public class UModif implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_UMODIF", nullable = false)
    protected LocalDateTime fechaUModif;

    @NotNull
    @JoinColumn(name = "ID_USUARIO_UMODIF", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    protected Usuario usuarioUModif;

    public UModif() {
    }

    public UModif(Usuario usuarioUModif, LocalDateTime fechaUModif) {
        this.usuarioUModif = usuarioUModif;
        this.fechaUModif = fechaUModif;
    }

    public UModif(Usuario usuarioUModif) {
        this.usuarioUModif = usuarioUModif;
    }

    public LocalDateTime getFechaUModif() {
        return fechaUModif;
    }

    public void setFechaUModif(LocalDateTime fechaUModif) {
        this.fechaUModif = fechaUModif;
    }

    public Usuario getUsuarioUModif() {
        return usuarioUModif;
    }

    public void setUsuarioUModif(Usuario usuarioUModif) {
        this.usuarioUModif = usuarioUModif;
    }
}
