/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.startup;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Singleton
@Startup
@DependsOn("LogbackConfig")
public class I18nMessages {

    private final static Logger LOGGER = LoggerFactory.getLogger(I18nMessages.class);

    private final Map<String, MessageResource> messagesResoucesMap = new HashMap<>();

    /**
     * Set the messages path to {@code ValidationMessages} for integration with
     * JSR-303 validation messages.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("Configuring omnifaces...");
        Messages.setResolver(new MessageResolver());
        LOGGER.info("Omnifaces configured...");
    }

    public void addResource(String name, String path, ClassLoader classLoader) {
        messagesResoucesMap.put(name, new MessageResource(path, classLoader));
    }

    public String getMessage(String message, Object... params) {
        ResourceBundle bundle;
        if (message.contains(".")) {
            String[] messages = message.split("\\.");
            MessageResource messageResource = messagesResoucesMap.get(messages[0]);
            if (messageResource != null) {
                bundle = ResourceBundle.getBundle(messageResource.messagePath, Faces.getLocale(), messageResource.classLoader);
                if (bundle.containsKey(messages[1])) {
                    message = bundle.getString(messages[1]);
                }
            }
        }
        return MessageFormat.format(message, params);
    }

    class MessageResolver implements Messages.Resolver {

        @Override
        public String getMessage(String message, Object... params) {
            return I18nMessages.this.getMessage(message, params);
        }
    }

    class MessageResource {

        private final String messagePath;

        private final ClassLoader classLoader;

        public MessageResource(final String messagePath, final ClassLoader classLoader) {
            this.messagePath = messagePath;
            this.classLoader = classLoader;
        }
    }
}
