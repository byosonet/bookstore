/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.permisos;

import com.r2r.core.components.PageFormInterface;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.exception.DatabaseException;
import static com.r2r.core.permisos.PermissionAdd.FORM_ID;
import static com.r2r.core.permisos.PermissionAdd.OUTCOME;
import com.r2r.core.permission.PermissionEjb;
import com.r2r.core.security.AccessEjb;
import com.r2r.core.session.UserSession;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Named
@ViewScoped
public class PermissionEdit extends PageFormInterface {

    private Usuario usuario;

    private List<Menu> listMenus;

    @Inject
    private PermissionEjb permissionEjb;

    @Inject
    private AccessEjb accessEjb;

    @Inject
    private UserSession userSession;

    private Integer id;

    public PermissionEdit() {
        super(OUTCOME, FORM_ID);
    }

    public void init() {
        usuario = permissionEjb.getForEditSimple(Usuario.class, id);
        listMenus = accessEjb.getMenus(usuario);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Menu> getListMenus() {
        return listMenus;
    }

    public void setListMenus(List<Menu> listMenus) {
        this.listMenus = listMenus;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
//        permissionEjb.editEntity(usuario);
        return true;
    }

}
