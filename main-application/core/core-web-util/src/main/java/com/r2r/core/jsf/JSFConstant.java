/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.jsf;

/**
 *
 * @author
 */
public class JSFConstant {

    /**
     * Avoid class instantiation.
     */
    private JSFConstant() {

    }

    /**
     * Time in seconds for a cookie to live.
     */
    public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 5;

    /**
     * Forces a redirect after an action is called.
     */
    public static final String REDIRECT = "faces-redirect=true";

    public static final String MAIN_MESSAGES_GROWL = "mainGrowl";
}
