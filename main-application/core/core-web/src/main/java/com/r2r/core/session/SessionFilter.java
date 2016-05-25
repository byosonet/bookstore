/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.session;

import java.io.IOException;
import javax.faces.application.ViewExpiredException;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.omnifaces.filter.HttpFilter;

/**
 *
 * @author Alan Hdez
 */
@WebFilter(urlPatterns = {"/*"})
public class SessionFilter extends HttpFilter {

    public static final String[] WHITE_PATHS = new String[]{
        "/javax.faces.resource/",};

    public static final String[] WHITE_PAGES = new String[]{
        "/login", "/login.xhtml",
        "/logout", "/connect", "/peopleGoogle", "/disconnect"};

    public static final String[] USER_PAGES = new String[]{
        "/index", "/index.xhtml"};

    @Inject
    private UserSession userSession;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, HttpSession session, FilterChain chain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        // If the request goes to white list path, should pass without the user logged
        for (String whitePath : WHITE_PATHS) {
            if (servletPath.startsWith(whitePath)) {
                chain.doFilter(request, response);
                return;
            }
        }
        // If the request goes to white list page, should pass without the user logged
        int endDir = servletPath.indexOf('?');
        if (endDir > 0) {
            servletPath = servletPath.substring(0, endDir);
        }
        for (String whitePage : WHITE_PAGES) {
            if (servletPath.equals(whitePage)) {
                chain.doFilter(request, response);
                return;
            }
        }

        if (!servletPath.equals("/index") && !servletPath.equals("/login") && request.getSession() != null && request.getSession().getId() != null && !request.isRequestedSessionIdValid()) {
            throw new ViewExpiredException();
        }

        // If pass, the user should be logged
        if (userSession.getUser() == null) {
            response.sendRedirect(request.getContextPath() + "/login");
//            userSession.setUrlAccess(new LinkedList<String>());
            return;
        }

        // then check with the users permissions
        for (String menuUrl : userSession.getUrlAccess()) {
            if (servletPath.equals(menuUrl)) {
                chain.doFilter(request, response);
                return;
            }
        }

        for (String userPage : USER_PAGES) {
            if (servletPath.equals(userPage)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // If get here the user doesn't have permissions
        response.sendRedirect(request.getContextPath() + "/index");
    }
}
