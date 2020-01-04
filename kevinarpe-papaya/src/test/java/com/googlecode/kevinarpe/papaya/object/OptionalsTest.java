package com.googlecode.kevinarpe.papaya.object;

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

import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class OptionalsTest {

    ///////////////////////////////////////////////////////////////////////////
    // Optionals.apply
    //

    private static Function<String, Integer> TRANSFORM = String::length;
    private static Function<String, Integer> TRANSFORM_TO_NULL = (String s) -> null;

    @DataProvider
    public static Object[][] apply_Pass_Data() {
        return new Object[][]{
            {null, TRANSFORM, null},
            {"", TRANSFORM, 0},
            {" ", TRANSFORM, 1},
            {"abc", TRANSFORM, 3},
            {null, TRANSFORM_TO_NULL, null},
            {"", TRANSFORM_TO_NULL, null},
            {" ", TRANSFORM_TO_NULL, null},
            {"abc", TRANSFORM_TO_NULL, null},
        };
    }

    @Test(dataProvider = "apply_Pass_Data")
    public void apply_Pass(@Nullable
                               String nullableInput,
                           Function<String, Integer> transform,
                           @Nullable
                               Integer nullableExpectedOutput) {

        final Optional<String> input = Optional.ofNullable(nullableInput);
        final Optional<Integer> output = Optionals.apply(input, transform);
        final Optional<Integer> expectedOutput = Optional.ofNullable(nullableExpectedOutput);
        Assert.assertEquals(output, expectedOutput);
    }

    private static class _RuntimeException
        extends RuntimeException {
    }

    @Test(expectedExceptions = _RuntimeException.class)
    public void apply_FailWhenTransformThrows() {

        Optionals.apply(Optional.of("abc"), (String s) -> {
            throw new _RuntimeException();
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // Nullables.throwingApply
    //

    private static ThrowingFunction<String, Integer> THROWING_TRANSFORM = String::length;
    private static ThrowingFunction<String, Integer> THROWING_TRANSFORM_TO_NULL = (String s) -> null;

    @DataProvider
    public static Object[][] throwingApply_Pass_Data() {
        return new Object[][]{
            {null, THROWING_TRANSFORM, null},
            {"", THROWING_TRANSFORM, 0},
            {" ", THROWING_TRANSFORM, 1},
            {"abc", THROWING_TRANSFORM, 3},
            {null, THROWING_TRANSFORM_TO_NULL, null},
            {"", THROWING_TRANSFORM_TO_NULL, null},
            {" ", THROWING_TRANSFORM_TO_NULL, null},
            {"abc", THROWING_TRANSFORM_TO_NULL, null},
        };
    }

    @Test(dataProvider = "throwingApply_Pass_Data")
    public void throwingApply_Pass(@Nullable
                                           String nullableInput,
                                   ThrowingFunction<String, Integer> transform,
                                   @Nullable
                                           Integer nullableExpectedOutput)
    throws Exception {

        final Optional<String> input = Optional.ofNullable(nullableInput);
        final Optional<Integer> output = Optionals.throwingApply(input, transform);
        final Optional<Integer> expectedOutput = Optional.ofNullable(nullableExpectedOutput);
        Assert.assertEquals(output, expectedOutput);
    }

    private static class _Exception
        extends Exception {
    }

    @Test(expectedExceptions = _Exception.class)
    public void throwingApply_FailWhenTransformThrows()
    throws Exception {

        Optionals.throwingApply(Optional.of("abc"), (String s) -> {
            throw new _Exception();
        });
    }
}
