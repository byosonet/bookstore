/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

import com.r2r.bookstore.db.entity.Editorial;
import com.r2r.core.address.AddressEjb;
import com.r2r.core.db.entity.ColoniaLocalidad;
import com.r2r.core.db.entity.DelegacionMunicipio;
import com.r2r.core.db.entity.Direccion;
import com.r2r.core.db.entity.Estado;
import java.io.IOException;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

/**
 *
 * @author
 */
@FacesComponent("editorialForm")
public class EditorialForm extends UINamingContainer {
    
    @Inject
    private AddressEjb addressEjb;
    
    
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);
        
        if (getValue() == null) {
            updateValue(context, new Editorial());
        }
        
        Editorial editorial = getValue();
        if (editorial.getDireccion() == null) {
            editorial.setDireccion(new Direccion());
            if (editorial.getDireccion().getEstado() == null) {
                editorial.getDireccion().setEstado(new Estado());
            }
            if (editorial.getDireccion().getColoniaLocalidad() == null) {
                editorial.getDireccion().setColoniaLocalidad(new ColoniaLocalidad());
            }
            if (editorial.getDireccion().getDelegacionMunicipio() == null) {
                editorial.getDireccion().setDelegacionMunicipio(new DelegacionMunicipio());
            }
            setValue(editorial);
        }
        
        inicializarEstados();
        inicializarLocalidades();
        inicializarDelegacionesMunicipios();        
    }

    public boolean getAltaAttr() {
        return (boolean) getAttributes().get("alta");
    }

    public Editorial getValue() {
        return (Editorial) getStateHelper().eval("value");
    }

    public void setValue(Editorial editorial) {
        getStateHelper().put("value", editorial);
    }

    public void updateValue(FacesContext context, Editorial value) {
        ValueExpression ve = this.getValueExpression("value");
        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setValue(value);
        }
    }
      
    private void inicializarEstados() {
        if (getCatalogoEstado() == null) {
            setCatalogoEstado(addressEjb.findActiveEstados());
        }
    }
    
    private void inicializarLocalidades() {
        if (getCatalogoColoniaLocalidad() == null) {
            setCatalogoColoniaLocalidad(addressEjb.findActiveColoniaLocalidad());
        }
    }

    private void inicializarDelegacionesMunicipios() {
        if (getCatalogoDelegacionMunicipio() == null) {
            setCatalogoDelegacionMunicipio(addressEjb.findActiveDelegacionMunicipio());
        }
    }
    
    public List<Estado> getCatalogoEstado() {
        return (List<Estado>) getStateHelper().get("catalogoEstado");
    }

    public List<ColoniaLocalidad> getCatalogoColoniaLocalidad() {
        return (List<ColoniaLocalidad>) getStateHelper().get("catalogoColoniaLocalidad");
    }
    
    public List<DelegacionMunicipio> getCatalogoDelegacionMunicipio() {
        return (List<DelegacionMunicipio>) getStateHelper().get("catalogoDelegacionMunicipio");
    }
    
    public void setCatalogoEstado(List<Estado> listaEstados) {
        getStateHelper().put("catalogoEstado", listaEstados);
    }
    
    public void setCatalogoColoniaLocalidad(List<ColoniaLocalidad> listaColoniasLocalidades) {
        getStateHelper().put("catalogoColoniaLocalidad", listaColoniasLocalidades);
    }
    
    public void setCatalogoDelegacionMunicipio(List<DelegacionMunicipio> listaDelegacionesMunicipios) {
        getStateHelper().put("catalogoDelegacionMunicipio", listaDelegacionesMunicipios);
    }
    
    public void handleDelegMunChange() {
        setCatalogoDelegacionMunicipio(addressEjb.findActiveDelegacionMunicipio(getValue().getDireccion().getEstado()));
    }    
    public void listenerDelegacionMunicipio(AjaxBehaviorEvent event) {
        setCatalogoDelegacionMunicipio(addressEjb.findActiveDelegacionMunicipio(getValue().getDireccion().getEstado()));
    }

    public void handleColoniaLocalidadChange() {
        setCatalogoColoniaLocalidad(addressEjb.findActiveColoniaLocalidad(getValue().getDireccion().getDelegacionMunicipio()));
    }
    public void listenerColoniaLocalidad(AjaxBehaviorEvent event) {
        setCatalogoColoniaLocalidad(addressEjb.findActiveColoniaLocalidad(getValue().getDireccion().getDelegacionMunicipio()));
    }
    
    public void handleCPChange() {
        getValue().getDireccion().setCodigoPostal(Integer.parseInt(getValue().getDireccion().getColoniaLocalidad().getCodigoPostalAsString()));
    }
    public void listenerCP(AjaxBehaviorEvent event) {
        getValue().getDireccion().setCodigoPostal(Integer.parseInt(getValue().getDireccion().getColoniaLocalidad().getCodigoPostalAsString()));
    }
    
}