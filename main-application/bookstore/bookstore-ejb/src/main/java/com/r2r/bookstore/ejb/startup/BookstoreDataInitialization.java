/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.bookstore.ejb.startup;

import com.r2r.core.db.SystemConstant;
import com.r2r.core.db.entity.ColoniaLocalidad;
import com.r2r.core.db.entity.ColoniaLocalidadPK;
import com.r2r.core.db.entity.DelegacionMunicipio;
import com.r2r.core.db.entity.DelegacionMunicipioPK;
import com.r2r.core.db.entity.Direccion;
import com.r2r.core.db.entity.Estado;
import com.r2r.core.db.entity.Pais;
import com.r2r.core.db.entity.SalarioMinimo;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.UsuarioInfo;
import com.r2r.core.db.entity.UsuarioPuesto;
import com.r2r.core.db.entity.UsuarioTitulo;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.entity.navegacion.Oficina;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks and initializes the database.
 *
 * @author Alan Hdez
 */
@Singleton
@Startup
public class BookstoreDataInitialization {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookstoreDataInitialization.class);

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    private EntityManager entityManager;

    private static final String SEPARATOR = "\\|";
    private static final String FILE_PATH = "META-INF/defaultdata/";

    private Usuario userAdmin;

    private boolean dataInitialized;

    @PostConstruct
    protected void init() {
        LOGGER.info("BOOKSTORE DATA INITIALIZATION...");
//        if (!entityManager.createQuery("SELECT COUNT(t) FROM Tema t", Long.class).getSingleResult().equals(0L)) {
//            userAdmin = entityManager.find(Usuario.class, 1);
//            dataInitialized = true;
//            LOGGER.info("Database is initialized");
//            return;
//        }
        LOGGER.info("Database empty, initializing default data...");
        
////        USUARIO PUESTO
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "USUARIO_PUESTO.txt"), "UTF-8"))) {
//            String renglon;
//            String[] valores;
//            UsuarioPuesto usuarioPuesto;
//            while ((renglon = reader.readLine()) != null) {
//                valores = renglon.split(SEPARATOR);
//                usuarioPuesto = new UsuarioPuesto(Short.valueOf(valores[0]), valores[2], Status.ENABLED);
//                usuarioPuesto.setUModif(uModif);
//                entityManager.persist(usuarioPuesto);
//            }
//        } catch (IOException ex) {
//            LOGGER.error("Error loading USUARIO_PUESTO", ex);
//        } catch (NumberFormatException e) {
//            LOGGER.error("Error saving USUARIO_PUESTO", e);
//        }
//        entityManager.flush();

    }

    
    public boolean isDataInitialized() {
        return dataInitialized;
    }
}
