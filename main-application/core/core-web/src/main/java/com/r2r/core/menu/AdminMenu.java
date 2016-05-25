/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.menu;

import com.r2r.core.db.entity.navegacion.Menu;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Arturo
 */
@Named
@ViewScoped
public class AdminMenu implements Serializable {

    List<Menu> listMenu;
    @Inject
    private MenuEjb menuEjb;

    public AdminMenu() {
    }

    @PostConstruct
    public void init() {
        menuEjb.getListMenu();
    }

    public List<Menu> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<Menu> listMenu) {
        this.listMenu = listMenu;
    }
}
