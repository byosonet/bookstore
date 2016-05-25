/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.entity.common;

/**
 * Basic status enum.
 * <p>
 * Only two options are given, {@code ENABLED} and {@code DISABLED}</p>
 *
 * @author Alan Hdez
 */
public enum Visible {

    /**
     * Statuses
     */
    DISABLED("disabled"), ENABLED("active");

    private final String name;

    private Visible(String name) {
        this.name = name;
    }

    /**
     * @return the status name with the full class path and name
     */
    public String getFullPath() {
        return getClass().getName() + "." + name();
    }

    /**
     * @return the status friendly name
     */
    @Override
    public String toString() {
        return name;
    }
}
