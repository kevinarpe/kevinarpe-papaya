package com.googlecode.kevinarpe.papaya.concurrent;

/*-
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

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ThreadLocalsWithResetTest {

    @Test
    public void newInstanceForStringBuilder_Pass() {

        final ThreadLocalWithReset<StringBuilder> classUnderTest = ThreadLocalsWithReset.newInstanceForStringBuilder();

        ThreadLocalWithResetTest.test_Pass(classUnderTest, x -> 0 == x.length(), x -> x.append("abc"));
    }

    @Test
    public void newInstanceForArrayList_Pass() {

        final ThreadLocalWithReset<ArrayList<String>> classUnderTest = ThreadLocalsWithReset.newInstanceForArrayList();

        ThreadLocalWithResetTest.test_Pass(classUnderTest, x -> x.isEmpty(), x -> x.add("abc"));
    }

    @Test
    public void newInstanceForHashSet_Pass() {

        final ThreadLocalWithReset<HashSet<String>> classUnderTest = ThreadLocalsWithReset.newInstanceForHashSet();

        ThreadLocalWithResetTest.test_Pass(classUnderTest, x -> x.isEmpty(), x -> x.add("abc"));
    }

    @Test
    public void newInstanceForLinkedHashSet_Pass() {

        final ThreadLocalWithReset<LinkedHashSet<String>> classUnderTest =
            ThreadLocalsWithReset.newInstanceForLinkedHashSet();

        ThreadLocalWithResetTest.test_Pass(classUnderTest, x -> x.isEmpty(), x -> x.add("abc"));
    }
}
