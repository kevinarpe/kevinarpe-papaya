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

import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.protocol.types.runtime.Evaluate;
import com.github.kklisura.cdt.protocol.types.runtime.RemoteObject;
import com.github.kklisura.cdt.protocol.types.runtime.RemoteObjectType;
import com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.util.ChromeDevToolsTestGlobals;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Other tests: ChromeDevToolsDomQuerySelectorImpTest
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ChromeDevToolsRuntimeServiceImpTest {

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^Failed to evaluate JavaScript expression .*$")
    public void runJavaScriptExpression_FailWhenRuntimeThrows()
    throws Exception {

        final Runtime mockRuntime = Mockito.mock(Runtime.class);

        final RuntimeException runtimeException = new RuntimeException("blah");
        Mockito.when(mockRuntime.evaluate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
            .thenThrow(runtimeException);

        final ChromeDevToolsRuntimeServiceImp classUnderTest =
            new ChromeDevToolsRuntimeServiceImp(ChromeDevToolsTestGlobals.exceptionThrower);

        try {
            classUnderTest.runJavaScriptExpression(
                mockRuntime, "javaScriptExpression", IncludeCommandLineAPI.YES);
        }
        catch (Exception e) {

            Assert.assertSame(e.getCause(), runtimeException);
            throw e;
        }
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^.*Expected result type.*$")
    public void evaluateJavaScriptExpression_FailWhenValueCastThrows()
    throws Exception {

        final ChromeDevToolsRuntimeServiceImp.ValueCastService mockValueCastService =
            Mockito.mock(ChromeDevToolsRuntimeServiceImp.ValueCastService.class);

        final Exception exception = new Exception("blah");
        Mockito.when(mockValueCastService.cast(Mockito.any(), Mockito.any())).thenThrow(exception);

        final ChromeDevToolsRuntimeServiceImp classUnderTest =
            new ChromeDevToolsRuntimeServiceImp(mockValueCastService, ChromeDevToolsTestGlobals.exceptionThrower);

        final Evaluate eval = new Evaluate();
        final RemoteObject ro = new RemoteObject();
        eval.setResult(ro);
        ro.setType(RemoteObjectType.STRING);
        ro.setValue("abc");

        final Runtime mockRuntime = Mockito.mock(Runtime.class);

        Mockito.when(mockRuntime.evaluate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
            .thenReturn(eval);

        try {
            classUnderTest.evaluateJavaScriptExpression(
                mockRuntime,
                "javaScriptExpression",
                IncludeCommandLineAPI.YES,
                ChromeDevToolsJavaScriptRemoteObjectType.STRING,
                IsNullResultAllowed.NO);
        }
        catch (Exception e) {

            Assert.assertSame(e.getCause(), exception);
            throw e;
        }
    }
}
