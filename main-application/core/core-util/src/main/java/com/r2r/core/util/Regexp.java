/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.util;

/**
 * Constants holding the most common expressions for regular expressions in the
 * system.
 *
 * @author Alan Hdez
 */
public class Regexp {

    /**
     * Avoid class instantiation.
     */
    private Regexp() {

    }

    public static final String BASIC_CHARACTERS = "^[A-Za-z0-9_-]*$",
            NUMBER_OR_CHAR = "^[A-Za-z0-9]*$",
            NUMBER_ONLY = "^[0-9]*$",
            SPANISH_WORDS = "^[A-Za-zÑñáéíóúÁÉÍÓÚ\\s]*$",
            SPANISH_WORDS_DOT = "^[A-Za-zÑñáéíóúÁÉÍÓÚ\\s\\.]*$",
            SPANISH_LETTERS = "^[A-Za-zÑñáéíóúÁÉÍÓÚ]*$",
            VIN_VALID = "^[A-HJ-NPR-Z0-9]*$",
            CURP_VALID = "^[a-zA-Z]{4}((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(H|M)(AS|BC|BS|CC|CL|CM|CS|CH|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|SM|NE)([a-zA-Z]{3})([a-zA-Z0-9\\s]{1})\\d{1}|[a-zA-Z]{4}((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)$",
            EMAIL_VALID = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

}
