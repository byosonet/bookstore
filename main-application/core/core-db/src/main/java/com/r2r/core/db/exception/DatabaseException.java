/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.exception;

import com.r2r.core.db.entity.common.UModifListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Hdez
 */
public class DatabaseException extends Exception {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UModifListener.class);

    public DatabaseException(Exception e) {
        super(e);
        LOGGER.error("Error " + e.getMessage());
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Exception e) {
        super(message, e);
    }
}
