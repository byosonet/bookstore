/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.autor;

import com.r2r.bookstore.db.entity.Autor;
import com.r2r.bookstore.ejb.autor.AutorEjb;
import static com.r2r.core.catalogos.autor.AutorAdd.FORM_ID;
import static com.r2r.core.catalogos.autor.AutorAdd.OUTCOME;
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
public class AutorEdit extends PageFormInterface {
    
    @Inject
    private AutorEjb autorEjb;
    @Inject
    private UserSession userSession;

    private Autor autor;
    private Integer id;


    public AutorEdit() {
        super(OUTCOME, FORM_ID);
    }

    public void init() {
        autor = autorEjb.getAutor(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        autorEjb.actualizarAutor(autor, userSession.getUser());
        return true;
    }
    
}
