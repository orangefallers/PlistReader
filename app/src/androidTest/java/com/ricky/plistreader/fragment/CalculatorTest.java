package com.ricky.plistreader.fragment;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

    private Calculator mCal;

    public void setUp() throws Exception {
        super.setUp();
        mCal = new Calculator();
    }

    public void testSum() throws Exception {
        assertEquals(6d, mCal.sum(1d,5d),0);

    }

    public void testSubstract() throws Exception {
        assertEquals(1d, mCal.substract(5d, 4d), 0);
    }

    public void testDivide() throws Exception {

    }

    public void testMultiply() throws Exception {

    }
}