package com.googlecode.kevinarpe.papaya.testing;

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

import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TestClassFinderUtilsTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderUtils.newInstance()
    //

    @Test
    public void newInstance_Pass() {
        TestClassFinder classUnderTest = TestClassFinderUtils.newInstance();
        TestClassFinderImplTest.assertAttrEquals(
            classUnderTest,
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            TestClassFinderUtils.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderUtils.newFactory()
    //

    @Test
    public void newFactory_Pass() {
        assertSame(TestClassFinderUtils.newFactory(), TestClassFinderFactoryImpl.INSTANCE);
    }
}
