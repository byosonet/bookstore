/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.editorial;

import com.r2r.bookstore.db.entity.Editorial;
import com.r2r.bookstore.ejb.editorial.EditorialEjb;
import static com.r2r.core.catalogos.editorial.EditorialAdd.FORM_ID;
import static com.r2r.core.catalogos.editorial.EditorialAdd.OUTCOME;
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
public class EditorialEdit extends PageFormInterface {
    
    @Inject
    private EditorialEjb editorialEjb;
    @Inject
    private UserSession userSession;

    private Editorial editorial;
    private Integer id;


    public EditorialEdit() {
        super(OUTCOME, FORM_ID);
    }

    public void init() {
        editorial = editorialEjb.getEditorial(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
    
    @Override
    public boolean doInternalAction() throws DatabaseException {
        editorialEjb.actualizarEditorial(editorial, userSession.getUser());
        return true;
    }
    
}
