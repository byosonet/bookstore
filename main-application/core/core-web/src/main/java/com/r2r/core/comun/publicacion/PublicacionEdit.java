/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.comun.publicacion;

import com.r2r.bookstore.db.entity.Publicacion;
import com.r2r.bookstore.ejb.publicacion.PublicacionEjb;
import static com.r2r.core.comun.publicacion.PublicacionAdd.FORM_ID;
import static com.r2r.core.comun.publicacion.PublicacionAdd.OUTCOME;
import com.r2r.core.components.PageFormInterface;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.session.UserSession;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author
 */
@Named
@ViewScoped
public class PublicacionEdit extends PageFormInterface {
        
    @Inject
    private PublicacionEjb publicacionEjb;
    @Inject
    private UserSession userSession;

    private Publicacion publicacion;
    private Integer id;


    public PublicacionEdit() {
        super(OUTCOME, FORM_ID);
    }

    public void init() {
        publicacion = publicacionEjb.getPublicacion(id);;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
    
    @Override
    public boolean doInternalAction() throws DatabaseException {
        publicacionEjb.actualizarPublicacion(publicacion, userSession.getUser());
        return true;
    }
    
}