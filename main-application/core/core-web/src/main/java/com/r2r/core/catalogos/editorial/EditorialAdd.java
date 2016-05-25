/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.editorial;

import com.r2r.bookstore.db.entity.Editorial;
import com.r2r.bookstore.ejb.editorial.EditorialEjb;
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
public class EditorialAdd extends PageFormInterface {

    static final String OUTCOME = "/catalogos/editorial/editoriales";
    static final String FORM_ID = "editorialForm";

    private Editorial editorial;

    @Inject
    private UserSession userSession;
    @Inject
    private EditorialEjb editorialEjb;

    public EditorialAdd() {
        super(OUTCOME, FORM_ID);
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        editorialEjb.agregarEditorial(editorial, userSession.getUser());
        return true;
    }
    
}
