/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

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
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Carlos
 */
@FacesComponent("addressForm")
public class addressForm extends UINamingContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserForm.class);

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
            updateValue(context, new Direccion());
        }
        initListEstados();
        //initListColoniaLocalidad();
        //initListDelegacionMunicipio();
    }

    public void handleDelegMunChange() {
        System.out.println("_________________________________________________________________________________________");
        setAddressDelegacionMunicipio(addressEjb.findActiveDelegacionMunicipio(getValue().getEstado()));
    }

    public void handleColoniaLocalidadChange() {
        setAddressColoniaLocalidad(addressEjb.findActiveColoniaLocalidad(getValue().getDelegacionMunicipio()));
    }

    public void handleCPChange() {
        getValue().setCodigoPostal(Integer.parseInt(getValue().getColoniaLocalidad().getCodigoPostalAsString()));
    }

    private void initListEstados() {
        if (getAddressEstado() == null) {
            setAddressEstado(addressEjb.findActiveEstados());
        }
    }

    private void initListColoniaLocalidad() {
        if (getAddressColoniaLocalidad() == null) {
            setAddressColoniaLocalidad(addressEjb.findActiveColoniaLocalidad());
        }
    }

    private void initListDelegacionMunicipio() {
        if (getAddressDelegacionMunicipio() == null) {
            setAddressDelegacionMunicipio(addressEjb.findActiveDelegacionMunicipio());
        }
    }

    public List<Estado> getAddressEstado() {
        return (List<Estado>) getStateHelper().get("addressEstado");
    }

    public List<ColoniaLocalidad> getAddressColoniaLocalidad() {
        return (List<ColoniaLocalidad>) getStateHelper().get("addressColoniaLocalidad");
    }

    public List<DelegacionMunicipio> getAddressDelegacionMunicipio() {
        return (List<DelegacionMunicipio>) getStateHelper().get("addressDelegacionMunicipio");
    }

    public void setAddressEstado(List<Estado> estado) {
        getStateHelper().put("addressEstado", estado);
    }

    public void setAddressColoniaLocalidad(List<ColoniaLocalidad> coloniaLocalidad) {
        getStateHelper().put("addressColoniaLocalidad", coloniaLocalidad);
    }

    public void setAddressDelegacionMunicipio(List<DelegacionMunicipio> delegacionMunicipio) {
        getStateHelper().put("addressDelegacionMunicipio", delegacionMunicipio);
    }

    public Direccion getValue() {
        return (Direccion) getStateHelper().eval("value");
    }

    public void setValue(Direccion direccion) {
        getStateHelper().put("value", direccion);
    }

    public void updateValue(FacesContext context, Direccion value) {
        ValueExpression ve = this.getValueExpression("value");

        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setValue(value);
        }
    }
}
