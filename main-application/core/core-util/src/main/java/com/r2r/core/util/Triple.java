/*
 * DEVALUX CONFIDENTIAL
 * 
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */

package com.r2r.core.util;

/**
 *
 * @author Alan Hdez
 */
public class Triple<A, B, C> extends Tuple<A, B> {
    
    protected final C c;
    
    public Triple(A a, B b, C c) {
        super(a, b);
        this.c = c;
    }

    public C getC() {
        return c;
    }
}
