/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rcomp;

import java.io.IOException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alan Hdez
 */
@FacesComponent("input")
public class Input extends UINamingContainer {

    // Fields -------------------------------------------------------------------------------------
    private UIInput input;

    /**
     * Returns the component family of {@link UINamingContainer}. (that's just
     * required by composite component)
     *
     * @return
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    /**
     * Set the selected and available values of the day, month and year fields
     * based on the model.
     *
     * @param context
     * @throws java.io.IOException
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);
    }

    public UIInput getInput() {
        return input;
    }

    public void setInput(UIInput input) {
        this.input = input;
    }

}
