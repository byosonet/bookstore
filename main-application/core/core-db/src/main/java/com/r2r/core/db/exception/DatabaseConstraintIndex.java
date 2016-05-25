/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.exception;

import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.util.Triple;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alan Hdez
 */
public class DatabaseConstraintIndex {

    public static final Map<String, Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>> MAP_CONSTRAINTS = new HashMap<>();

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("PRIMARY", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("id", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<EntityAbstract>() {
            @Override
            public Object[] getValues(EntityAbstract entity) {
                return new Object[]{entity.getId()};
            }

            @Override
            public String getLabel() {
                return "core.entityIdAlreadyInSystem";
            }
        }
        ));
    }

    public enum ConstraintType {

        UNIQUE("devalux.database.constraint.unique"),
        MISSING_RELATIONSHIP("devalux.database.constraint.missingRelationship");
        private final String message;

        ConstraintType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static final Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated> tripleCatalogName = new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("nombre", ConstraintType.UNIQUE, new InterfaceConstraintViolated<CatalogAbstract>() {
        @Override
        public Object[] getValues(CatalogAbstract entity) {
            return new Object[]{entity.getNombre()};
        }

        @Override
        public String getLabel() {
            return "core.entityWithNameAlreadyInSystem";
        }
    });

    public static Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated> searchIndexData(String message) {
        for (String constraintName : MAP_CONSTRAINTS.keySet()) {
            if (message.contains(constraintName)) {
                return MAP_CONSTRAINTS.get(constraintName);
            }
        }
        return null;
    }
}
