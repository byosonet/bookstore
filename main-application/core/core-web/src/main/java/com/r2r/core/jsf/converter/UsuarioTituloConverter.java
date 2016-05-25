/*
 * ZEITEK CONFIDENTIAL
 *
 * [2014-2015] Zeitek - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.jsf.converter;

import com.r2r.core.db.entity.UsuarioTitulo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@FacesConverter(forClass = UsuarioTitulo.class)
public class UsuarioTituloConverter extends SelectItemsConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value == null || !(value instanceof UsuarioTitulo)
                ? null : getAsString((UsuarioTitulo) value);
    }

    public String getAsString(UsuarioTitulo value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : null;
    }
}
