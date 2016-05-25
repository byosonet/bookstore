/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.startup;

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
public class CoreDataInitialization {

    private final static Logger LOGGER = LoggerFactory.getLogger(CoreDataInitialization.class);

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    private EntityManager entityManager;

    private static final String SEPARATOR = "\\|";
    private static final String FILE_PATH = "META-INF/defaultdata/";

    private Usuario userAdmin;

    private Oficina office;

    private boolean dataInitialized;

    @PostConstruct
    protected void init() {
        LOGGER.info("Checking database...");
        if (!entityManager.createQuery("SELECT COUNT(u) FROM Usuario u", Long.class).getSingleResult().equals(0L)) {
            userAdmin = entityManager.find(Usuario.class, 1);
            office = entityManager.find(Oficina.class, 1);
            dataInitialized = true;
            LOGGER.info("Database is initialized");
            return;
        }
        LOGGER.info("Database empty, initializing default data...");
        userAdmin = new Usuario();
        userAdmin.setLogin("admin");
        userAdmin.setPassword("admin");
        userAdmin.setAMaterno("ADMIN");
        userAdmin.setAPaterno("ADMIN");
        userAdmin.setNombre("ADMIN");
        entityManager.persist(userAdmin);
        entityManager.flush();

        UModif uModif = new UModif(userAdmin, LocalDateTime.now());

//        USUARIO PUESTO
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "USUARIO_PUESTO.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            UsuarioPuesto usuarioPuesto;
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                usuarioPuesto = new UsuarioPuesto(Short.valueOf(valores[0]), valores[2], Status.ENABLED);
                usuarioPuesto.setUModif(uModif);
                entityManager.persist(usuarioPuesto);
            }
        } catch (IOException ex) {
            LOGGER.error("Error loading USUARIO_PUESTO", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving USUARIO_PUESTO", e);
        }
        entityManager.flush();

//        usuarioTitulo
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "USUARIO_TITULO.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            UsuarioTitulo usuarioTitulo;
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                usuarioTitulo = new UsuarioTitulo();
                usuarioTitulo.setId(new Short(valores[0]));
                usuarioTitulo.setNombre(valores[3]);
                usuarioTitulo.setAbreviatura(valores[1]);
                usuarioTitulo.setEstatus(Status.ENABLED);
                usuarioTitulo.setUModif(uModif);

                entityManager.persist(usuarioTitulo);
            }
        } catch (IOException ex) {
            LOGGER.error("Error loading USUARIO_TITULO", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving USUARIO_TITULO", e);
        }
        entityManager.flush();

//        USUARIO
        UsuarioInfo usuarioInfo = new UsuarioInfo();
        usuarioInfo.setUsuario(userAdmin);
        usuarioInfo.setUModif(uModif);
        usuarioInfo.setUsuarioPuesto(new UsuarioPuesto((short) 1));
        usuarioInfo.setUsuarioTitulo(new UsuarioTitulo((short) 1));
        entityManager.persist(usuarioInfo);
        entityManager.flush();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "USUARIO.txt"), "UTF-8"))) {
//            String renglon;
//            String[] valores;
//            Usuario admin = null;
//            while ((renglon = reader.readLine()) != null) {
//                admin = new Usuario();
//                valores = renglon.split(SEPARATOR);
//                uModif.setUsuarioUModif(admin);
//                admin.setId(Integer.valueOf(valores[0]));
//                admin.setLogin(valores[7]);
//                admin.setNombre(valores[8]);
//                admin.setAPaterno(valores[2]);
//                admin.setAMaterno(valores[1]);
//                admin.setPassword(valores[7]);
//                admin.setEstatus(Status.ENABLED);
//                admin.setUModif(uModif);
//                admin.setFechaAlta(LocalDateTime.now());
//                admin.setUsuarioPuesto(new UsuarioPuesto(new Short(valores[13])));
//                admin.setUsuarioTitulo(new UsuarioTitulo(new Short(valores[14])));
//                entityManager.persist(admin);
//            }
//            userAdmin = admin;
//        } catch (IOException ex) {
//            LOGGER.error("Error loading USUARIOS", ex);
//        } catch (NumberFormatException e) {
//            LOGGER.error("Error saving USUARIOS", e);
//        }
//        entityManager.flush();
        //PAISES 1
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "PAIS.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            Pais pais;
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                pais = new Pais(new Short(valores[0]), valores[4], valores[1], valores[2], uModif);
                pais.setEstatus(Status.ENABLED);
                entityManager.persist(pais);
            }
        } catch (IOException ex) {
            LOGGER.error("Error loading paises", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving paises", e);
        }
        entityManager.flush();

        //ESTADOS 3
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "ESTADO.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            Estado estado;
            Short idPais;
            Pais country = new Pais((short) 0);
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                idPais = new Short(valores[1]);
                if (!country.getId().equals(idPais)) {
                    country = new Pais(idPais);
                }
                estado = new Estado(new Short(valores[0]), valores[3], country, uModif);
                estado.setEstatus(Status.ENABLED);
                entityManager.persist(estado);
            }
        } catch (IOException ex) {
            LOGGER.error("Error loading estado", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving estado", e);
        }
        entityManager.flush();

        //CAT_DELEGACION_MUNICIPIO 4
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "DELEGACION_MUNICIPIO.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            DelegacionMunicipio delegacionMunicipio;
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                delegacionMunicipio = new DelegacionMunicipio(new DelegacionMunicipioPK(new Short(valores[0]), new Integer(valores[1])));
                delegacionMunicipio.setAbreviatura(valores[3]);
                delegacionMunicipio.setNombre(valores[2]);
                delegacionMunicipio.setUModif(uModif);
                entityManager.persist(delegacionMunicipio);
            }
        } catch (IOException ex) {
            LOGGER.error("Error loading DELEGACION_MUNICIPIO", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving DELEGACION_MUNICIPIO", e);
        }
        entityManager.flush();
        //CAT_COLONIA_LOCALIDAD 5
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "COLONIA_LOCALIDAD.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            ColoniaLocalidad catColoniaLocalidad;
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                catColoniaLocalidad = new ColoniaLocalidad(new ColoniaLocalidadPK(new Short(valores[6]), new Integer(valores[7]), new Integer(valores[5])));
                catColoniaLocalidad.setNombre(valores[2]);
                catColoniaLocalidad.setCodigoPostal(new Integer(valores[0]));
                catColoniaLocalidad.setUModif(uModif);
                entityManager.persist(catColoniaLocalidad);
            }
        } catch (IOException ex) {
            LOGGER.error("Error loading Colonia Localidad", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving Colonia Localidad", e);
        }
        entityManager.flush();

        //DIRECCIONES 6
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "DIRECCION.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            Direccion direccion;
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                direccion = new Direccion(Long.valueOf(valores[0]), valores[2], valores[6], new Integer(valores[3]), LocalDateTime.now());
                direccion.setNumeroInt(valores[7]);
                direccion.setReferencia(valores[8]);
                direccion.setUModif(uModif);
                direccion.setColoniaLocalidad(entityManager.getReference(ColoniaLocalidad.class, new ColoniaLocalidadPK(new Short(valores[11]), new Integer(valores[13]), new Integer(valores[12]))));

                entityManager.persist(direccion);
            }
        } catch (IOException ex) {
            LOGGER.error("Error loading las Direcciones", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving las Direcciones", e);
        }
        entityManager.flush();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(FILE_PATH + "OFICINA.txt"), "UTF-8"))) {
            String renglon;
            String[] valores;
            Oficina oficina = null;
            while ((renglon = reader.readLine()) != null) {
                valores = renglon.split(SEPARATOR);
                oficina = new Oficina(new Integer(valores[0]),
                        valores[1],
                        valores[2],
                        entityManager.getReference(Usuario.class, new Integer(valores[3])),
                        entityManager.getReference(Direccion.class, new Long(valores[4])));
                oficina.setClave(valores[5]);
                oficina.setEstatus(Status.ENABLED);
                oficina.setTelefono(valores[6]);
                oficina.setUModif(uModif);
                oficina.getDireccion().setUModif(uModif);
                entityManager.persist(oficina);
                entityManager.flush();
            }
            office = oficina;
        } catch (IOException ex) {
            LOGGER.error("Error loading OFICINA", ex);
        } catch (NumberFormatException e) {
            LOGGER.error("Error saving OFICINA", e);
        }

        SalarioMinimo salarioMinimo = new SalarioMinimo();
        salarioMinimo.setFechaAplicacion(new LocalDateTime(2014, 1, 1, 0, 0));
        salarioMinimo.setUModif(uModif);
        salarioMinimo.setCantidad(new BigDecimal("63.77"));
        salarioMinimo.setVersion(1L);
        entityManager.persist(salarioMinimo);
    }

    public Usuario getUserAdmin() {
        return userAdmin;
    }

    public Oficina getOffice() {
        return office;
    }

    public boolean isDataInitialized() {
        return dataInitialized;
    }
}
