/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.usuarios;

import com.r2r.core.components.PageFormInterface;
import com.r2r.core.db.entity.UsuarioInfo;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.session.UserSession;
import com.r2r.core.user.UserEjb;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Carlos
 */
@Named
@ViewScoped
public class UserAdd extends PageFormInterface {

    static final String OUTCOME = "/administracion/usuario/usuarios";
    static final String FORM_ID = "userForm";

    private UsuarioInfo userInfo;

    @Inject
    private UserSession userSession;

    @Inject
    private UserEjb userEjb;

    public UserAdd() {
        super(OUTCOME, FORM_ID);
    }

    public UsuarioInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UsuarioInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {
        userEjb.saveNewUsuario(userInfo, userSession.getUser());
        return true;
    }
}
