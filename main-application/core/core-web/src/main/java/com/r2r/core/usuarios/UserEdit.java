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
import static com.r2r.core.usuarios.UserAdd.FORM_ID;
import static com.r2r.core.usuarios.UserAdd.OUTCOME;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Carlos
 */
@Named
@ViewScoped
public class UserEdit extends PageFormInterface {

    private UsuarioInfo userInfo;

    @Inject
    private UserEjb userEjb;

    @Inject
    private UserSession userSession;

    private Integer id;

    public UserEdit() {
        super(OUTCOME, FORM_ID);
    }

    public void init() {
        userInfo = userEjb.getUserForEdit(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UsuarioInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean doInternalAction() throws DatabaseException {

        userEjb.editUsuario(userInfo, userSession.getUser());
        return true;
    }

}
