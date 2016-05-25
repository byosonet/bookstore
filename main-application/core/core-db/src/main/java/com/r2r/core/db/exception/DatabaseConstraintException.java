/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.exception;

import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.util.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alan Hdez
 */
public class DatabaseConstraintException extends DatabaseException {

    public static final String CONSTRAINT_ERROR_CODE = "com.microsoft.sqlserver.jdbc.SQLServerException";
    public static final String CONSTRAINT_ERROR_CODE_BEGIN = "UNIQUE KEY '";
    public static final String CONSTRAINT_ERROR_CODE_END = "'.";
    public static final String MYSQL_CONSTRAINT_EXCEPTION_NAME = "com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException";

    private String field;

    private String errorCode;

    private Object[] errorValues;

    private DatabaseConstraintIndex.ConstraintType constraintType;

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseConstraintException.class);

    public DatabaseConstraintException(Exception e) {
        super(e);
    }

    public DatabaseConstraintException(String message) {
        super(message);
    }

    public DatabaseConstraintException(Exception e, String field, String errorCode, Object[] errorValues, DatabaseConstraintIndex.ConstraintType constraintType) {
        super(e);
        this.field = field;
        this.errorCode = errorCode;
        this.errorValues = errorValues;
        this.constraintType = constraintType;
    }

    public DatabaseConstraintException(String message, String field, String errorCode, Object[] errorValues, DatabaseConstraintIndex.ConstraintType constraintType) {
        super(message);
        this.field = field;
        this.errorCode = errorCode;
        this.errorValues = errorValues;
        this.constraintType = constraintType;
    }

    @SuppressWarnings("unchecked")
    public <E extends EntityAbstract> DatabaseConstraintException(org.eclipse.persistence.exceptions.DatabaseException constraintViolationException, E entity) {
        super(constraintViolationException);
//        initCause(constraintViolationException);
        if (constraintViolationException.getInternalException() == null) {
            LOGGER.warn("Can not decode exception with message {}", constraintViolationException.getMessage());
            return;
        }
        String constraintName = null;
        switch (constraintViolationException.getInternalException().getClass().getName()) {
            case MYSQL_CONSTRAINT_EXCEPTION_NAME:
                constraintName = getMysqlContraintName(constraintViolationException);
                break;
        }
        if (constraintName == null) {
            LOGGER.warn("System can not handle the database error {}", constraintViolationException.getMessage());
            return;
        }
        Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated> constraintDataType;
        if ((constraintDataType = DatabaseConstraintIndex.searchIndexData(constraintName)) == null) {
            LOGGER.warn("InterfaceConstraint not found for constraint {}", constraintName);
            return;
        }
        field = constraintDataType.getA();
        constraintType = constraintDataType.getB();
        errorCode = constraintDataType.getC().getLabel();
        errorValues = constraintDataType.getC().getValues(entity);
    }

    public String getField() {
        return field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getErrorValues() {
        return errorValues;
    }

    public DatabaseConstraintIndex.ConstraintType getConstraintType() {
        return constraintType;
    }

    private String getMysqlContraintName(org.eclipse.persistence.exceptions.DatabaseException constraintViolationException) {
        String message = constraintViolationException.getCause().getMessage();
        message = message.substring(0, message.length() - 1);
        message = message.substring(message.lastIndexOf("'") + 1);
        return message;
    }

}
