/*
 * ZEITEK CONFIDENTIAL
 *
 * [2014-2015] Zeitek - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.google.access;

import com.r2r.core.util.GoogleConstant;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Arturo
 */
@Named
@SessionScoped
public class GoogleSession implements Serializable {

    private String state;
    private String token;

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIdClient() {
        return GoogleConstant.ID_CLIENT;
    }

    public String getApplicationName() {
        return GoogleConstant.APPLICATION_NAME;
    }

    public String getSecretClient() {
        return GoogleConstant.SECRET_CLIENT;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @PostConstruct
    public void init() {
        this.state = new BigInteger(130, new SecureRandom()).toString(32);
        token = null;
    }

}
