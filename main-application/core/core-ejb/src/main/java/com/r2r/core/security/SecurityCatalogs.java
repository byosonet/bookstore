/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.security;

import com.r2r.core.db.SystemConstant;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.Visible;
import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.navegacion.MenuUrl;
import com.r2r.core.db.entity.navegacion.Sistema;
import com.r2r.core.db.entity.navegacion.SubMenu;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.entity.permisos.PerfilMenu;
import com.r2r.core.util.RecursiveMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Singleton
public class SecurityCatalogs {

    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityCatalogs.class);

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    private EntityManager entityManager;

    private final Map<Sistema, List<RecursiveMap<Menu>>> systemMenusMap = new HashMap<>();

    private final Map<Short, Menu> menusMap = new HashMap<>();

    @PostConstruct
    public void init() {
        reloadMenus();
    }

    @Lock(LockType.WRITE)
    public void reloadMenus() {
        systemMenusMap.clear();
        menusMap.clear();
        List<Menu> listMenus = entityManager.createQuery("SELECT m FROM Menu m WHERE m.estatus = :estatus ORDER BY m.orden ASC", Menu.class)
                .setParameter("estatus", Status.ENABLED)
                .getResultList();

        RecursiveMap<Menu> menuMap;
        for (Menu menu : listMenus) {
            menusMap.put(menu.getId(), menu);
            if (menu.getMenuPadre() == null && menu.getVisible() == Visible.ENABLED) {
                menuMap = new RecursiveMap();
                menuMap.put(menu, addSubmenu(menu));
                if (!systemMenusMap.containsKey(menu.getSistema())) {
                    systemMenusMap.put(menu.getSistema(), new LinkedList<RecursiveMap<Menu>>());
                }
                systemMenusMap.get(menu.getSistema()).add(menuMap);
            }
        }

    }

    public Menu getMenu(Short id) {
        return menusMap.get(id);
    }

    public Map<Sistema, List<RecursiveMap<Menu>>> getSystemMenuMap() {
        return systemMenusMap;
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, short orderMenu) {
        return addMenu(sistema, icon, nombreMenu, null, Visible.ENABLED, orderMenu, new String[]{});
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final String nombreMenuPadre, short orderMenu) {
        return addMenu(sistema, icon, nombreMenu, nombreMenuPadre, Visible.ENABLED, orderMenu, new String[]{});
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu) {
        return addMenu(sistema, icon, nombreMenu, null, Visible.ENABLED, (short) 0, new String[]{});
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, short ordenMenu, final String... urls) {
        return addMenu(sistema, icon, nombreMenu, null, Visible.ENABLED, (short) 0, urls);
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final Visible visible) {
        return addMenu(sistema, icon, nombreMenu, null, visible, (short) 0, new String[]{});
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final Visible visible, final String... urls) {
        return addMenu(sistema, icon, nombreMenu, null, visible, (short) 0, urls);
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final String nombreMenuPadre, final Visible visible, final String... urls) {
        return addMenu(sistema, icon, nombreMenu, nombreMenuPadre, visible, (short) 0, urls);
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final String nombreMenuPadre, short ordenMenu, final String... urls) {
        return addMenu(sistema, icon, nombreMenu, nombreMenuPadre, Visible.ENABLED, ordenMenu, urls);
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final String nombreMenuPadre, final Visible visible) {
        return addMenu(sistema, icon, nombreMenu, nombreMenuPadre, visible, (short) 0, new String[]{});
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final String nombreMenuPadre, final String... urls) {
        return addMenu(sistema, icon, nombreMenu, nombreMenuPadre, Visible.ENABLED, (short) 0, urls);
    }

    @Lock(LockType.WRITE)
    public Menu addMenu(final Sistema sistema, final String icon, final String nombreMenu, final String nombreMenuPadre, final Visible visible, short orderMenu, final String... urls) {
        Menu menuPadre = null;
        if (nombreMenuPadre != null && !nombreMenuPadre.isEmpty()) {
            menuPadre = getMenuByName(sistema, nombreMenuPadre);
            if (menuPadre == null) {
                menuPadre = addMenuToDatabase(sistema, nombreMenu, visible, orderMenu, urls);
            }
        }

        Menu menu = getMenuByName(sistema, nombreMenu);
        if (menu == null) {
            menu = addMenuToDatabase(sistema, icon, nombreMenu, menuPadre, visible, orderMenu, urls);
        }

        return menu;
    }

    private Menu addMenuToDatabase(final Sistema sistema, final String nombreMenu, final Visible visible, short orderMenu, final String... urls) {
        return addMenuToDatabase(sistema, null, nombreMenu, null, visible, orderMenu, urls);
    }

    private Menu addMenuToDatabase(final Sistema sistema, final String icon, final String nombreMenu, final Menu menuPadre, final Visible visible, short orderMenu, final String... urls) {
        Menu menu = new Menu();
        menu.setNombre(nombreMenu);
        menu.setSistema(sistema);

        if (orderMenu == 0) {
            Short ordenMenuTmp = entityManager.createQuery("SELECT MAX(m.orden) FROM Menu m WHERE m.sistema = :sistema", Short.class)
                    .setParameter("sistema", sistema)
                    .getSingleResult();
            orderMenu = ordenMenuTmp++;
        }
        menu.setOrden(orderMenu);

        menu.setVisible(visible);

        if (menuPadre != null) {
            menu.setMenuPadre(new SubMenu(menu, menuPadre));
        }

        if (urls != null) {
            List<MenuUrl> listUrl = new LinkedList<>();
            for (String url : urls) {
                listUrl.add(new MenuUrl(menu, url));
            }
            menu.setUrls(listUrl);
        }

        if (icon != null) {
            menu.setIcon(icon);
        }

        entityManager.persist(menu);
        entityManager.flush();

        PerfilMenu perfilMenu = new PerfilMenu();
        perfilMenu.setMenu(menu);
        perfilMenu.setPerfil(
                entityManager.createQuery(
                        "SELECT p FROM Perfil p ORDER BY p.id ASC", Perfil.class)
                .setMaxResults(1)
                .getSingleResult()
        );

        entityManager.persist(perfilMenu);

        reloadMenus();

        return menu;
    }

    private Menu getMenuByName(final Sistema sistema, final String nombreMenu) {
        Menu menu;
        try {
            menu = entityManager.createQuery("SELECT m FROM Menu m LEFT JOIN FETCH m.menuPadre WHERE m.nombre = :nombreMenu AND m.sistema = :sistema", Menu.class)
                    .setParameter("nombreMenu", nombreMenu)
                    .setParameter("sistema", sistema)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return menu;
    }

    public List<RecursiveMap<Menu>> addSubmenu(Menu menu) {
        List<RecursiveMap<Menu>> list = null;
        RecursiveMap<Menu> menuMaptemp;
        if (menu.getMenusHijos() != null) {
            list = new LinkedList<>();
            for (SubMenu menuTemp : menu.getMenusHijos()) {
                menuMaptemp = new RecursiveMap();
                menuMaptemp.put(menuTemp.getMenu(), addSubmenu(menuTemp.getMenu()));
                list.add(menuMaptemp);
            }
        }
        return list;
    }

}
