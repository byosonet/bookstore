/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.converter;

import java.sql.Timestamp;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.LocalDateTime;

/**
 * Converts {@link LocalDateTime} to {@link Timestamp} for the database.
 *
 * @author
 */
@Converter(autoApply = true)
public class JodaLocalDateTimeConverter implements AttributeConverter<LocalDateTime, java.sql.Timestamp> {

    private static final long serialVersionUID = 1L;

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime x) {
        return x == null ? null : new Timestamp(x.toDateTime().getMillis());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp y) {
        return y == null ? null : new LocalDateTime(y.getTime());
    }
}
