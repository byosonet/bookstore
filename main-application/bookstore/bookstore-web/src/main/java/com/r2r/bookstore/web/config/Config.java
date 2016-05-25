/*
 * ZEITEK CONFIDENTIAL
 *
 * [2014-2015] Zeitek - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.bookstore.web.config;

import com.r2r.core.security.SecurityCatalogs;
import com.r2r.core.startup.CoreEjb;
import com.r2r.core.startup.I18nMessages;
import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@WebListener
class Config implements ServletContextListener {

    @EJB
    private CoreEjb coreEjb;

    @EJB
    private SecurityCatalogs securityCatalogs;

    @EJB
    private I18nMessages i18nMessages;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        i18nMessages.addResource("bookstore", "META-INF/i18n/BookstoreMessages", getClass().getClassLoader());
//        securityCatalogs.addMenu(coreEjb.getSystemCentral(), "fa fa-gears", "Administración", (short) 1);
//        securityCatalogs.addMenu(coreEjb.getSystemCentral(), "fa fa-users", "Usuarios", "Administración", (short) 1, "/administracion/usuario/usuarios", "/administracion/usuario/usuario-alta", "/administracion/usuario/usuario-edicion");
//        securityCatalogs.addMenu(coreEjb.getSystemCentral(), "fa fa-building-o", "Oficinas", "Administración", (short) 2, "/administracion/oficina/oficinas", "/administracion/oficina/oficina-alta", "/administracion/oficina/oficina-edicion");
//        securityCatalogs.addMenu(coreEjb.getSystemCentral(), "fa fa-key", "Seguridad", (short) 2);
//        securityCatalogs.addMenu(coreEjb.getSystemCentral(), "fa fa-briefcase", "Perfiles", "Seguridad", (short) 1, "/seguridad/perfil/perfiles", "/seguridad/perfil/perfil-alta", "/seguridad/perfil/perfil-edicion");
//        securityCatalogs.addMenu(coreEjb.getSystemCentral(), "fa fa-lock", "Permisos", "Seguridad", (short) 2, "/seguridad/permiso/permisos", "/seguridad/permiso/permiso-alta", "/seguridad/permiso/permiso-edicion");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
