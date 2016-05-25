/*
 * ZEITEK CONFIDENTIAL
 *
 * [2014-2015] Zeitek - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.session;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Alan Hdez
 */
@Named
@RequestScoped
public class UserImageService implements Serializable {

    @Inject
    private UserSession userSession;

    public byte[] getFoto() {
        return userSession.getUser().getFoto();
    }

}
