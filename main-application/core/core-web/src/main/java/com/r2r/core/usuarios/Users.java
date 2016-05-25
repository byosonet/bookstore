/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.usuarios;

import com.r2r.core.components.ListTableBase;
import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.Usuario_;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.session.UserSession;
import com.r2r.core.user.UserEjb;
import com.r2r.core.util.Tuple;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.metamodel.SingularAttribute;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Alan Hdez
 */
@Named
@ViewScoped
public class Users extends ListTableBase<Usuario> {

    @Inject
    private UserEjb userEjb;
    @Inject
    private UserSession userSession;

    private LazyUsuariosModel lazyUsuariosModel;

    private Status estatus;

    private Integer id;

    public Users() {
        super(Usuario.class);
    }

    @PostConstruct
    public void init() {
        lazyUsuariosModel = new LazyUsuariosModel();
    }

    @Override
    public LazyUsuariosModel getLazyModel() {
        return lazyUsuariosModel;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void disableInternal() throws DatabaseException {
        userEjb.disableUsuario(id, userSession.getUser());
    }

    @Override
    public void enableInternal() throws DatabaseException {
        userEjb.enableUsuario(id, userSession.getUser());
    }

    @Override
    protected QueryHelper<Usuario> createQueryHelper() {
        QueryHelper<Usuario> queryHelper = new QueryHelper<>();
        if (estatus != null) {
            queryHelper.addEqual(Usuario_.estatus, estatus);
        }
        return queryHelper;
    }

    public class LazyUsuariosModel extends LazyBasicModel {

        public LazyUsuariosModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Usuario, String>, String>>(4), new HashMap<String, Tuple<SingularAttribute<? super Usuario, ?>, SortOrder>>(5));
            createFilterAndOrder("login", Usuario_.login);
            createFilterAndOrder("nombre", Usuario_.nombre);
            createFilterAndOrder("APaterno", Usuario_.APaterno);
            createFilterAndOrder("AMaterno", Usuario_.AMaterno);
            createOrder("estatus", Usuario_.estatus);
        }
    }

}
