package com.tss.awesomehotel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest
{
    @Test
    public void testCheckIfStringContainsSomething()
    {
        Assertions.assertFalse(StringHelper.checkIfStringContainsSomething(null));
        Assertions.assertFalse(StringHelper.checkIfStringContainsSomething(""));
        Assertions.assertFalse(StringHelper.checkIfStringContainsSomething(" "));
        Assertions.assertTrue(StringHelper.checkIfStringContainsSomething("a"));
    }
}