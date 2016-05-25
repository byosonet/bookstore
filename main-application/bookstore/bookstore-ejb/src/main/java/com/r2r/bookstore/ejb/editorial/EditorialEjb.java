/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.ejb.editorial;

import com.r2r.bookstore.db.entity.Editorial;
import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.exception.DatabaseException;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author
 */
@PermitAll
@Stateless
public class EditorialEjb extends EntityDaoEjb{
    
    public List<Editorial> getEditorialesActivas() {
        return entityManager.createQuery("SELECT a FROM Editorial a JOIN FETCH a.direccion WHERE a.estatus = :estatus", 
            Editorial.class).setParameter("estatus", Status.ENABLED).getResultList();    
    }
    
    public void deshabilitarEditorial(Integer idEditorial, Usuario usuarioModifica) throws DatabaseException {
        Editorial editorial = entityManager.getReference(Editorial.class, idEditorial);
        editorial.setEstatus(Status.DISABLED);
        editorial.setUModif(new UModif(usuarioModifica));
        entityManager.merge(editorial);
    }

    public void habilitaEditorial(Integer idEditorial, Usuario usuarioModifica) throws DatabaseException {
        Editorial editorial = entityManager.getReference(Editorial.class, idEditorial);
        editorial.setEstatus(Status.ENABLED);
        editorial.setUModif(new UModif(usuarioModifica));
        entityManager.merge(editorial);
    }
    
    public void agregarEditorial(Editorial editorial, Usuario usuarioAlta) throws DatabaseException {
        editorial.setUModif(new UModif(usuarioAlta));
        editorial.getDireccion().setUModif(new UModif(usuarioAlta));
        
        saveNewEntity(editorial.getDireccion());
        saveNewEntity(editorial);
    }

    public void actualizarEditorial(Editorial editorial, Usuario usuarioModifica) throws DatabaseException {
        editorial.setUModif(new UModif(usuarioModifica));
        editorial.getDireccion().setUModif(new UModif(usuarioModifica));
        
        editEntity(editorial.getDireccion());
        editEntity(editorial);
    }
    
    public Editorial getEditorial(Integer idEditorial) {
        try {
            return entityManager.createQuery("SELECT a FROM Editorial a JOIN FETCH a.direccion WHERE a.id = :id", Editorial.class)
                .setParameter("id", idEditorial).getSingleResult();
        } catch (NoResultException nr) {
            return null;
        }
    }
    
}
