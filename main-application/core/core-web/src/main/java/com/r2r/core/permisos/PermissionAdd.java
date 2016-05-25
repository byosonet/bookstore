/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.permisos;

import com.r2r.core.components.PageFormInterface;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.profile.ProfileEjb;
import com.r2r.core.session.UserSession;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Named
@ViewScoped
public class PermissionAdd extends PageFormInterface {

    static final String OUTCOME = "/seguridad/permiso/permisos";
    static final String FORM_ID = "permissionForm";

    private Perfil perfil;

    @Inject
    private UserSession userSession;

    @Inject
    private ProfileEjb profileEjb;

    public PermissionAdd() {
        super(OUTCOME, FORM_ID);
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        profileEjb.saveProfile(perfil, userSession.getUser());
        return true;
    }
}
