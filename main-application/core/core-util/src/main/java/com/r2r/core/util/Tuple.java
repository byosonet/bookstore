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
public class Tuple<A, B> {
    
    protected final A a;
    protected B b;
    
    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
    
    public void setB(B b) {
        this.b = b;
    }
}
