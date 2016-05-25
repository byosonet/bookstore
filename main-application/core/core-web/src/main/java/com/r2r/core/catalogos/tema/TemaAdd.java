/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.tema;

import com.r2r.bookstore.db.entity.Tema;
import com.r2r.bookstore.ejb.tema.TemaEjb;
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
public class TemaAdd extends PageFormInterface {

    static final String OUTCOME = "/catalogos/tema/temas";
    static final String FORM_ID = "temaForm";

    private Tema tema;

    @Inject
    private UserSession userSession;
    @Inject
    private TemaEjb temaEjb;

    public TemaAdd() {
        super(OUTCOME, FORM_ID);
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        temaEjb.agregarTema(tema, userSession.getUser());
        return true;
    }
    
}
