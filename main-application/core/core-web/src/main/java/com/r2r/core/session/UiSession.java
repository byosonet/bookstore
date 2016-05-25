package com.r2r.core.session;

import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.navegacion.Oficina;
import com.r2r.core.jsf.JSFConstant;
import com.r2r.core.security.AccessEjb;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Alan Hdez
 */
@Named
@SessionScoped
public class UiSession implements Serializable {

    @EJB
    private AccessEjb access;

    @Inject
    private UserSession userSession;

    private String perfil;
    private List<Oficina> oficinasList;

    private List<Menu> listMenus;

    private String codeMenu;

    private String dateTimeLocalizedPattern;
    private String dateLocalizedPattern;

    public boolean login(String nickname, String password) {
        AccessEjb.UserLoggedInfo userLeggedInfo = access.login(nickname, password);
        if (userLeggedInfo == null) {
            return false;
        }

        userSession.setUser(userLeggedInfo.getUser());
        listMenus = userLeggedInfo.getListMenus();
        if (listMenus.isEmpty()) {
            return false;
        }

        perfil = userLeggedInfo.getNombrePerfil();
        oficinasList = userLeggedInfo.getOficinas();
        userSession.setOficina(oficinasList.get(0));
        userSession.setUrlAccess(userLeggedInfo.getUrlsFilter());

        if (dateTimeLocalizedPattern == null) {
            dateTimeLocalizedPattern = DateTimeFormat.patternForStyle("SS", FacesContext.getCurrentInstance().getViewRoot().getLocale()).replace("a", "").trim();
        }
        if (dateLocalizedPattern == null) {
            dateLocalizedPattern = DateTimeFormat.patternForStyle("S-", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        }
        return true;
    }

    public String getDateTimeLocalizedPattern() {
        return dateTimeLocalizedPattern;
    }

    public String getDateLocalizedPattern() {
        return dateLocalizedPattern;
    }

    public void setDateTimeLocalizedPattern(Locale locale) {
        this.dateTimeLocalizedPattern = DateTimeFormat.patternForStyle("SS", locale).replace("a", "").trim();
    }

    public void setDateLocalizedPattern(Locale locale) {
        dateLocalizedPattern = DateTimeFormat.patternForStyle("S-", locale);
    }

    public String oficinaUpdated() {
        return "/index?" + JSFConstant.REDIRECT;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public List<Oficina> getOficinasList() {
        return oficinasList;
    }

    public void setOficinasList(List<Oficina> oficinasList) {
        this.oficinasList = oficinasList;
    }

    public List<Menu> getListMenus() {
        return listMenus;
    }

    public void setListMenus(List<Menu> listMenus) {
        this.listMenus = listMenus;
    }

    public String getCodeMenu() {
        return codeMenu;
    }

    public void setCodeMenu(String codeMenu) {
        this.codeMenu = codeMenu;
    }

}
