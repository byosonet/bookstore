/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.ejb.autor;

import com.r2r.bookstore.db.entity.Autor;
import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.exception.DatabaseException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author 
 */
@Stateless
public class AutorEjb extends EntityDaoEjb{
   
    public List<Autor> getAutoresActivos() {
        return entityManager.createQuery("SELECT a FROM Autor a WHERE a.estatus = :estatus", 
            Autor.class).setParameter("estatus", Status.ENABLED).getResultList();    
    } 

    public void deshabilitarAutor(Integer idAutor, Usuario usuarioModifica) throws DatabaseException {
        Autor autor = entityManager.getReference(Autor.class, idAutor);
        autor.setEstatus(Status.DISABLED);
        autor.setUModif(new UModif(usuarioModifica));
        entityManager.merge(autor);
    }

    public void habilitaAutor(Integer idAutor, Usuario usuarioModifica) throws DatabaseException {
        Autor autor = entityManager.getReference(Autor.class, idAutor);
        autor.setEstatus(Status.ENABLED);
        autor.setUModif(new UModif(usuarioModifica));
        entityManager.merge(autor);
    }
    
    public void agregarAutor(Autor autor, Usuario usuarioAlta) throws DatabaseException {
        autor.setUModif(new UModif(usuarioAlta));
        saveNewEntity(autor);
    }

    public void actualizarAutor(Autor autor, Usuario usuarioModifica) throws DatabaseException {
        autor.setUModif(new UModif(usuarioModifica));
        editEntity(autor);
    }
    
    public Autor getAutor(Integer idAutor) {
        try {
            return entityManager.createQuery("SELECT a FROM Autor a WHERE a.id = :id", Autor.class)
                .setParameter("id", idAutor).getSingleResult();
        } catch (NoResultException nr) {
            return null;
        }
    }
    
}
