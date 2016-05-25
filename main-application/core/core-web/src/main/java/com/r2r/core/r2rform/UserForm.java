/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.UsuarioInfo;
import com.r2r.core.db.entity.UsuarioPuesto;
import com.r2r.core.db.entity.UsuarioTitulo;
import com.r2r.core.user.UserEjb;
import java.io.IOException;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Carlos
 */
@FacesComponent("userForm")
public class UserForm extends UINamingContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserForm.class);

    @Inject
    private UserEjb userEjb;

    public UploadedFile getFile() {
        return (UploadedFile) getStateHelper().eval("file");
    }

    public void setFile(UploadedFile file) {
        getStateHelper().put("file", file);
    }

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        if (getValue() == null) {
            updateValue(context, new UsuarioInfo());
        }

        UsuarioInfo userInfo = getValue();
        if (userInfo.getUsuario() == null) {
            userInfo.setUsuario(new Usuario());
            setValue(userInfo);
        }

        if (getChangePass() == null) {
            setChangePass(!getAltaAttr());
        }

        initUserRanks();
        initUserTitles();
    }

    private void initUserTitles() {
        if (getUserTitles() == null) {
            setUserTitles(userEjb.getUserTitles());
        }
    }

    private void initUserRanks() {
        if (getUserRanks() == null) {
            setUserRanks(userEjb.getUserRanks());
        }
    }

    public String getPasswordAgain() {
        return (String) getStateHelper().get("passwordAgain");
    }

    public void setPasswordAgain(String passwordAgain) {
        getStateHelper().put("passwordAgain", passwordAgain);
    }

    public Boolean getChangePass() {
        try {
            return (Boolean) getStateHelper().get("changePass");
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void setChangePass(boolean changePass) {
        getStateHelper().put("changePass", changePass);
    }

    public boolean getAltaAttr() {
        return (boolean) getAttributes().get("alta");
    }

    public UsuarioInfo getValue() {
        return (UsuarioInfo) getStateHelper().eval("value");
    }

    public void setValue(UsuarioInfo userInfo) {
        getStateHelper().put("value", userInfo);
    }

    public void updateValue(FacesContext context, UsuarioInfo value) {
        ValueExpression ve = this.getValueExpression("value");

        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setValue(value);
        }
    }

    public List<UsuarioPuesto> getUserRanks() {
        return (List<UsuarioPuesto>) getStateHelper().get("userRanks");
    }

    public void setUserRanks(List<UsuarioPuesto> userRanks) {
        getStateHelper().put("userRanks", userRanks);
    }

    public List<UsuarioTitulo> getUserTitles() {
        return (List<UsuarioTitulo>) getStateHelper().get("userTitles");
    }

    public void setUserTitles(List<UsuarioTitulo> userTitles) {
        getStateHelper().put("userTitles", userTitles);
    }

    public void changePassword() {
        setChangePass(false);
    }

    public void cancelChangePassword() {
        setChangePass(true);
    }

    private void saveFoto(UploadedFile file) {
        if (file != null) {
            getValue().getUsuario().setFoto(file.getContents());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        saveFoto(event.getFile());
    }

}
