/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.offices;

import com.r2r.core.components.PageFormInterface;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.entity.navegacion.Oficina;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.office.OfficeEjb;
import com.r2r.core.session.UserSession;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.LocalDateTime;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

/**
 *
 * @author Carlos
 */
@Named
@ViewScoped
public class OfficeAdd extends PageFormInterface implements Serializable {

    static final String OUTCOME = "/administracion/oficina/oficinas?";
    static final String FORM_ID = "officeForm";

    private Oficina oficina;

    @Inject
    private UserSession userSession;

    @Inject
    private OfficeEjb officeEjb;

    public OfficeAdd() {
        super(OUTCOME, FORM_ID);
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        UModif uModif = new UModif();
        uModif.setFechaUModif(LocalDateTime.now());
        uModif.setUsuarioUModif(userSession.getUser());
        Messages.addError("officeForm:login:input", "Error en el sistema");
        oficina.getDireccion().setUModif(uModif);
        officeEjb.saveNewEntity(oficina, userSession.getUser());
        return true;
    }
}
