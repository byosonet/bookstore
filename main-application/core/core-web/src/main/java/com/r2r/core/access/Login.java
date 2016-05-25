/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.access;

import com.r2r.core.jsf.JSFConstant;
import com.r2r.core.session.UiSession;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Hdez
 */
@RequestScoped
@Named
public class Login implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Login.class);
    private static final String COOKIE_REMEMBER_ME = "rememberMe";
    private static final String COOKIE_NICKNAME = "nickname";

    @NotNull
    private String nickname;
    @NotNull
    private String password;

    private boolean rememberMe;

    @Inject
    private UiSession uiSession;

    @PostConstruct
    public void init() {
        rememberMe = "1".equals(Faces.getRequestCookie(COOKIE_REMEMBER_ME));
        if (rememberMe) {
            nickname = Faces.getRequestCookie(COOKIE_NICKNAME);
        }
    }

    public String login() {
        Faces.addResponseCookie(COOKIE_REMEMBER_ME, rememberMe ? "1" : "0", "login.xhtml", JSFConstant.COOKIE_MAX_AGE);
        if (rememberMe) {
            Faces.addResponseCookie(COOKIE_NICKNAME, nickname, "login.xhtml", JSFConstant.COOKIE_MAX_AGE);
        }
        if (!uiSession.login(nickname, password)) {
            Messages.addGlobalError("core.loginFailed");
            LOGGER.error("Fallo el login");
            return null;
        }
        LOGGER.info("Login correcto");
        return "index?" + JSFConstant.REDIRECT;
    }

    //<editor-fold defaultstate="collapsed" desc="Properties">
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
    //</editor-fold>
}
