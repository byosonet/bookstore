/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.editorial;

import com.r2r.bookstore.db.entity.Editorial;
import com.r2r.bookstore.db.entity.Editorial_;
import com.r2r.bookstore.ejb.editorial.EditorialEjb;
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
public class Editoriales extends ListTableBase<Editorial>{

    @Inject
    private EditorialEjb editorialEjb;
    @Inject
    private UserSession userSession;

    private LazyEditorialesModel lazyEditorialesModel;
    private Status estatus;
    private Integer id;

    public Editoriales() {
        super(Editorial.class);
    }

    @PostConstruct
    public void init() {
        lazyEditorialesModel = new LazyEditorialesModel();
    }

    @Override
    public LazyEditorialesModel getLazyModel() {
        return lazyEditorialesModel;
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
        editorialEjb.deshabilitarEditorial(id, userSession.getUser());
    }

    @Override
    public void enableInternal() throws DatabaseException {
        editorialEjb.habilitaEditorial(id, userSession.getUser());
    }

    @Override
    protected QueryHelper<Editorial> createQueryHelper() {
        QueryHelper<Editorial> queryHelper = new QueryHelper<>();
        if (estatus != null) {
            queryHelper.addEqual(Editorial_.estatus, estatus);
        }
        return queryHelper;
    }

    public class LazyEditorialesModel extends LazyBasicModel {

        public LazyEditorialesModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Editorial, String>, String>>(1), 
                new HashMap<String, Tuple<SingularAttribute<? super Editorial, ?>, SortOrder>>(2));
            createFilterAndOrder("nombre", Editorial_.nombre);
            createFilterAndOrder("rfc", Editorial_.rfc);
            createFilterAndOrder("email", Editorial_.email);                        
            createFilterAndOrder("telefono", Editorial_.telefono);
            
            createOrder("estatus", Editorial_.estatus);
        }
    }
    
}
