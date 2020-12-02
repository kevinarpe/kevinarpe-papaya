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
import com.github.kklisura.cdt.protocol.commands.Input;
import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.protocol.types.input.DispatchKeyEventType;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategy;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

import javax.annotation.Nullable;

/**
 * This interface wraps a single DOM node identified via {@link #nodeId()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ChromeDevToolsDomNode {

    /**
     * @return identifier for a single DOM node
     *
     * @see DOM#querySelector(Integer, String)
     * @see <a href="https://chromedevtools.github.io/devtools-protocol/tot/DOM/#method-querySelector"
     *      >Chrome DevTools Protocol: DOM.querySelector</a>
     */
    int nodeId();

    /**
     * @return Google Chrome tab associated with this DOM node
     */
    ChromeDevToolsTab
    chromeTab();

    /**
     * Focus on the DOM node.  This method calls {@link DOM#focus(Integer, Integer, String)} with {@link #nodeId()}.
     * <p>
     * Callers should always prefer {@link #awaitFocus(RetryStrategyFactory)} where possible.
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         if {@link #nodeId()} is no longer valid
     *         <br>if DOM node cannot accept focus ever or right
     *
     * @see #awaitFocus(RetryStrategyFactory)
     * @see ChromeDevToolsDomQuerySelector#awaitQuerySelectorByIndexThenFocus(String, int, RetryStrategyFactory)
     */
    @NonBlocking
    ChromeDevToolsDomNode
    focus()
    throws Exception;

    /**
     * This is a convenience method to call {@link #focus()} with a retry strategy.
     * <p>
     * In practice, it is safer to call
     * {@link ChromeDevToolsDomQuerySelector#awaitQuerySelectorByIndexThenFocus(String, int, RetryStrategyFactory)},
     * as sometimes {@link #nodeId()} is not stable while the page DOM is modified with JavaScript.  If {@link #nodeId()}
     * has become invalid, no amount of retry will allow DOM node focus!
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         via {@link #focus()} or {@link RetryStrategy#beforeRetry()}
     */
    @Blocking
    ChromeDevToolsDomNode
    awaitFocus(RetryStrategyFactory retryStrategyFactory)
    throws Exception;

    /**
     * This is a convenience method to call {@link ChromeDevToolsInputService#sendKeys(Input, DispatchKeyEventType, String)}
     * with {@link DispatchKeyEventType#KEY_DOWN} and {@code text}.
     * <p>
     * IMPORTANT: The DOM node (HTMLInputElement) <b>must</b> have focus before calling this method.
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         if send key event fails for any characters
     *
     * @throws IllegalArgumentException
     *         if {@code text} is empty
     */
    @Blocking
    ChromeDevToolsDomNode
    sendKeys(String text)
    throws Exception;

    /**
     * Sets the "inspected node" ($0) as the current node ({@link #nodeId()}, then calls
     * {@link ChromeDevToolsRuntimeService#evaluateJavaScriptExpression(Runtime, String, IncludeCommandLineAPI, ChromeDevToolsJavaScriptRemoteObjectType, IsNullResultAllowed)}.
     *
     * @param javaScriptExpression
     *        may contain token {@code $0} which is an alias to the current (inspected) DOM node in Chrome DevTools
     *
     * @param valueType
     *        See: {@link ChromeDevToolsRuntimeService#evaluateJavaScriptExpression(Runtime, String, IncludeCommandLineAPI, ChromeDevToolsJavaScriptRemoteObjectType, IsNullResultAllowed)}
     *
     * @param isNullResultAllowed
     *        See: {@link ChromeDevToolsRuntimeService#evaluateJavaScriptExpression(Runtime, String, IncludeCommandLineAPI, ChromeDevToolsJavaScriptRemoteObjectType, IsNullResultAllowed)}
     *
     * @return result of {@code javaScriptExpression} as a Java type
     *
     * @throws Exception
     *         if the current node fails to be set as "inspected node"
     *         <br>if {@code javaScriptExpression} throws an exception, including due to syntax error
     */
    @Nullable
    <TValue>
    TValue evaluateJavaScriptExpressionWithDollarZero(String javaScriptExpression,
                                                      ChromeDevToolsJavaScriptRemoteObjectType<TValue> valueType,
                                                      IsNullResultAllowed isNullResultAllowed)
    throws Exception;

    /**
     * This is a convenience method to call
     * {@link #evaluateJavaScriptExpressionWithDollarZero(String, ChromeDevToolsJavaScriptRemoteObjectType, IsNullResultAllowed)}
     * where {@code javaScriptExpression} is {@code "$0.click()"}.
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         See: {@link #evaluateJavaScriptExpressionWithDollarZero(String, ChromeDevToolsJavaScriptRemoteObjectType, IsNullResultAllowed)}
     */
    ChromeDevToolsDomNode
    click()
    throws Exception;
}
