package com.googlecode.kevinarpe.papaya.comparator;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.google.common.testing.EqualsTester;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class AbstractLexicographicalComparatorTest {

    private static class AbstractLexicographicalComparatorImpl
    extends AbstractLexicographicalComparator {

        public AbstractLexicographicalComparatorImpl() {
            super();
        }

        public AbstractLexicographicalComparatorImpl(CaseSensitive caseSensitive) {
            super(caseSensitive);
        }

        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractLexicographicalComparator.ctor()
    //

    @Test
    public void ctor_PassWithCaseSensitive() {
        for (CaseSensitive cs : CaseSensitive.values()) {
            assertEquals(new AbstractLexicographicalComparatorImpl(cs).getCaseSensitive(), cs);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullCaseSensitive() {
        new AbstractLexicographicalComparatorImpl((CaseSensitive) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractLexicographicalComparator.setSensitive()
    //

    @Test
    public void setSensitive_Pass() {
        for (CaseSensitive cs : CaseSensitive.values()) {
            assertEquals(new AbstractLexicographicalComparatorImpl().setCaseSensitive(cs).getCaseSensitive(), cs);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void setSensitive_FailWithNull() {
        new AbstractLexicographicalComparatorImpl().setCaseSensitive(null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractLexicographicalComparator.isSensitive()
    //

    @Test
    public void isSensitive_Pass() {
        assertTrue(
            new AbstractLexicographicalComparatorImpl()
                .setCaseSensitive(CaseSensitive.YES)
                .isCaseSensitive());
        assertFalse(
            new AbstractLexicographicalComparatorImpl()
                .setCaseSensitive(CaseSensitive.NO)
                .isCaseSensitive());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractLexicographicalComparator.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        // This looks a bit weird to the casual reader.
        // Each call to EqualsTester.addEqualityGroup() expects objects that are equal/same hash
        // code.  However, values between calls to addEqualityGroup() must not be equals.
        // Get your head around that.
        // Why call new AbstractLexicographicalComparatorImpl(cs) twice?  If hashCode is badly implemented, it will fail.
        // Example: The default hashCode implementation might just return the internal object ID.
        EqualsTester equalsTester = new EqualsTester();
        for (CaseSensitive cs : CaseSensitive.values()) {
            equalsTester.addEqualityGroup(
                new AbstractLexicographicalComparatorImpl(cs),
                new AbstractLexicographicalComparatorImpl(cs));
        }
        equalsTester.testEquals();
    }
}
