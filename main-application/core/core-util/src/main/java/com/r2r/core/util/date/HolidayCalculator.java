/**
 * DEVALUX CONFIDENTIAL
 *
 * [2015] Devalux - All Rights Reserved.
 *
 * ATTENTION: This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.util.date;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 *
 * @author Alan Hdez
 */
public class HolidayCalculator {

    public static short getNaturalDays(LocalDate fechaInicio, short days) {

        DateCalculator<LocalDate> cal = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("MX", HolidayHandlerType.FORWARD);
        cal.setStartDate(fechaInicio);

        cal.moveByBusinessDays(days);
        return (short) Days.daysBetween(cal.getStartDate(), cal.getCurrentBusinessDate()).getDays();
    }

    public static LocalDate getNaturalDate(short days) {
        return getNaturalDate(LocalDate.now(), days);
    }

    public static LocalDate getNaturalDate(LocalDate fechaInicio, short days) {
        DateCalculator<LocalDate> cal;

        if (days >= 0) {
            cal = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("MX", HolidayHandlerType.FORWARD);
        } else {
            cal = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("MX", HolidayHandlerType.BACKWARD);
        }

        cal.setStartDate(fechaInicio);
        cal.moveByBusinessDays(days);
        return cal.getCurrentBusinessDate();
    }

    public static short getNaturalDaysFromToday(short days) {
        return getNaturalDays(LocalDate.now(), days);
    }
}
