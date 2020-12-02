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

import com.github.kklisura.cdt.protocol.types.runtime.RemoteObjectType;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.lang.Numbers;

/**
 * Ref: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/typeof
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ChromeDevToolsJavaScriptRemoteObjectType<TValue> {

    /**
     * Ex: JavaScript expression {@code "(function(){return {};})()"} is an object,
     * but will always return {@code null}.
     * <p>
     * Unfortunately, it does not currently seem possible to return arrays, e.g., {@code [ "abc" ]}.  The Chrome
     * DevTools Protocol will indicate the JavaScript result is an array, but value is always {@code null}.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<Void> OBJECT =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.OBJECT, Void.class);

    /**
     * Ex: JavaScript expression {@code ""(function(){return (function(){});})()"} is a function,
     * but will always return {@code null}.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<Void> FUNCTION =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.FUNCTION, Void.class);

    /**
     * Ex: JavaScript expression {@code "(function(){return undefined;})()"} is an undefined value,
     * but will always return {@code null}.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<Void> UNDEFINED =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.UNDEFINED, Void.class);

    /**
     * Ex: JavaScript expression {@code "(function(){return "abc";})()"} is a string,
     * and will have Java type {@link String}.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<String> STRING =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.STRING, String.class);

    /**
     * Ex: JavaScript expression {@code "(function(){return 7;})()"} is a number, and will have Java type {@link Integer}.
     * <p>
     * Ex: JavaScript expression {@code "(function(){return 2147483647 + 1;})()"} is a number,
     * and will have Java type {@link Long}.  (Note: 2147483647 is equal to {@link Integer#MAX_VALUE}.)
     * <p>
     * Ex: JavaScript expression {@code "(function(){return 7.2;})()"} is a number, and will have Java type {@link Double}.
     * <p>
     * See methods in {@link Numbers} to safely extract a {@code double} or {@code long} value.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<Number> NUMBER =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.NUMBER, Number.class);

    /**
     * Ex: JavaScript expression {@code "(function(){return true;})()"} is a boolean,
     * and will have Java type {@link Boolean}.
     * <p>
     * Ex: JavaScript expression {@code "(function(){return false;})()"} is a boolean,
     * and will have Java type {@link Boolean}.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<Boolean> BOOLEAN =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.BOOLEAN, Boolean.class);

    /**
     * Ex: JavaScript expression {@code "Symbol('foo')"} is a Symbol, but will always return {@code null}.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<Void> SYMBOL =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.SYMBOL, Void.class);

    /**
     * Ex: JavaScript expression {@code "42n"} is a BigInt, but will always return {@code null}.
     */
    public static final ChromeDevToolsJavaScriptRemoteObjectType<Void> BIGINT =
        new ChromeDevToolsJavaScriptRemoteObjectType<>(RemoteObjectType.BIGINT, Void.class);

    public final RemoteObjectType type;
    /**
     * If value class is {@link Void}, then {@link #isAlwaysNull} is {@code true}.
     */
    public final Class<TValue> valueClass;
    /**
     * If {@link #valueClass} is {@link Void}, then always {@code true}.
     */
    public final boolean isAlwaysNull;

    private ChromeDevToolsJavaScriptRemoteObjectType(RemoteObjectType type, Class<TValue> valueClass) {

        this.type = ObjectArgs.checkNotNull(type, "type");
        this.valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
        this.isAlwaysNull = Void.class.equals(valueClass);
    }
}
