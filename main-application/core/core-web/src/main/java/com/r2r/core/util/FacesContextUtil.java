/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Arturo
 */
public class FacesContextUtil {

    private static HttpServletRequest request = (HttpServletRequest) javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequest();

    public static String getPath(String path) {
        return request.getServletContext().getRealPath(path);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return request;
    }
}
