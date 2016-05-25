/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.entity.permisos.PerfilMenu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@FacesComponent("profileForm")
public class ProfileForm extends UINamingContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileForm.class);

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        if (getValue() == null) {
            updateValue(context, new Perfil());
        }

    }

    public List<Menu> getSelectedMenus() {
        return (List<Menu>) getStateHelper().eval("selectedMenus");
    }

    public void setSelectedMenus(List<Menu> selectedMenus) {
        getStateHelper().put("selectedMenus", selectedMenus);
    }

    public Perfil getValue() {
        return (Perfil) getStateHelper().eval("value");
    }

    public void setValue(Perfil userInfo) {
        getStateHelper().put("value", userInfo);
    }

    public void updateValue(FacesContext context, Perfil value) {
        ValueExpression ve = this.getValueExpression("value");

        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setValue(value);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {

        super.processUpdates(context); //To change body of generated methods, choose Tools | Templates.

        List<PerfilMenu> menusPerfil = new ArrayList<>();
        for (Menu menu : getSelectedMenus()) {
            menusPerfil.add(new PerfilMenu(getValue(), menu));
        }
        getValue().setMenus(menusPerfil);

    }

}
