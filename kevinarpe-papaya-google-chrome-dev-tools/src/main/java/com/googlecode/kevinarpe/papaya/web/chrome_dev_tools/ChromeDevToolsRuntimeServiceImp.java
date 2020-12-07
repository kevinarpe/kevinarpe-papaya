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
import com.github.kklisura.cdt.protocol.types.runtime.ExceptionDetails;
import com.github.kklisura.cdt.protocol.types.runtime.RemoteObject;
import com.github.kklisura.cdt.protocol.types.runtime.RemoteObjectType;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;

import javax.annotation.Nullable;

/**
 * FullyTested?  Some code below is impossible to test in a deterministic manner.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class ChromeDevToolsRuntimeServiceImp
implements ChromeDevToolsRuntimeService {

    // package-private for testing
    interface ValueCastService {

        <T> T cast(Class<T> valueClass, @Nullable Object obj)
        throws Exception;
    }

    private static final class ValueCastServiceImp
    implements ValueCastService {

        private static final ValueCastServiceImp INSTANCE = new ValueCastServiceImp();

        private ValueCastServiceImp() {
            // Empty
        }

        @Override
        public <T>
        T cast(Class<T> valueClass, @Nullable Object obj)
        throws Exception {

            @Nullable
            final T x = valueClass.cast(obj);
            return x;
        }
    }

    private final ValueCastService valueCastService;
    private final ExceptionThrower exceptionThrower;

    public ChromeDevToolsRuntimeServiceImp(ExceptionThrower exceptionThrower) {

        this(ValueCastServiceImp.INSTANCE, exceptionThrower);
    }

    // package-private for testing
    ChromeDevToolsRuntimeServiceImp(ValueCastService valueCastService, ExceptionThrower exceptionThrower) {

        this.valueCastService = ObjectArgs.checkNotNull(valueCastService, "valueCastService");
        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public <TValue>
    TValue evaluateJavaScriptExpression(Runtime runtime,
                                        String javaScriptExpression,
                                        IncludeCommandLineAPI includeCommandLineAPI,
                                        ChromeDevToolsJavaScriptRemoteObjectType<TValue> valueType,
                                        IsNullResultAllowed isNullResultAllowed)
    throws Exception {

        final Evaluate evaluate = _eval(runtime, javaScriptExpression, includeCommandLineAPI);
        final RemoteObject result = evaluate.getResult();
        final RemoteObjectType type = result.getType();
        if (false == valueType.type.equals(type)) {

            throw exceptionThrower.throwCheckedException(Exception.class,
                "Failed to evaluate expression: [%s]"
                    + "%nExpected result type %s.%s, but found %s: [%s]",
                javaScriptExpression,
                valueType.type.getClass().getSimpleName(), valueType.type.name(), type.name(), result.getValue());
        }
        @Nullable
        final Object nullableValue = result.getValue();

        if (null == nullableValue && IsNullResultAllowed.NO.equals(isNullResultAllowed)) {

            throw exceptionThrower.throwCheckedException(Exception.class,
                "Failed to evaluate expression: [%s]"
                    + "%nUnexpected null result",
                javaScriptExpression,
                valueType.valueClass.getSimpleName());
        }

        try {
            @Nullable
            final TValue x = valueCastService.cast(valueType.valueClass, nullableValue);
            return x;
        }
        catch (Exception e) {

            throw exceptionThrower.throwChainedCheckedException(Exception.class,
                e,
                "Failed to evaluate expression: [%s]"
                    + "%nExpected result type %s, but found %s: [%s]",
                javaScriptExpression,
                valueType.valueClass.getSimpleName(),
                (null == nullableValue) ? null : nullableValue.getClass().getSimpleName(),
                nullableValue);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void
    runJavaScriptExpression(Runtime runtime,
                            String javaScriptExpression,
                            IncludeCommandLineAPI includeCommandLineAPI)
    throws Exception {

        _eval(runtime, javaScriptExpression, includeCommandLineAPI);
    }

    private Evaluate
    _eval(Runtime runtime, String javaScriptExpression, IncludeCommandLineAPI includeCommandLineAPI)
    throws Exception {

        StringArgs.checkNotEmptyOrWhitespace(javaScriptExpression, "javaScriptExpression");

        final Evaluate evaluate = _eval0(runtime, javaScriptExpression, includeCommandLineAPI);
        @Nullable
        final ExceptionDetails ed = evaluate.getExceptionDetails();
        if (null == ed) {
            return evaluate;
        }
        @Nullable
        final RemoteObject e = ed.getException();
        final String message =
            // Note: I have never observed 'e' to be null, but the private data member is marked as @Optional,
            // so we need to very careful.
            (null == e)
                // Ex: "Uncaught"
                ? ed.getText()
                // Ex: "Error: error_message\n    at <anonymous>:1:7"
                : e.getDescription();

        throw exceptionThrower.throwCheckedException(Exception.class,
            "Failed to evaluate JavaScript expression: [%s]"
                + "%nLine %d, Column %d: %s",
            javaScriptExpression, ed.getLineNumber(), ed.getColumnNumber(), message);
    }

    private Evaluate
    _eval0(Runtime runtime, String javaScriptExpression, IncludeCommandLineAPI includeCommandLineAPI)
    throws Exception {

        @Nullable
        final Boolean nullableIncludeCommandLineAPI =
            IncludeCommandLineAPI.YES.equals(includeCommandLineAPI) ? Boolean.TRUE : null;
        try {
            final Evaluate x = runtime.evaluate(javaScriptExpression, null, nullableIncludeCommandLineAPI,
                null, null, null, null, null,
                null, null, null);
            return x;
        }
        catch (Exception e) {
            throw exceptionThrower.throwChainedCheckedException(Exception.class,
                e,
                "Failed to evaluate JavaScript expression (%s.%s): [%s]",
                includeCommandLineAPI.getClass().getSimpleName(), includeCommandLineAPI.name(), javaScriptExpression);
        }
    }
}
