/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.components;

import com.r2r.core.db.exception.DatabaseConstraintException;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.jsf.JSFConstant;
import java.io.Serializable;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
public abstract class PageFormInterface implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(PageFormInterface.class);

    public abstract boolean doInternalAction() throws DatabaseException;

    private final String outcome;

    private final String formId;

    public PageFormInterface() {
        this(null, null);
    }

    public PageFormInterface(String outcome, String formId) {
        this.outcome = outcome;
        this.formId = formId;
    }

    public String doAction() {
        boolean result;
        try {
            result = doInternalAction();
        } catch (DatabaseConstraintException dce) {
            Messages.addError(formId + ":" + dce.getField() + ":input", dce.getErrorCode(), dce.getErrorValues());
            return null;
        } catch (DatabaseException de) {
            Messages.addFlashError(JSFConstant.MAIN_MESSAGES_GROWL, "core.errorSaved");
            LOGGER.error("Error en acción", de);
            return null;
        }
        if (!result) {
            return null;
        }
        Messages.addFlashInfo(JSFConstant.MAIN_MESSAGES_GROWL, "core.changesSaved");
        return outcome + "?" + JSFConstant.REDIRECT;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getFormId() {
        return formId;
    }

}
