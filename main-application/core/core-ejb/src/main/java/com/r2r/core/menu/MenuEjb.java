/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.menu;

import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.navegacion.Menu;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arturo
 */
@Stateless
public class MenuEjb extends EntityDaoEjb {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MenuEjb.class);
    private List<Menu> listMenu;

    public List<Menu> getListMenu() {
        try {
            listMenu = entityManager.createQuery("SELECT m FROM Menu m WHERE m.estatus =:estatus", Menu.class)
                    .setParameter("estatus", Status.ENABLED)
                    .getResultList();
        } catch (NoResultException nre) {
            Logger.getLogger(MenuEjb.class.getName()).log(Level.SEVERE, null, nre);
            //LOGGER.error("Failed to insert/update user data from Google.: " + nre);
            return null;
        }
        return listMenu;
    }
}
