/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.exception;

import java.util.HashMap;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Hdez
 */
public class JpaConstraintException extends DatabaseException {

    private HashMap<String, String> hashViolation;

    private HashMap<String, String> hashMessages;

    private final static Logger LOGGER = LoggerFactory.getLogger(JpaConstraintException.class);

    public JpaConstraintException(ConstraintViolationException e) {
        super(e);
        hashViolation = new HashMap<>(e.getConstraintViolations().size());
        hashMessages = new HashMap<>(e.getConstraintViolations().size());
        StringBuilder builder;
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            builder = new StringBuilder();
            builder.append(constraintViolation.getPropertyPath().toString());
            builder.append(": ");
            builder.append(constraintViolation.getInvalidValue());
            builder.append(" <br> ");
            builder.append(constraintViolation.getMessage());
            builder.append(" <br> ");
            builder.append(constraintViolation.getRootBean());
            LOGGER.error(builder.toString());
            hashViolation.put(constraintViolation.getPropertyPath().toString(), builder.toString());
            hashMessages.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }

    }

    public HashMap<String, String> getHashViolation() {
        return hashViolation;
    }

    public HashMap<String, String> getHashMessages() {
        return hashMessages;
    }

}
