/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rform;

import com.r2r.bookstore.db.entity.Autor;
import com.r2r.bookstore.db.entity.Editorial;
import com.r2r.bookstore.db.entity.Publicacion;
import com.r2r.bookstore.db.entity.Tema;
import com.r2r.bookstore.ejb.tema.TemaEjb;
import com.r2r.bookstore.ejb.autor.AutorEjb;
import com.r2r.bookstore.ejb.editorial.EditorialEjb;
import java.io.IOException;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author
 */
@FacesComponent("publicacionForm")
public class PublicacionForm extends UINamingContainer {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(PublicacionForm.class);
    
    @Inject
    private TemaEjb temaEjb;
    @Inject
    private AutorEjb autorEjb;
    @Inject
    private EditorialEjb editorialEjb;    
    
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        LOGGER.info("encodeBegin.........");
        super.encodeBegin(context);        
        if (getValue() == null) {
            updateValue(context, new Publicacion());
        }        
        Publicacion publicacion = getValue();
        if (publicacion.getAutor() == null 
                || publicacion.getEditorial() == null 
                || publicacion.getTema() == null) {
            LOGGER.info("autor, tema, publicacion.........");
            if (publicacion.getAutor()==null) {
                publicacion.setAutor(new Autor());
            }
            if (publicacion.getTema() == null) {
                publicacion.setTema(new Tema());
            }
            if (publicacion.getEditorial() == null) {
                publicacion.setEditorial(new Editorial());
            }            
            setValue(publicacion);
        }
        inicializarAutores();
        inicializarTemas();
        inicializarEditoriales();        
    }
    
    public boolean getAltaAttr() {
        return (boolean) getAttributes().get("alta");
    }

    public Publicacion getValue() {
        return (Publicacion) getStateHelper().eval("value");
    }

    public void setValue(Publicacion publicacion) {
        getStateHelper().put("value", publicacion);
    }

    public void updateValue(FacesContext context, Publicacion value) {
        ValueExpression ve = this.getValueExpression("value");
        if (ve != null) {
            ve.setValue(context.getELContext(), value);
        } else {
            this.setValue(value);
        }
    }
      
    private void inicializarAutores() {
        if (getCatalogoAutor() == null) {
            setCatalogoAutor(autorEjb.getAutoresActivos());
        }
    }
    
    private void inicializarTemas() {
        if (getCatalogoTema() == null) {
            setCatalogoTema(temaEjb.getTemasActivos());
        }
    }

    private void inicializarEditoriales() {
        if (getCatalogoEditorial() == null) {
            setCatalogoEditorial(editorialEjb.getEditorialesActivas());
        }
    }

    public List<Autor> getCatalogoAutor() {
        return (List<Autor>) getStateHelper().get("catalogoAutor");
    }
    public void setCatalogoAutor(List<Autor> listaAutores) {
        getStateHelper().put("catalogoAutor", listaAutores);
    }    
    
    public List<Tema> getCatalogoTema() {
        return (List<Tema>) getStateHelper().get("catalogoTema");
    }
    public void setCatalogoTema(List<Tema> listaTemas) {
        getStateHelper().put("catalogoTema", listaTemas);
    }

    public List<Editorial> getCatalogoEditorial() {
        return (List<Editorial>) getStateHelper().get("catalogoEditorial");
    }
    public void setCatalogoEditorial(List<Editorial> listaEditoriales) {
        getStateHelper().put("catalogoEditorial", listaEditoriales);
    }
    
    public UploadedFile getFile() {
        return (UploadedFile) getStateHelper().eval("file");
    }

    public void setFile(UploadedFile file) {
        getStateHelper().put("file", file);
    }
    
    private void savePortada(UploadedFile file) {
        if (file != null) {
            getValue().setPortada(file.getContents());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        LOGGER.info("handleFileUpload.........");
        savePortada(event.getFile());
    }
    
}
