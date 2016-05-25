/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.comun.publicacion;


import com.r2r.bookstore.db.entity.Publicacion;
import com.r2r.bookstore.db.entity.Publicacion_;
import com.r2r.bookstore.ejb.publicacion.PublicacionEjb;
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
public class Publicaciones extends ListTableBase<Publicacion>{

    @Inject
    private PublicacionEjb publicacionEjb;
    @Inject
    private UserSession userSession;

    private LazyPublicacionesModel lazyPublicacionesModel;
    private Status estatus;
    private Integer id;

    public Publicaciones() {
        super(Publicacion.class);
    }

    @PostConstruct
    public void init() {
        lazyPublicacionesModel = new LazyPublicacionesModel();
    }

    @Override
    public LazyPublicacionesModel getLazyModel() {
        return lazyPublicacionesModel;
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
        publicacionEjb.deshabilitarPublicacion(id, userSession.getUser());
    }

    @Override
    public void enableInternal() throws DatabaseException {
        publicacionEjb.habilitaPublicacion(id, userSession.getUser());
    }

    @Override
    protected QueryHelper<Publicacion> createQueryHelper() {
        QueryHelper<Publicacion> queryHelper = new QueryHelper<>();
        if (estatus != null) {
            queryHelper.addEqual(Publicacion_.estatus, estatus);
        }
        return queryHelper;
    }

    public class LazyPublicacionesModel extends LazyBasicModel {

        public LazyPublicacionesModel() {
            super(new HashMap<String, Tuple<SingularAttribute<? super Publicacion, String>, String>>(1), 
                new HashMap<String, Tuple<SingularAttribute<? super Publicacion, ?>, SortOrder>>(2));
            createFilterAndOrder("nombre", Publicacion_.nombre);
            createFilterAndOrder("isbn", Publicacion_.isbn);
                        
            createOrder("estatus", Publicacion_.estatus);
        }
    }
    
}
