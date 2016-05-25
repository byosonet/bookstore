/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.tema;

import com.r2r.bookstore.db.entity.Tema;
import com.r2r.bookstore.db.entity.Tema_;
import com.r2r.bookstore.ejb.tema.TemaEjb;
import com.r2r.core.components.ListTableBase;
import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.exception.DatabaseException;
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
 * @author
 */
@Named
@ViewScoped
public class Temas extends ListTableBase<Tema>{

    @Inject
    private TemaEjb temaEjb;
    @Inject
    private UserSession userSession;

    private LazyTemasModel lazyTemasModel;
    private Status estatus;
    private Integer id;

    public Temas() {
        super(Tema.class);
    }

    @PostConstruct
    public void init() {
        lazyTemasModel = new LazyTemasModel();
    }

    @Override
    public LazyTemasModel getLazyModel() {
        return lazyTemasModel;
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
        temaEjb.deshabilitarTema(id, userSession.getUser());
    }

    @Override
    public void enableInternal() throws DatabaseException {
        temaEjb.habilitaTema(id, userSession.getUser());
    }

    @Override
    protected QueryHelper<Tema> createQueryHelper() {
        QueryHelper<Tema> queryHelper = new QueryHelper<>();
        if (estatus != null) {
            queryHelper.addEqual(Tema_.estatus, estatus);
        }
        return queryHelper;
    }

    public class LazyTemasModel extends LazyBasicModel {
        public LazyTemasModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Tema, String>, String>>(1), 
                new HashMap<String, Tuple<SingularAttribute<? super Tema, ?>, SortOrder>>(2));
            createFilterAndOrder("nombre", Tema_.nombre);
            
            createOrder("estatus", Tema_.estatus);
        }
    }
    
}
