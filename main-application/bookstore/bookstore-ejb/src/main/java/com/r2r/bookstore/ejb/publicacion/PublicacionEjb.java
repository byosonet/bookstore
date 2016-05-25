/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.ejb.publicacion;

import com.r2r.bookstore.db.entity.Publicacion;
import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.exception.DatabaseException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author
 */
@Stateless
public class PublicacionEjb extends EntityDaoEjb{
   
    public List<Publicacion> getPublicacionesActivas() {
        return entityManager.createQuery("SELECT a FROM Publicacion a WHERE a.estatus = :estatus", 
            Publicacion.class).setParameter("estatus", Status.ENABLED).getResultList();    
    } 

    public void deshabilitarPublicacion(Integer idPublicacion, Usuario usuarioModifica) throws DatabaseException {
        Publicacion publicacion = entityManager.getReference(Publicacion.class, idPublicacion);
        publicacion.setEstatus(Status.DISABLED);
        publicacion.setUModif(new UModif(usuarioModifica));
        entityManager.merge(publicacion);
    }

    public void habilitaPublicacion(Integer idPublicacion, Usuario usuarioModifica) throws DatabaseException {
        Publicacion publicacion = entityManager.getReference(Publicacion.class, idPublicacion);
        publicacion.setEstatus(Status.ENABLED);
        publicacion.setUModif(new UModif(usuarioModifica));
        entityManager.merge(publicacion);
    }
    
    public void agregarPublicacion(Publicacion publicacion, Usuario usuarioAlta) throws DatabaseException {
        System.err.println("PublicacionEjb - agregarPublicacion");
        publicacion.setUModif(new UModif(usuarioAlta));
        publicacion.setUsuarioAlta(usuarioAlta);
        if(publicacion.getPrecio()==null){
            publicacion.setPrecio(BigDecimal.ZERO);
        }
        if(publicacion.getDescuento()==null){
            publicacion.setDescuento(BigDecimal.ZERO);
        }        
        System.err.println("ANTES DE PERSISTIR");
        saveNewEntity(publicacion);
        System.err.println("FIN");
    }

    public void actualizarPublicacion(Publicacion publicacion, Usuario usuarioModifica) throws DatabaseException {
        publicacion.setUModif(new UModif(usuarioModifica));
        if(publicacion.getPrecio()==null){
            publicacion.setPrecio(BigDecimal.ZERO);
        }
        if(publicacion.getDescuento()==null){
            publicacion.setDescuento(BigDecimal.ZERO);
        }  
        editEntity(publicacion);
    }
    
    public Publicacion getPublicacion(Integer idPublicacion) {
        try {
            return entityManager.createQuery("SELECT a FROM Publicacion a WHERE a.id = :id", Publicacion.class)
                .setParameter("id", idPublicacion).getSingleResult();
        } catch (NoResultException nr) {
            return null;
        }
    }

}