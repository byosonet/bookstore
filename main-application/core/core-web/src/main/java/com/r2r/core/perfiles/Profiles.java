/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.perfiles;

import com.r2r.core.components.ListTableBase;
import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.entity.permisos.Perfil_;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.profile.ProfileEjb;
import com.r2r.core.session.UserSession;
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
public class Profiles extends ListTableBase<Perfil> {

    @Inject
    private ProfileEjb profileEjb;

    @Inject
    private UserSession userSession;

    private LazyPerfilesModel lazyPerfilesModel;

    private Status estatus;

    private Integer id;

    public Profiles() {
        super(Perfil.class);
    }

    @PostConstruct
    public void init() {
        lazyPerfilesModel = new LazyPerfilesModel();
    }

    @Override
    public LazyPerfilesModel getLazyModel() {
        return lazyPerfilesModel;
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
        profileEjb.changeStatusModifTrack(Perfil.class, id, userSession.getUser(), Status.DISABLED);
    }

    @Override
    public void enableInternal() throws DatabaseException {
        profileEjb.changeStatusModifTrack(Perfil.class, id, userSession.getUser(), Status.ENABLED);

    }

    @Override
    protected QueryHelper<Perfil> createQueryHelper() {
        QueryHelper<Perfil> queryHelper = new QueryHelper<>();
        if (estatus != null) {
            queryHelper.addEqual(Perfil_.estatus, estatus);
        }
        return queryHelper;
    }

    public class LazyPerfilesModel extends LazyBasicModel {

        public LazyPerfilesModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Perfil, String>, String>>(2), new HashMap<String, Tuple<SingularAttribute<? super Perfil, ?>, SortOrder>>(3));
            createFilterAndOrder("nombre", Perfil_.nombre);
            createFilterAndOrder("descripcion", Perfil_.descripcion);
            createOrder("estatus", Perfil_.estatus);
        }
    }

}
