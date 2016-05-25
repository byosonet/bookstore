/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.autor;

import com.r2r.bookstore.db.entity.Autor;
import com.r2r.bookstore.ejb.autor.AutorEjb;
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
public class AutorAdd extends PageFormInterface {

    static final String OUTCOME = "/catalogos/autor/autores";
    static final String FORM_ID = "autorForm";

    private Autor autor;

    @Inject
    private UserSession userSession;

    @Inject
    private AutorEjb autorEjb;

    public AutorAdd() {
        super(OUTCOME, FORM_ID);
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        autorEjb.agregarAutor(autor, userSession.getUser());
        return true;
    }
    
}
