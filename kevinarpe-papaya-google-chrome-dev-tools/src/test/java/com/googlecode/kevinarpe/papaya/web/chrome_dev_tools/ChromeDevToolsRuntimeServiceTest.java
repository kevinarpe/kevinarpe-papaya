package com.googlecode.kevinarpe.papaya.web.chrome_dev_tools;

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

import com.googlecode.kevinarpe.papaya.lang.Numbers;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ChromeDevToolsRuntimeServiceTest
extends ChromeDevToolsTest {

    @Test
    public void pass_eval()
    throws Exception {

        final Number number =
            appContext().chromeDevToolsRuntimeService.evaluateJavaScriptExpression(
                chrome().chromeTab0.getRuntime(),
                "1 + 2",
                IncludeCommandLineAPI.NO,
                ChromeDevToolsJavaScriptRemoteObjectType.NUMBER,
                IsNullResultAllowed.NO);

        final long value = Numbers.longValue(number);
        Assert.assertEquals(value, 3);
    }

    private static final String EXPECTED_EXCEPTIONS_MESSAGE_REG_EXP = "^.* Error: error_message\n.*$";

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = EXPECTED_EXCEPTIONS_MESSAGE_REG_EXP)
    public void fail_evalWhenThrowError()
    throws Exception {

        appContext().chromeDevToolsRuntimeService.evaluateJavaScriptExpression(
            chrome().chromeTab0.getRuntime(),
            "throw new Error(\"error_message\")",
            IncludeCommandLineAPI.NO,
            ChromeDevToolsJavaScriptRemoteObjectType.OBJECT,
            IsNullResultAllowed.NO);
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^.*\nExpected result type RemoteObjectType.STRING, but found NUMBER:.*$")
    public void fail_evalWhenWrongType()
    throws Exception {

        appContext().chromeDevToolsRuntimeService.evaluateJavaScriptExpression(
            chrome().chromeTab0.getRuntime(),
            "1 + 2",
            IncludeCommandLineAPI.NO,
            ChromeDevToolsJavaScriptRemoteObjectType.STRING,
            IsNullResultAllowed.NO);
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^.*\nUnexpected null result$")
    public void fail_evalWhenNullResult()
    throws Exception {

        appContext().chromeDevToolsRuntimeService.evaluateJavaScriptExpression(
            chrome().chromeTab0.getRuntime(),
            "undefined",
            IncludeCommandLineAPI.NO,
            ChromeDevToolsJavaScriptRemoteObjectType.UNDEFINED,
            IsNullResultAllowed.NO);
    }

    @Test
    public void pass_run()
    throws Exception {

        appContext().chromeDevToolsRuntimeService.runJavaScriptExpression(
            chrome().chromeTab0.getRuntime(),
            "1 + 2",
            IncludeCommandLineAPI.NO);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = EXPECTED_EXCEPTIONS_MESSAGE_REG_EXP)
    public void fail_runWhenThrowError()
    throws Exception {

        appContext().chromeDevToolsRuntimeService.runJavaScriptExpression(
            chrome().chromeTab0.getRuntime(),
            "throw new Error(\"error_message\")",
            IncludeCommandLineAPI.NO);
    }
}
