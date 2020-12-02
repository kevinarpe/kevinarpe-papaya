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

import com.github.kklisura.cdt.protocol.commands.DOM;
import com.github.kklisura.cdt.protocol.commands.Runtime;

import javax.annotation.Nullable;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ChromeDevToolsRuntimeService {

    /**
     * Evaluates a JavaScript expression using an instance of {@link Runtime}, then return result as a Java type.
     * If the return value is void, null, or not important,
     * see {@link #runJavaScriptExpression(Runtime, String, IncludeCommandLineAPI)}.
     * <p>
     * If the expression throws a JavaScript exception, it is rethrown as a Java exception.
     * <p>
     * This Chrome DevTools Protocol feature is tricky.  Please closely read the parameter docs below!
     *
     * @param runtime
     *        instance of {@link Runtime} associated with a Chrome tab
     *        <br>See: {@link ChromeDevToolsTab.Data#runtime}
     *
     * @param javaScriptExpression
     *        may require some experimentation to correctly return a value
     *        <br>Must not be empty or all whitespace.
     *        <br>Ex: {@code "document.title"} will return a non-null {@code String}
     *        <br>Ex: {@code "7"} will always (and strangely) return null.
     *        <br>... however, {@code "(function(){return 7;})()"} will return {@code Integer: 7}.
     *        <br>For JavaScript expressions that do not return a value, such as
     *        <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/click">HTMLElement.click()</a>,
     *        use {@link ChromeDevToolsJavaScriptRemoteObjectType#OBJECT} and {@link IsNullResultAllowed#YES}.
     *
     * @param includeCommandLineAPI
     *        if {@link IncludeCommandLineAPI#YES}, then {@code javaScriptExpression} may contain token {@code $0} which
     *        is an alias to the current "inspected DOM node" in Chrome DevTools.
     *        <br>See: {@link DOM#setInspectedNode(Integer)}
     *        <br>See: <a href="https://chromedevtools.github.io/devtools-protocol/tot/DOM/#method-setInspectedNode"
     *                 >Chrome DevTools Protocol: DOM.setInspectedNode</a>
     *
     * @param valueType
     *        may values types, e.g., {@link ChromeDevToolsJavaScriptRemoteObjectType#OBJECT}, are useless as they will
     *        always return {@code null}
     *        <br>See notes on each remote object type here {@link ChromeDevToolsJavaScriptRemoteObjectType}
     *        and learn more about which type is {@link Void}
     *        and {@link ChromeDevToolsJavaScriptRemoteObjectType#isAlwaysNull}.
     *
     * @param isNullResultAllowed
     *        in most cases, it would be surprising to evaluate JavaScript, then receive a {@code null} result.
     *        <br>To throw when result is {@code null}, use {@link IsNullResultAllowed#NO}.
     *
     * @return result of {@code javaScriptExpression} as a Java type
     *
     * @throws IllegalArgumentException
     *         if {@code ref} is empty or only whitespace
     *
     * @throws Exception
     *         if {@code javaScriptExpression} throws an exception, including due to syntax error
     *
     * @see #runJavaScriptExpression(Runtime, String, IncludeCommandLineAPI)
     */
    @Nullable
    <TValue>
    TValue evaluateJavaScriptExpression(Runtime runtime,
                                        String javaScriptExpression,
                                        IncludeCommandLineAPI includeCommandLineAPI,
                                        ChromeDevToolsJavaScriptRemoteObjectType<TValue> valueType,
                                        IsNullResultAllowed isNullResultAllowed)
    throws Exception;

    /**
     * This is a convenience method to call
     * {@link #evaluateJavaScriptExpression(Runtime, String, IncludeCommandLineAPI, ChromeDevToolsJavaScriptRemoteObjectType, IsNullResultAllowed)}
     * and ignore the result, unless an exception was thrown.
     */
    void runJavaScriptExpression(Runtime runtime,
                                 String javaScriptExpression,
                                 IncludeCommandLineAPI includeCommandLineAPI)
    throws Exception;
}
