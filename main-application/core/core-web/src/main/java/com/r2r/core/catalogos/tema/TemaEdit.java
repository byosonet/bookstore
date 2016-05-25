/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.catalogos.tema;

import com.r2r.bookstore.db.entity.Tema;
import com.r2r.bookstore.ejb.tema.TemaEjb;
import static com.r2r.core.catalogos.tema.TemaAdd.FORM_ID;
import static com.r2r.core.catalogos.tema.TemaAdd.OUTCOME;
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
public class TemaEdit extends PageFormInterface {
    
    @Inject
    private TemaEjb temaEjb;
    @Inject
    private UserSession userSession;

    private Tema tema;
    private Integer id;


    public TemaEdit() {
        super(OUTCOME, FORM_ID);
    }

    public void init() {
        tema = temaEjb.getTema(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
    
    @Override
    public boolean doInternalAction() throws DatabaseException {
        temaEjb.actualizarTema(tema, userSession.getUser());
        return true;
    }
    
}
