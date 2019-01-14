package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // IterableArgs.checkElementsNotNull
    //

    @DataProvider
    public static Object[][] checkElementsNotNull_Pass_Data() {
        return CollectionArgsTest.checkElementsNotNull_Pass_Data();
    }
    
    @Test(dataProvider = "checkElementsNotNull_Pass_Data")
    public <T> void checkElementsNotNull_Pass(Iterable<T> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == IterableArgs.checkElementsNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == IterableArgs.checkElementsNotNull(ref, null));
        Assert.assertTrue(ref == IterableArgs.checkElementsNotNull(ref, ""));
        Assert.assertTrue(ref == IterableArgs.checkElementsNotNull(ref, "   "));
    }

    @DataProvider
    public static Object[][] checkElementsNotNull_FailWithNullElements_Data() {
        return CollectionArgsTest.checkElementsNotNull_FailWithNullElements_Data();
    }
    
    @Test(dataProvider = "checkElementsNotNull_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkElementsNotNull_FailWithNullElements(Iterable<T> ref) {
        IterableArgs.checkElementsNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkElementsNotNull_FailWithNullIterable() {
        IterableArgs.checkElementsNotNull((Iterable<Object>) null, "ref");
    }
}
