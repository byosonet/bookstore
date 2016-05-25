/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.ejb.tema;

import com.r2r.bookstore.db.entity.Tema;
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
public class TemaEjb extends EntityDaoEjb{
    
    public List<Tema> getTemasActivos() {
        return entityManager.createQuery("SELECT a FROM Tema a WHERE a.estatus = :estatus", 
            Tema.class).setParameter("estatus", Status.ENABLED).getResultList();    
    }
    
    public void deshabilitarTema(Integer idTema, Usuario usuarioModifica) throws DatabaseException {
        Tema tema = entityManager.getReference(Tema.class, idTema);
        tema.setEstatus(Status.DISABLED);
        tema.setUModif(new UModif(usuarioModifica));
        entityManager.merge(tema);
    }

    public void habilitaTema(Integer idTema, Usuario usuarioModifica) throws DatabaseException {
        Tema tema = entityManager.getReference(Tema.class, idTema);
        tema.setEstatus(Status.ENABLED);
        tema.setUModif(new UModif(usuarioModifica));
        entityManager.merge(tema);
    }
    
    public void agregarTema(Tema tema, Usuario usuarioAlta) throws DatabaseException {
        tema.setUModif(new UModif(usuarioAlta));
        saveNewEntity(tema);
    }

    public void actualizarTema(Tema tema, Usuario usuarioModifica) throws DatabaseException {
        tema.setUModif(new UModif(usuarioModifica));
        editEntity(tema);
    }
    
    public Tema getTema(Integer idTema) {
        try {
            return entityManager.createQuery("SELECT a FROM Tema a WHERE a.id = :id", Tema.class)
                .setParameter("id", idTema).getSingleResult();
        } catch (NoResultException nr) {
            return null;
        }
    }
    
}
