/*
 * ZEITEK CONFIDENTIAL
 *
 * [2014-2015] Zeitek - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.session;

import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.navegacion.Oficina;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Alan Hdez
 */
@Named
@SessionScoped
public class UserSession implements Serializable {

    private List<String> urlAccess;

    private Usuario user;
    private Oficina oficina;

    public List<String> getUrlAccess() {
        return urlAccess;
    }

    public void setUrlAccess(List<String> urlAccess) {
        this.urlAccess = urlAccess;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

}
