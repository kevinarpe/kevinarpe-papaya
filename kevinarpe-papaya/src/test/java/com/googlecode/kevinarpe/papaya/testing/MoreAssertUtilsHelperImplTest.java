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

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.testing.mockito.MockitoUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MoreAssertUtilsHelperImplTest {

    private static enum EqualsNonNull {YES, NO}
    private static enum EqualsNull {YES, NO}
    private static enum HashCode {A, B}

    private static final class _EqualsAndHashCodeHelper {

        private final EqualsNonNull equalsNonNull;
        private final EqualsNull equalsNull;
        private final HashCode hashCode;

        private _EqualsAndHashCodeHelper(
            EqualsNonNull equalsNonNull, EqualsNull equalsNull, HashCode hashCode) {
            this.equalsNonNull = equalsNonNull;
            this.equalsNull = equalsNull;
            this.hashCode = hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                boolean x = (EqualsNull.YES == equalsNull);
                return x;
            }
            else {
                boolean x = (EqualsNonNull.YES == equalsNonNull);
                return x;
            }
        }

        @Override
        public int hashCode() {
            int x = (HashCode.A == hashCode) ? 1 : 2;
            return x;
        }
    }

    private static final String FORMAT = "%s:%d";
    private static final Object[] FORMAT_ARG_ARR = new Object[]{"abc", 123};
    private static final String PREFIX;

    static {
        PREFIX = String.format(FORMAT, FORMAT_ARG_ARR);
    }

    private MoreAssertUtilsHelperImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new MoreAssertUtilsHelperImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtilsHelperImpl.assertEqualsAndHashCodeCorrect()
    //

    @Test(expectedExceptions = AssertionError.class)
    public void assertEqualsAndHashCodeCorrect_FailWhenActualNotEqualsToExpected() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertEqualsAndHashCodeCorrect(
                        _EqualsAndHashCodeHelper.class,
                        new _EqualsAndHashCodeHelper(EqualsNonNull.NO, EqualsNull.NO, HashCode.A),
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.A),
                        FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertEqualsAndHashCodeCorrect_FailWhenExpectedNotEqualsToActual() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertEqualsAndHashCodeCorrect(
                        _EqualsAndHashCodeHelper.class,
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.A),
                        new _EqualsAndHashCodeHelper(EqualsNonNull.NO, EqualsNull.NO, HashCode.A),
                        FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertEqualsAndHashCodeCorrect_FailWhenActualEqualsToNull() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertEqualsAndHashCodeCorrect(
                        _EqualsAndHashCodeHelper.class,
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.YES, HashCode.A),
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.A),
                        FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertEqualsAndHashCodeCorrect_FailWhenExpectedEqualsToNull() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertEqualsAndHashCodeCorrect(
                        _EqualsAndHashCodeHelper.class,
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.A),
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.YES, HashCode.A),
                        FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertEqualsAndHashCodeCorrect_FailWhenEqualsButNotHashCode() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertEqualsAndHashCodeCorrect(
                        _EqualsAndHashCodeHelper.class,
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.A),
                        new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.B),
                        FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test
    public void assertEqualsAndHashCodeCorrect_Pass() {
        classUnderTest.assertEqualsAndHashCodeCorrect(
            _EqualsAndHashCodeHelper.class,
            new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.A),
            new _EqualsAndHashCodeHelper(EqualsNonNull.YES, EqualsNull.NO, HashCode.A),
            FORMAT, FORMAT_ARG_ARR);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtilsHelperImpl.assertNeitherNull()
    //

    @Test(expectedExceptions = AssertionError.class)
    public void assertNeitherNull_FailWhenActualIsNull() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertNeitherNull(
                        String.class, null, "abc", FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertNeitherNull_FailWhenExpectedIsNull() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertNeitherNull(
                        String.class, "abc", null, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test
    public void assertNeitherNull_Pass() {
        classUnderTest.assertNeitherNull(String.class, "abc", "def", FORMAT, FORMAT_ARG_ARR);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtilsHelperImpl.assertSameContainerSize()
    //

    @Test(expectedExceptions = AssertionError.class)
    public void assertSameContainerSize_FailWhenSizesNotEqual() {
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertSameContainerSize(
                        List.class, 3, 4, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test
    public void assertSameContainerSize_Pass() {
        classUnderTest.assertSameContainerSize(List.class, 3, 3, FORMAT, FORMAT_ARG_ARR);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtilsHelperImpl.throwAssertionError()
    //

    @Test
    public void throwAssertionError_Pass() {
        try {
            classUnderTest.throwAssertionError(null, new Object[0], "abc: %d", 3);
        }
        catch (AssertionError e) {
            assertEquals(e.getMessage(), "abc: 3");
        }
    }

    @Test
    public void throwAssertionError_Pass2() {
        try {
            classUnderTest.throwAssertionError("def: %d", new Object[]{5}, "abc: %d", 3);
        }
        catch (AssertionError e) {
            assertEquals(e.getMessage(), String.format("def: 5:%nabc: 3"));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtilsHelperImpl.assertSetContains()
    //

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetContains_FailWhenActualSetDoesNotContain() {
        final Set<String> actualSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "actualSet");
        final Set<String> expectedSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "expectedSet");
        when(expectedSet.iterator()).thenReturn(Iterators.forArray("blah"));
        when(actualSet.contains("blah")).thenReturn(false);
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertSetContains(
                        "Set", actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetContains_FailWhenExpectedSetDoesNotContain() {
        final Set<String> actualSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "actualSet");
        final Set<String> expectedSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "expectedSet");
        when(expectedSet.iterator()).thenReturn(Iterators.<String>forArray());
        when(actualSet.iterator()).thenReturn(Iterators.forArray("blah"));
        when(expectedSet.contains("blah")).thenReturn(false);
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertSetContains(
                        "Set", actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetContains_FailWhenActualSetDoesNotContainAll() {
        final Set<String> actualSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "actualSet");
        final Set<String> expectedSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "expectedSet");
        when(expectedSet.iterator()).thenReturn(Iterators.<String>forArray());
        when(actualSet.iterator()).thenReturn(Iterators.<String>forArray());
        when(actualSet.containsAll(expectedSet)).thenReturn(false);
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertSetContains(
                        "Set", actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetContains_FailWhenExpectedSetDoesNotContainAll() {
        final Set<String> actualSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "actualSet");
        final Set<String> expectedSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Set.class, "expectedSet");
        when(expectedSet.iterator()).thenReturn(Iterators.<String>forArray());
        when(actualSet.iterator()).thenReturn(Iterators.<String>forArray());
        when(actualSet.containsAll(expectedSet)).thenReturn(true);
        when(expectedSet.containsAll(expectedSet)).thenReturn(false);
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertSetContains(
                        "Set", actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @DataProvider
    private static Object[][] _assertSetContains_Pass_Data() {
        return new Object[][]{
            {Sets.<String>newHashSet(), Sets.<String>newHashSet()},
            {Sets.<String>newHashSet("abc"), Sets.<String>newHashSet("abc")},
            {Sets.<String>newHashSet("abc", "def"), Sets.<String>newHashSet("abc", "def")},
            {Sets.<String>newHashSet("abc", "def"), Sets.<String>newHashSet("def", "abc")},
            {
                Sets.<String>newLinkedHashSet(Arrays.asList("abc", "def")),
                Sets.<String>newLinkedHashSet(Arrays.asList("abc", "def")),
            },
            {
                Sets.<String>newLinkedHashSet(Arrays.asList("abc", "def")),
                Sets.<String>newLinkedHashSet(Arrays.asList("def", "abc")),
            },
        };
    }

    @Test(dataProvider = "_assertSetContains_Pass_Data")
    public void assertSetContains_Pass(Set<String> actualSet, Set<String> expectedSet) {
        classUnderTest.assertSetContains("Set", actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtilsHelperImpl.assertSetContains()
    //

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEntrySetEquals_FailWhenActualEntrySetNotEqualsExpectedEntrySet() {
        final Map<String, Integer> actualSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Map.class);
        final Map<String, Integer> expectedSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Map.class);
        when(actualSet.entrySet())
            .thenReturn(
                Sets.<Map.Entry<String, Integer>>newHashSet(
                    new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123)))
            .thenReturn(Sets.<Map.Entry<String, Integer>>newHashSet());
        when(expectedSet.entrySet())
            .thenReturn(Sets.<Map.Entry<String, Integer>>newHashSet())
            .thenReturn(Sets.<Map.Entry<String, Integer>>newHashSet());
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertMapEntrySetEquals(
                        "Map", actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEntrySetEquals_FailWhenExpectedEntrySetNotEqualsActualEntrySet() {
        final Map<String, Integer> actualSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Map.class);
        final Map<String, Integer> expectedSet =
            MockitoUtils.INSTANCE.mockGenericInterface(Map.class);
        when(actualSet.entrySet())
            .thenReturn(Sets.<Map.Entry<String, Integer>>newHashSet())
            .thenReturn(
                Sets.<Map.Entry<String, Integer>>newHashSet(
                    new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123)));
        when(expectedSet.entrySet())
            .thenReturn(Sets.<Map.Entry<String, Integer>>newHashSet())
            .thenReturn(Sets.<Map.Entry<String, Integer>>newHashSet());
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertMapEntrySetEquals(
                        "Map", actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    @Test
    public void assertMapEntrySetEquals_Pass() {
        final HashMap<String, Integer> actualMap = new HashMap<String, Integer>();
        actualMap.put("abc", 123);
        final HashMap<String, Integer> expectedMap = new HashMap<String, Integer>(actualMap);
        _checkExceptionMessagePrefix(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertMapEntrySetEquals(
                        "Map", actualMap, expectedMap, FORMAT, FORMAT_ARG_ARR);
                }
            },
            PREFIX);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private static void _checkExceptionMessagePrefix(
        Runnable runnable, String exceptionMessagePrefix) {
        try {
            runnable.run();
        }
        catch (AssertionError e) {
            try {
                assertTrue(e.getMessage().startsWith(exceptionMessagePrefix));
            }
            catch (AssertionError e2) {
                throw new RuntimeException(e2);
            }
            throw e;
        }
    }
}
