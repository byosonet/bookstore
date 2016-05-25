/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.security;

import com.r2r.core.db.SystemConstant;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.navegacion.MenuUrl;
import com.r2r.core.db.entity.navegacion.Oficina;
import com.r2r.core.db.entity.permisos.UsuarioMenu;
import com.r2r.core.db.entity.permisos.UsuarioOficina;
import com.r2r.core.office.OfficeEjb;
import com.r2r.core.startup.CoreEjb;
import com.r2r.core.util.RecursiveMap;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Stateless
public class AccessEjb implements Serializable {

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    private EntityManager entityManager;

    @EJB
    private CoreEjb coreEjb;

    @EJB
    private OfficeEjb officeEjb;

    @EJB
    private SecurityCatalogs securityCatalogs;

    private final static Logger LOGGER = LoggerFactory.getLogger(AccessEjb.class);

    /**
     * Check if the user and password are in the application and return a list
     * of systems where the user have access.
     *
     * @param userName The user name
     * @param password The password
     * @return A list of systems where the user have access, null if the user
     * does not exist or if the password do not match
     */
    public UserLoggedInfo login(final String userName, final String password) {
        Usuario user;
        try {
            user = entityManager.createQuery("SELECT NEW com.r2r.core.db.entity.Usuario(s.id, s.nombre, s.APaterno, s.AMaterno, s.password, s.foto) "
                    + "FROM Usuario s WHERE s.login = :userName AND s.estatus = :estatus", Usuario.class)
                    .setParameter("userName", userName)
                    .setParameter("estatus", Status.ENABLED)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        try {
            if (!new BasicPasswordEncryptor().checkPassword(password, user.getPassword())) {
                return null;
            }
        } catch (EncryptionOperationNotPossibleException eonpe) {
            LOGGER.error("Error decrypting password", eonpe);
            return null;
        }

        List<Menu> listMenus = getMenus(user);
        List<String> listUrls = new LinkedList<>();
        for (Menu menu : listMenus) {
            if (menu.getUrls() != null) {
                for (MenuUrl menuUrl : menu.getUrls()) {
                    listUrls.add(menuUrl.getUrl());
                }
            }
        }

        List<Oficina> listOficinas = getOficinas(user);

        return new UserLoggedInfo(user, listUrls, listMenus, listOficinas, password);
    }

    private void removeMenus(List<RecursiveMap<Menu>> listMap, List<Menu> listMenus) {

        for (RecursiveMap<Menu> map : listMap) {
            for (Map.Entry<Menu, List<RecursiveMap<Menu>>> entrySet : map.entrySet()) {
                if (!listMenus.contains(entrySet.getKey())) {
                    map.remove(entrySet.getKey());
                } else {
                    if (entrySet.getValue() != null) {
                        removeMenus(entrySet.getValue(), listMenus);
                    }
                }
            }
        }
    }

    public List<Menu> getMenus(Usuario user) {
        List<Short> listMenuPerfil = entityManager.createQuery("SELECT pm.id.idMenu FROM UsuarioPerfil p JOIN FETCH p.perfil pe JOIN FETCH pe.menus pm WHERE p.usuario = :user", Short.class)
                .setParameter("user", user)
                .getResultList();
        List<UsuarioMenu> listUsuarioMenu = entityManager.createQuery("SELECT new com.r2r.core.db.entity.permisos.UsuarioMenu(u.id, u.estatus) FROM UsuarioMenu u WHERE u.usuario = :user", UsuarioMenu.class)
                .setParameter("user", user)
                .getResultList();

        for (UsuarioMenu usuarioMenu : listUsuarioMenu) {
            if (listMenuPerfil.contains(usuarioMenu.getId().getIdMenu())) {
                if (usuarioMenu.getEstatus() == Status.DISABLED) {
                    listMenuPerfil.remove(usuarioMenu.getMenu().getId());
                }
            } else {
                listMenuPerfil.add(usuarioMenu.getMenu().getId());
            }
        }

        List<Menu> listMenu = new LinkedList<>();

        for (Short id : listMenuPerfil) {
            listMenu.add(securityCatalogs.getMenu(id));
        }

        return listMenu;
    }

    private List<Oficina> getOficinas(Usuario user) {
        List<Integer> listOficinaPerfil = entityManager.createQuery("SELECT po.id.idOficina FROM UsuarioPerfil p JOIN FETCH p.perfil pe JOIN FETCH pe.oficinas po WHERE p.usuario = :user", Integer.class)
                .setParameter("user", user)
                .getResultList();
        List<UsuarioOficina> listUsuarioOficina = entityManager.createQuery("SELECT new com.r2r.core.db.entity.permisos.UsuarioOficina(u.id, u.estatus) FROM UsuarioOficina u WHERE u.usuario = :user", UsuarioOficina.class)
                .setParameter("user", user)
                .getResultList();

        for (UsuarioOficina usuarioOficina : listUsuarioOficina) {
            if (listOficinaPerfil.contains(usuarioOficina.getId().getIdOficina())) {
                if (usuarioOficina.getEstatus() == Status.DISABLED) {
                    listOficinaPerfil.remove(usuarioOficina.getOficina().getId());
                }
            } else {
                listOficinaPerfil.add(usuarioOficina.getOficina().getId());
            }
        }

        List<Oficina> listOficinas = new LinkedList<>();
        for (Integer id : listOficinaPerfil) {
            listOficinas.add(officeEjb.getOficina(id));
        }

        return listOficinas;
    }

    public class UserLoggedInfo {

        private final Usuario user;

        private final List<String> urlsFilter;

        private final List<Menu> listMenus;

        private final List<Oficina> oficinas;

        private final String nombrePerfil;

        public UserLoggedInfo(Usuario user, List<String> urlsFilter, List<Menu> listMenus, List<Oficina> oficinas, String nombrePerfil) {
            this.user = user;
            this.urlsFilter = urlsFilter;
            this.listMenus = listMenus;
            this.oficinas = oficinas;
            this.nombrePerfil = nombrePerfil;
        }

        public Usuario getUser() {
            return user;
        }

        public List<String> getUrlsFilter() {
            return urlsFilter;
        }

        public List<Menu> getListMenus() {
            return listMenus;
        }

        public List<Oficina> getOficinas() {
            return oficinas;
        }

        public String getNombrePerfil() {
            return nombrePerfil;
        }

    }
}
