/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

import com.r2r.bookstore.db.entity.Autor;
import java.io.IOException;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

/**
 *
 * @author
 */
@FacesComponent("autorForm")
public class AutorForm extends UINamingContainer {
    
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);
        
        if (getValue() == null) {
            updateValue(context, new Autor());
        }
        
        Autor autor = getValue();
        if (autor == null) {
            setValue(new Autor());
        }
        
    }

    public boolean getAltaAttr() {
        return (boolean) getAttributes().get("alta");
    }

    public Autor getValue() {
        return (Autor) getStateHelper().eval("value");
    }

    public void setValue(Autor autor) {
        getStateHelper().put("value", autor);
    }

    public void updateValue(FacesContext context, Autor value) {
        ValueExpression ve = this.getValueExpression("value");

        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setValue(value);
        }
    }
        
}
