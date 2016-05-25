/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.permission.PermissionEjb;
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
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@FacesComponent("permissionForm")
public class PermissionForm extends UINamingContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionForm.class);

    @Inject
    private PermissionEjb permissionEjb;

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        initProfiles();
    }

    private void initProfiles() {
        if (getProfiles() == null) {
            setProfiles(permissionEjb.getEnabledProfiles());
        }
    }

    public Usuario getUser() {
        return (Usuario) getStateHelper().eval("user");
    }

    public void setUser(Usuario user) {
        getStateHelper().put("user", user);
    }

    public List<Perfil> getProfiles() {
        return (List<Perfil>) getStateHelper().eval("profiles");
    }

    public void setProfiles(List<Perfil> value) {
        getStateHelper().put("profiles", value);
    }

    public List<Perfil> getSelectedProfiles() {
        return (List<Perfil>) getStateHelper().eval("selectedProfiles");
    }

    public void setSelectedProfiles(List<Perfil> value) {
        getStateHelper().put("selectedProfiles", value);
    }

    public List<Menu> getUserMenuList() {
        return (List<Menu>) getStateHelper().eval("userMenuList");
    }

    public void setUserMenuList(List<Menu> value) {
        getStateHelper().put("userMenuList", value);
    }

    public void updateUserMenuList(FacesContext context, List<Menu> value) {
        ValueExpression ve = this.getValueExpression("userMenuList");

        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setUserMenuList(value);
        }
    }

    public void selectProfiles() {
        System.out.println("ajax");
        System.out.println("size " + getSelectedProfiles().size());
    }

    @Override
    public void processUpdates(FacesContext context) {

        super.processUpdates(context); //To change body of generated methods, choose Tools | Templates.

//        List<PerfilMenu> menusPerfil = new ArrayList<>();
//        for (Menu menu : getSelectedMenus()) {
//            menusPerfil.add(new PerfilMenu(getUser(), menu));
//        }
//        getUser().setMenus(menusPerfil);
    }

}
