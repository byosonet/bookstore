/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rcomp;

import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.navegacion.MenuUrl;
import com.r2r.core.db.entity.navegacion.Sistema;
import com.r2r.core.security.SecurityCatalogs;
import com.r2r.core.session.UiSession;
import com.r2r.core.util.RecursiveMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Alan Hdez
 */
@FacesComponent("menuBar")
public class MenuBar extends UINamingContainer {

    @Inject
    private UiSession uiSession;

    @Inject
    private SecurityCatalogs securityCatalogs;

    private String servletPath;

    /**
     * Returns the component family of {@link UINamingContainer}. (that's just
     * required by composite component)
     *
     * @return
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    /**
     * Set the selected and available values of the day, month and year fields
     * based on the model.
     *
     * @param context
     * @throws java.io.IOException
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        List<Menu> listMenus = uiSession.getListMenus();

        if (listMenus == null) {
            super.encodeBegin(context);
            return;
        }

        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        servletPath = origRequest.getServletPath();

//        if (uiSession.getCodeMenu() == null) {
        uiSession.setCodeMenu(generateMenu(listMenus).toString());
//        }

        ResponseWriter writer = context.getResponseWriter();
//        writer.startElement("ul", null);
//        writer.writeAttribute("class", writer, null);
//        writer.endElement("ul");
        writer.write(uiSession.getCodeMenu());
        super.encodeBegin(context);
    }

    private StringBuilder generateMenu(List<Menu> listMenus) {

        StringBuilder builder = new StringBuilder();
        builder.append("<ul class=\"sidebar-menu\">");
        builder.append("\n");
        for (Map.Entry<Sistema, List<RecursiveMap<Menu>>> entrySet : securityCatalogs.getSystemMenuMap().entrySet()) {
            boolean existsSystem = false;
            for (Menu menu : listMenus) {
                if (menu.getSistema().equals(entrySet.getKey())) {
                    existsSystem = true;
                    break;
                }
            }
            if (!existsSystem) {
                continue;
            }
            builder.append("<li class=\"header\">");
            builder.append("\n");
            builder.append(entrySet.getKey().getNombre());
            builder.append("</li>");
            builder.append("\n");

            for (RecursiveMap<Menu> map : entrySet.getValue()) {
                for (Map.Entry<Menu, List<RecursiveMap<Menu>>> entrySetMenu : map.entrySet()) {
                    if (!listMenus.contains(entrySetMenu.getKey())) {
                        continue;
                    }
                    builder.append("<li class=\"treeview");
                    if (activateMenuRecursive(entrySetMenu.getValue())) {
                        builder.append(" active");
                    }
                    builder.append("\">");
                    builder.append("\n");
                    builder.append("<a href=\"");

                    if (entrySetMenu.getKey().getUrl() == null) {
                        builder.append("#");
                    } else {
                        builder.append(entrySetMenu.getKey().getUrl());
                    }
                    builder.append("\">");
                    builder.append("\n");
                    if (entrySetMenu.getKey().getIcon() != null) {
                        builder.append("<i class=\"");
                        builder.append(entrySetMenu.getKey().getIcon());
                        builder.append("\"></i>");
                        builder.append("\n");
                    }

                    builder.append("<span>");
                    builder.append("\n");
                    builder.append(entrySetMenu.getKey().getNombre());
                    builder.append("\n");
                    builder.append("</span>");
                    builder.append("\n");
                    if (entrySetMenu.getValue() != null && !entrySetMenu.getValue().isEmpty()) {
                        builder.append("<i class=\"fa fa-angle-left pull-right\"></i>");
                        builder.append("\n");
                        builder.append("</a>");
                        builder.append("\n");
                        printMenu(entrySetMenu.getValue(), builder, listMenus);
                    } else {
                        builder.append("</a>");
                        builder.append("\n");
                    }

                    builder.append("</li>");
                    builder.append("\n");
                }
            }
        }
        builder.append("</ul>");
        builder.append("\n");
        return builder;
    }

    private void printMenu(List<RecursiveMap<Menu>> listMap, StringBuilder builder, List<Menu> listMenus) {
        builder.append("<ul class=\"treeview-menu");
        if (activateMenuRecursive(listMap)) {
            builder.append(" menu-open");
        }
        builder.append("\">");
        builder.append("\n");
        for (RecursiveMap<Menu> map : listMap) {
            for (Map.Entry<Menu, List<RecursiveMap<Menu>>> entrySet : map.entrySet()) {
                if (!listMenus.contains(entrySet.getKey())) {
                    continue;
                }

                builder.append("<li class=\"");
                if (activateMenu(entrySet)) {
                    builder.append("active");
                }
                builder.append("\">");
                builder.append("\n");
                builder.append("<a href=\"");
                if (entrySet.getKey().getUrl() == null) {
                    builder.append("#");
                } else {
                    builder.append(entrySet.getKey().getUrl());
                }
                builder.append("\">");
                builder.append("\n");
                if (entrySet.getKey().getIcon() != null) {
                    builder.append("<i class=\"");
                    builder.append(entrySet.getKey().getIcon());
                    builder.append("\"></i>");
                    builder.append("\n");
                }
                builder.append("\n");
                builder.append(entrySet.getKey().getNombre());
                builder.append("\n");
                if (entrySet.getValue() != null && !entrySet.getValue().isEmpty()) {
                    builder.append("<i class=\"fa fa-angle-left pull-right\"></i>");
                    builder.append("\n");
                    builder.append("</a>");
                    builder.append("\n");
                    printMenu(entrySet.getValue(), builder, listMenus);
                } else {
                    builder.append("</a>");
                    builder.append("\n");
                }

                builder.append("</li>");
                builder.append("\n");

            }

        }
        builder.append("</ul>");
        builder.append("\n");
    }

    private boolean activateMenu(Map.Entry<Menu, List<RecursiveMap<Menu>>> entrySet) {
        if (entrySet.getKey().getUrls() != null) {
            for (MenuUrl menuUrl : entrySet.getKey().getUrls()) {
                if (menuUrl.getUrl().concat(".xhtml").equals(servletPath)) {
                    return true;
                }
            }
        }

        for (RecursiveMap<Menu> map : entrySet.getValue()) {
            for (Map.Entry<Menu, List<RecursiveMap<Menu>>> entrySetMenu : map.entrySet()) {
                if (entrySetMenu.getKey().getUrls() != null) {
                    for (MenuUrl menuUrl : entrySetMenu.getKey().getUrls()) {
                        if (menuUrl.getUrl().concat(".xhtml").equals(servletPath)) {
                            return true;
                        }
                    }
                }
                if (entrySetMenu.getValue() != null && !entrySetMenu.getValue().isEmpty()) {
                    return activateMenu(entrySetMenu);
                }
            }
        }

        return false;
    }

    private boolean activateMenuRecursive(List<RecursiveMap<Menu>> listMap) {
        for (RecursiveMap<Menu> mapFor : listMap) {
            for (Map.Entry<Menu, List<RecursiveMap<Menu>>> entrySet : mapFor.entrySet()) {
                if (activateMenu(entrySet)) {
                    return true;
                }
            }
        }
        return false;
    }

}
