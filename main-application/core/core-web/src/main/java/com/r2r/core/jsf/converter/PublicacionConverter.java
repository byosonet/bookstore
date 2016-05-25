/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.jsf.converter;

import com.r2r.bookstore.db.entity.Publicacion;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author
 */
@FacesConverter(forClass = Publicacion.class)
public class PublicacionConverter extends SelectItemsConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value == null || !(value instanceof Publicacion)
                ? null : getAsString((Publicacion) value);
    }

    public String getAsString(Publicacion value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : null;
    }
}
