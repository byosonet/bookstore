/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.comun.publicacion;

import com.r2r.bookstore.db.entity.Publicacion;
import com.r2r.bookstore.ejb.publicacion.PublicacionEjb;
import com.r2r.core.components.PageFormInterface;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.session.UserSession;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author
 */
@Named
@ViewScoped
public class PublicacionAdd extends PageFormInterface {

    private final static Logger LOGGER = LoggerFactory.getLogger(PublicacionAdd.class);
    
    @Inject
    private UserSession userSession;
    @Inject
    private PublicacionEjb publicacionEjb;

    static final String OUTCOME = "/publicacion/publicaciones";
    static final String FORM_ID = "publicacionForm";

    private Publicacion publicacion;
    
    public PublicacionAdd() {
        super(OUTCOME, FORM_ID);
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        LOGGER.info("doInternalAction.........");
        System.err.println("doInternalAction....");
        publicacionEjb.agregarPublicacion(publicacion, userSession.getUser());
        return true;
    }
    
}
