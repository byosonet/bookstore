/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.google.signin;

import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.UsuarioPuesto;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.startup.CoreDataInitialization;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Arturo
 */
@Singleton
public class PuestoEJB extends EntityDaoEjb {

    @Inject
    private CoreDataInitialization cdi;

    public UsuarioPuesto putGetUsuarioGoogle() {
        UsuarioPuesto puesto = null;
        try {

            puesto = entityManager.createQuery("SELECT u FROM UsuarioPuesto u WHERE u.nombre =:nombre", UsuarioPuesto.class)
                    .setParameter("nombre", "usuario google").getSingleResult();
        } catch (NoResultException nre) {
            puesto = new UsuarioPuesto();
            try {
                UModif uModif = new UModif();
                uModif.setFechaUModif(LocalDateTime.now());
                uModif.setUsuarioUModif(cdi.getUserAdmin());
                puesto.setEstatus(Status.ENABLED);
                puesto.setNombre("usuario google");
                puesto.setUModif(uModif);
                saveNewEntity(puesto);
                puesto = entityManager.createQuery("SELECT u FROM UsuarioPuesto u WHERE u.nombre =:nombre", UsuarioPuesto.class)
                        .setParameter("nombre", "usuario google").getSingleResult();
            } catch (DatabaseException ex) {
                Logger.getLogger(PuestoEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return puesto;
    }
}
