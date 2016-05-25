/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Hdez
 */
public class Config {

    public static final String DEFAULT_COUNTRY_ID = "defaultCountryId",
            DEFAULT_STATE_ID = "defaultStateId",
            DEFAULT_PERFIL_NAME = "defaultPerfilName",
            DEFAULT_SYSTEM_NAME = "defaultSystemName";

    private static final Properties config = new Properties();

    private final static Logger LOGGER = LoggerFactory.getLogger(Config.class);

    static {
        try (InputStream stream = Config.class.getClassLoader().getResourceAsStream("META-INF/config.properties")) {
            config.load(stream);
        } catch (IOException ex) {
            LOGGER.error("Error loading the config.properties", ex);
        }
    }

    public static String getStringProperty(String key) {
        return config.getProperty(key);
    }
}
