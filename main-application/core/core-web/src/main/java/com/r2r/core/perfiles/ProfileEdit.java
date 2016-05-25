/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.perfiles;

import com.r2r.core.components.PageFormInterface;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.exception.DatabaseException;
import static com.r2r.core.perfiles.ProfileAdd.FORM_ID;
import static com.r2r.core.perfiles.ProfileAdd.OUTCOME;
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
public class ProfileEdit extends PageFormInterface {

    private Perfil perfil;

    @Inject
    private ProfileEjb profileEjb;

    @Inject
    private UserSession userSession;

    private Integer id;

    public ProfileEdit() {
        super(OUTCOME, FORM_ID);
    }

    public void init() {
        perfil = profileEjb.getForEditSimple(Perfil.class, id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        profileEjb.editProfile(perfil, userSession.getUser());
        return true;
    }

}
