/*
 * ZEITEK CONFIDENTIAL
 *
 * [2014-2015] Zeitek - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.jsf.converter;

import com.r2r.core.db.entity.DelegacionMunicipio;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@FacesConverter(forClass = DelegacionMunicipio.class)
public class DelegacionMunicipioConverter extends SelectItemsConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value == null || !(value instanceof DelegacionMunicipio)
                ? null : getAsString((DelegacionMunicipio) value);
    }

    public String getAsString(DelegacionMunicipio value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : null;
    }
}
