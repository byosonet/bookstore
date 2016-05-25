/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.offices;

import com.r2r.core.components.ListTableBase;
import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.navegacion.Oficina;
import com.r2r.core.db.entity.navegacion.Oficina_;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.office.OfficeEjb;
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
 * @author Arturo
 */
@Named
@ViewScoped
public class Offices extends ListTableBase<Oficina> {

    @Inject
    private OfficeEjb officeEjb;

    @Inject
    private UserSession userSession;

    private LazyOfficesModel lazyOfficesModel;

    private Status status;
    private Integer id;

    public Offices() {
        super(Oficina.class);
    }

    @PostConstruct
    public void init() {
        lazyOfficesModel = new LazyOfficesModel();
    }

    @Override
    public LazyBasicModel getLazyModel() {
        return lazyOfficesModel;
    }

    @Override
    public void enableInternal() throws DatabaseException {
        officeEjb.enableOffice(id, userSession.getUser());
    }

    @Override
    public void disableInternal() throws DatabaseException {
        officeEjb.disableOffice(id, userSession.getUser());
    }

    @Override
    protected QueryHelper<Oficina> createQueryHelper() {
        QueryHelper<Oficina> queryHelper = new QueryHelper<>();
        /*if (status != null) {
         queryHelper.addEqual(Oficina_.estatus status);
         }*/
        return queryHelper;
    }

    public class LazyOfficesModel extends LazyBasicModel {

        public LazyOfficesModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Oficina, String>, String>>(3), new HashMap<String, Tuple<SingularAttribute<? super Oficina, ?>, SortOrder>>(4));
            createFilterAndOrder("clave", Oficina_.clave);
            createFilterAndOrder("abreviatura", Oficina_.abreviatura);
            createFilterAndOrder("nombre", Oficina_.nombre);
            createOrder("estatus", Oficina_.estatus);
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
