/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.autor;

import com.r2r.bookstore.db.entity.Autor;
import com.r2r.bookstore.db.entity.Autor_;
import com.r2r.bookstore.ejb.autor.AutorEjb;
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
public class Autores extends ListTableBase<Autor>{

    @Inject
    private AutorEjb autorEjb;
    @Inject
    private UserSession userSession;

    private LazyAutoresModel lazyAutoresModel;
    private Status estatus;
    private Integer id;

    public Autores() {
        super(Autor.class);
    }

    @PostConstruct
    public void init() {
        lazyAutoresModel = new LazyAutoresModel();
    }

    @Override
    public LazyAutoresModel getLazyModel() {
        return lazyAutoresModel;
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
        autorEjb.deshabilitarAutor(id, userSession.getUser());
    }

    @Override
    public void enableInternal() throws DatabaseException {
        autorEjb.habilitaAutor(id, userSession.getUser());
    }

    @Override
    protected QueryHelper<Autor> createQueryHelper() {
        QueryHelper<Autor> queryHelper = new QueryHelper<>();
        if (estatus != null) {
            queryHelper.addEqual(Autor_.estatus, estatus);
        }
        return queryHelper;
    }

    public class LazyAutoresModel extends LazyBasicModel {

        public LazyAutoresModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Autor, String>, String>>(1), new HashMap<String, Tuple<SingularAttribute<? super Autor, ?>, SortOrder>>(2));
            createFilterAndOrder("nombre", Autor_.nombre);
            createFilterAndOrder("aPaterno", Autor_.aPaterno);
            createFilterAndOrder("aMaterno", Autor_.aMaterno);
            createFilterAndOrder("rfc", Autor_.rfc);
            createFilterAndOrder("email", Autor_.email);                        
            createFilterAndOrder("telefono", Autor_.telefono);
            
            createOrder("estatus", Autor_.estatus);
        }
    }
    
}
