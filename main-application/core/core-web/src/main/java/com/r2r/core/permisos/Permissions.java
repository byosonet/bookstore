/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.permisos;

import com.r2r.core.components.ListTableBase;
import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.Usuario_;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.util.Tuple;
import java.util.HashMap;
import javax.annotation.PostConstruct;
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
public class Permissions extends ListTableBase<Usuario> {

    private LazyPermissionModel lazyPermissionModel;

    private Status estatus;

    private Integer id;

    public Permissions() {
        super(Usuario.class);
    }

    @PostConstruct
    public void init() {
        lazyPermissionModel = new LazyPermissionModel();
    }

    @Override
    public LazyPermissionModel getLazyModel() {
        return lazyPermissionModel;
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

    }

    @Override
    public void enableInternal() throws DatabaseException {

    }

    @Override
    protected QueryHelper<Usuario> createQueryHelper() {
        QueryHelper<Usuario> queryHelper = new QueryHelper<>();
        return queryHelper;
    }

    public class LazyPermissionModel extends LazyBasicModel {

        public LazyPermissionModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Usuario, String>, String>>(4), new HashMap<String, Tuple<SingularAttribute<? super Usuario, ?>, SortOrder>>(5));
            createFilterAndOrder("login", Usuario_.login);
            createFilterAndOrder("nombre", Usuario_.nombre);
            createFilterAndOrder("APaterno", Usuario_.APaterno);
            createFilterAndOrder("AMaterno", Usuario_.AMaterno);
            createOrder("estatus", Usuario_.estatus);
        }
    }

}
