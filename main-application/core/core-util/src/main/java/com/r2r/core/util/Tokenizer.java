/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.util;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Alan Hdez
 */
public class Tokenizer {

    public static String createChalengeToken(Object... data) {
        StringBuilder sb = new StringBuilder();
        for (Object o : data) {
            sb.append('|');
            sb.append(o);
        }
        sb.append(LocalDateTime.now());
        return new BasicPasswordEncryptor().encryptPassword(sb.substring(1));
    }
}
