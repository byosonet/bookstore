/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db.converter;

import java.sql.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.LocalDate;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Converter(autoApply = true)
public class JodaLocalDateConverter implements AttributeConverter<LocalDate, Date> {

    private static final long serialVersionUID = 1L;

    @Override
    public Date convertToDatabaseColumn(LocalDate x) {
        return x == null ? null : new Date(x.toDate().getTime());
    }

    @Override
    public LocalDate convertToEntityAttribute(Date y) {
        return y == null ? null : new LocalDate(y.getTime());
    }
}
