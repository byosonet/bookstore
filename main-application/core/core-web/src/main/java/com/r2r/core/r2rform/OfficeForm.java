/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.navegacion.Oficina;
import com.r2r.core.office.OfficeEjb;
import java.io.IOException;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Carlos
 */
@FacesComponent("officeForm")
public class OfficeForm extends UINamingContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserForm.class);

    @Inject
    private OfficeEjb officeEjb;

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        if (getValue() == null) {
            updateValue(context, new Oficina());
        }
        initOfficeFather();
        initListUser();
    }

    private void initOfficeFather() {
        if (getOfficeFather() == null) {
            setOfficeFather(officeEjb.getOfficeFather(getValue()));
        }
    }

    private void initListUser() {
        if (getUserResponsable() == null) {
            setUserResponsable(officeEjb.getListUsuarios());
        }
    }

    public List<Oficina> getOfficeFather() {
        return (List<Oficina>) getStateHelper().get("officeFather");
    }

    public List<Usuario> getUserResponsable() {
        return (List<Usuario>) getStateHelper().get("userResponsable");
    }

    public void setOfficeFather(List<Oficina> userTitles) {
        getStateHelper().put("officeFather", userTitles);
    }

    public void setUserResponsable(List<Usuario> userTitles) {
        getStateHelper().put("userResponsable", userTitles);
    }

    public Oficina getValue() {
        return (Oficina) getStateHelper().eval("value");
    }

    public void setValue(Oficina oficina) {
        getStateHelper().put("value", oficina);
    }

    public void updateValue(FacesContext context, Oficina value) {
        ValueExpression ve = this.getValueExpression("value");

        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setValue(value);
        }
    }
}
