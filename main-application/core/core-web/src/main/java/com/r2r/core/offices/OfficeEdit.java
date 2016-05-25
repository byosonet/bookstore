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
import static com.r2r.core.offices.OfficeAdd.FORM_ID;
import static com.r2r.core.offices.OfficeAdd.OUTCOME;
import com.r2r.core.session.UserSession;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.LocalDateTime;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Carlos
 */
@Named
@ViewScoped
public class OfficeEdit extends PageFormInterface implements Serializable {

    private Oficina oficina;

    @Inject
    private OfficeEjb officeEjb;

    @Inject
    private UserSession userSession;

    private Integer id;

    public void init() {
        oficina = officeEjb.getOfficeForEdit(id);
    }

    public OfficeEdit() {
        super(OUTCOME, FORM_ID);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        oficina.getDireccion().setUModif(uModif);
        officeEjb.editEntity(oficina, userSession.getUser());
        return true;
    }

}
