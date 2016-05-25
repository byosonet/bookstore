/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.startup;

import com.r2r.core.common.Config;
import com.r2r.core.db.SystemConstant;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.entity.navegacion.Sistema;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.entity.permisos.PerfilOficina;
import com.r2r.core.db.entity.permisos.UsuarioPerfil;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Hdez
 */
@Singleton
@Startup
public class CoreEjb implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(CoreEjb.class);

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    private EntityManager entityManager;

    private final Map<Short, Sistema> systemsMap = new HashMap<>();

    @EJB
    private CoreDataInitialization coreDataInitialization;

    private Sistema systemCentral;

    @PostConstruct
    protected void init() {
        systemCentral = addSystem(Config.getStringProperty(Config.DEFAULT_SYSTEM_NAME));
    }

    @Lock(LockType.WRITE)
    public Sistema addSystem(final String nombre) {
        Sistema system = getSystemByName(nombre);
        if (system == null) {
            system = addSystemToDatabase(nombre);
        }
        systemsMap.put(system.getId(), system);
        return system;
    }

    public Sistema getSystem(Short id) {
        return systemsMap.get(id);
    }

    public Collection<Sistema> getSystems() {
        return systemsMap.values();
    }

    public Sistema getSystemCentral() {
        return systemCentral;
    }

    private Sistema getSystemByName(String nombre) {
        try {
            return entityManager.createQuery("SELECT s FROM Sistema s WHERE s.nombre = :nombre", Sistema.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    private Sistema addSystemToDatabase(String nombre) {
        // First add the system
        Sistema sistema = new Sistema((short) getSystems().size(), nombre);
        LOGGER.debug("Adding system " + sistema.getNombre());
        sistema.setEstatus(Status.ENABLED);
        entityManager.persist(sistema);

        addAdminPerfil(sistema);
        return sistema;
    }

    public Sistema getSistema(String name) {
        return getSystemByName(name);
    }

    public void addAdminPerfil(Sistema sistema) {
        // Then a default admin perfil is created
        Perfil perfil = new Perfil();
        perfil.setNombre(MessageFormat.format(Config.getStringProperty(Config.DEFAULT_PERFIL_NAME), sistema.getNombre()));
        perfil.setEstatus(Status.ENABLED);
        perfil.setUModif(new UModif());
        perfil.getUModif().setUsuarioUModif(coreDataInitialization.getUserAdmin());
        entityManager.persist(perfil);
        entityManager.flush();

        UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
        usuarioPerfil.setUsuario(coreDataInitialization.getUserAdmin());
        usuarioPerfil.setPerfil(perfil);
        entityManager.persist(usuarioPerfil);
        entityManager.flush();

        PerfilOficina perfilOficina = new PerfilOficina();
        perfilOficina.setOficina(coreDataInitialization.getOffice());
        perfilOficina.setPerfil(perfil);
        entityManager.persist(perfilOficina);
        entityManager.flush();
    }
}
