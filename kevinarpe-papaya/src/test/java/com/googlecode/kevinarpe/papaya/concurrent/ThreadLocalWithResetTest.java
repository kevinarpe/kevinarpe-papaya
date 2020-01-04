package com.googlecode.kevinarpe.papaya.concurrent;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ThreadLocalWithResetTest {

    private ThreadLocalWithReset<ArrayList<String>> classUnderTest;

    @BeforeMethod
    public void beforeEachTest() {

        classUnderTest = ThreadLocalWithReset.withInitialAndReset(() -> new ArrayList<>(), x -> x.clear());
    }

    @Test
    public void pass() {

        test_Pass(classUnderTest, x -> x.isEmpty(), x -> x.add("abc"));
    }

    public static <TValue>
    void test_Pass(ThreadLocalWithReset<TValue> classUnderTest,
                   Function<TValue, Boolean> isEmpty,
                   Consumer<TValue> modify) {

        final TValue c = classUnderTest.getAndReset();
        Assert.assertTrue(isEmpty.apply(c));
        modify.accept(c);
        final TValue c2 = classUnderTest.getAndReset();
        Assert.assertSame(c2, c);
        Assert.assertTrue(isEmpty.apply(c));
        Assert.assertTrue(isEmpty.apply(c2));
    }
}
