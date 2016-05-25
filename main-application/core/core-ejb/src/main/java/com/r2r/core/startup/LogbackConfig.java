/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.startup;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Startup
@Singleton
public class LogbackConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogbackConfig.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Configuring logback...");
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator jc = new JoranConfigurator();
        jc.setContext(context);
        context.reset();

        context.putProperty("application-name", "core-ejb");
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("logback.xml");
            if (is == null) {
                LOGGER.error("Error loading logback config file, using default");
                return;
            }
            jc.doConfigure(is);
        } catch (JoranException je) {
            LOGGER.error("Logback contextInitialized error", je);
        }
        LOGGER.info("Logback configured...");
    }

}
