/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.common;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update the last modification date and user.
 *
 * @author Alan Hdez
 */
public class UModifListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(UModifListener.class);

    @PrePersist
    @PreUpdate
    public void updateFechaLastModification(UModifInterface lastModif) {
        // TODO: Update the user as well.
        try {
            lastModif.getUModif().setFechaUModif(LocalDateTime.now());
        } catch (NullPointerException e) {
            LOGGER.error("Falta UModif en la clase " + lastModif.getClass().getName());
            throw e;
        }
    }
}
