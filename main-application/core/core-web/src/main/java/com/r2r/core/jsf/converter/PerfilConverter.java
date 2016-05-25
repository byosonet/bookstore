/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.jsf.converter;

import com.r2r.core.db.entity.permisos.Perfil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@FacesConverter(forClass = Perfil.class)
public class PerfilConverter extends SelectItemsConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value == null || !(value instanceof Perfil)
                ? null : getAsString((Perfil) value);
    }

    public String getAsString(Perfil value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : null;
    }
}
