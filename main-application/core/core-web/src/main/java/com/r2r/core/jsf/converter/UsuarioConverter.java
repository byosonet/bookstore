/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.jsf.converter;

import com.r2r.core.db.entity.Usuario;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author alanhdez
 */
@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter extends SelectItemsConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value == null || !(value instanceof Usuario)
                ? null : getAsString((Usuario) value);
    }

    public String getAsString(Usuario value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : null;
    }
}
