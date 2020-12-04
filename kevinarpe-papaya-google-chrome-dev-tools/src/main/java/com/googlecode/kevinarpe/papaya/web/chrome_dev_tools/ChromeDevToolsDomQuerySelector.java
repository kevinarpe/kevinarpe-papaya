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
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.function.ThrowingConsumer;
import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;
import com.googlecode.kevinarpe.papaya.function.count.CountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategy;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

/**
 * This is a service to help your build a DOM query selector.
 * <ol>
 *     <li>Call either {@link #parentNodeIsDocument()} or {@link #parentNodeId(int)}</li>
 *     <li>Call {@link #expectedCount(CountMatcher)}</li>
 * </ol>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ChromeDevToolsDomQuerySelector {

    /**
     * Sets the parent node as the DOM document.  However, this is not queried and set immediately.  Why?  The DOM
     * document node ID is a tricky thing!  It might change while JavaScript is modifying the DOM node tree.  Thus, an
     * internal flag is used to indicate the parent should should be DOM document.  When a parent node is required --
     * when {@link #querySelectorAll(String)} and friends are called -- the DOM document node ID is queried.  This
     * implementation details leads to fewer runtime errors when using {@link RetryStrategy}.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #parentNodeId(int)
     * @see #expectedCount(CountMatcher)
     */
    @NonBlocking
    ChromeDevToolsDomQuerySelector
    parentNodeIsDocument();

    /**
     * Sets the parent node using a specific integer node ID.  To be clear: This is risky!  DOM node IDs change
     * frequently, while JavaScript is modifying the DOM node tree.  Instead, the safest option is to call:
     * {@link #parentNodeIsDocument()}.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #parentNodeIsDocument()
     * @see #expectedCount(CountMatcher)
     */
    @NonBlocking
    ChromeDevToolsDomQuerySelector
    parentNodeId(int parentNodeId);

    /**
     * Required: Sets the expected count of nodes from {@link DOM#querySelectorAll(Integer, String)}.
     * <p>
     * Exception: It is not required to call this method before {@link #querySelectorExactlyOne(String)}
     * or {@link #awaitQuerySelectorExactlyOne(String, RetryStrategyFactory)}.
     *
     * @param expectedCount
     *        matcher for expected count
     *
     * @return self for fluent interface / method chaining
     */
    @NonBlocking
    ChromeDevToolsDomQuerySelector
    expectedCount(CountMatcher expectedCount);

    /**
     * Selects exactly one DOM node using {@code cssSelector}.  It is not required to call
     * {@link #expectedCount(CountMatcher)} before this method.
     * <p>
     * Callers should always prefer {@link #awaitQuerySelectorExactlyOne(String, RetryStrategyFactory)} where possible.
     *
     * @param cssSelector
     *        Ex: {@code "#input_text_username"}
     *        <br>See: <a href="https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelectorAll"
     *             >MDN: Document.querySelectorAll</a>
     *
     * @return selected DOM node
     *
     * @throws IllegalStateException
     *         if parent node is unset
     *         <br>if {@link #expectedCount(CountMatcher)} called before this method where {@link CountMatcher} does
     *         <b>not</b> equal {@link ExactlyCountMatcher#ONE}
     *
     * @throws Exception
     *         if zero or more than one DOM nodes are selected
     */
    @NonBlocking
    ChromeDevToolsDomNode
    querySelectorExactlyOne(String cssSelector)
    throws Exception;

    /**
     * This is a convenience method to call {@link #querySelectorExactlyOne(String)} with a retry strategy.
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         if zero or more than one DOM nodes are selected
     *         <br>or, thrown from {@link RetryStrategy#beforeRetry()}
     */
    @Blocking
    ChromeDevToolsDomNode
    awaitQuerySelectorExactlyOne(String cssSelector,
                                 RetryStrategyFactory retryStrategyFactory)
    throws Exception;

    /**
     * This is a convenience method to call {@link #querySelectorExactlyOne(String)} with a retry strategy,
     * then run {@code thenRun} with selected node.
     *
     * @param thenRun
     *        Ex: {@code (ChromeDevToolsDomNode n) -> n.focus()}
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         if zero or more than one DOM nodes are selected
     *         <br>or, thrown from {@link RetryStrategy#beforeRetry()}
     */
    @Blocking
    ChromeDevToolsDomNode
    awaitQuerySelectorExactlyOneThenRun(String cssSelector,
                                        RetryStrategyFactory retryStrategyFactory,
                                        ThrowingConsumer<ChromeDevToolsDomNode> thenRun)
    throws Exception;

    /**
     * This is a convenience method to call {@link #querySelectorExactlyOne(String)} with a retry strategy,
     * then run {@code thenCall} with selected node and return result.
     *
     * @param thenCall
     *        Ex: {@code (ChromeDevToolsDomNode n) -> n.getOuterHTML()}
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         if zero or more than one DOM nodes are selected
     *         <br>or, thrown from {@link RetryStrategy#beforeRetry()}
     */
    @Blocking
    <TValue>
    TValue
    awaitQuerySelectorExactlyOneThenCall(String cssSelector,
                                         RetryStrategyFactory retryStrategyFactory,
                                         ThrowingFunction<ChromeDevToolsDomNode, TValue> thenCall)
    throws Exception;

    /**
     * Selects zero or more DOM nodes using {@code cssSelector}.
     * <p>
     * Callers should always prefer {@link #awaitQuerySelectorAll(String, RetryStrategyFactory)} where possible.
     *
     * @param cssSelector
     *        Ex: {@code "#input_text_username"}
     *        <br>See: <a href="https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelectorAll"
     *             >MDN: Document.querySelectorAll</a>
     *
     * @return zero or more selected nodes
     *
     * @throws IllegalStateException
     *         if parent node is unset
     *         <br>if expected count is unset
     *
     * @throws Exception
     *         if number of DOM nodes does not match {@link #expectedCount(CountMatcher)}
     *
     * @see #parentNodeIsDocument()
     * @see #parentNodeId(int)
     * @see #expectedCount(CountMatcher)
     */
    @NonBlocking
    @EmptyContainerAllowed
    ImmutableList<ChromeDevToolsDomNode>
    querySelectorAll(String cssSelector)
    throws Exception;

    /**
     * This is a convenience method to call {@link #querySelectorAll(String)} with a retry strategy.
     *
     * @param cssSelector
     *        Ex: {@code "#input_text_username"}
     *        <br>See: <a href="https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelectorAll"
     *             >MDN: Document.querySelectorAll</a>
     *
     * @return zero or more selected nodes
     *
     * @throws Exception
     *         if number of DOM nodes does not match {@link #expectedCount(CountMatcher)}
     */
    @Blocking
    @EmptyContainerAllowed
    ImmutableList<ChromeDevToolsDomNode>
    awaitQuerySelectorAll(String cssSelector,
                          RetryStrategyFactory retryStrategyFactory)
    throws Exception;

    /**
     * Selects a DOM node by index using {@code cssSelector}.
     * <p>
     * Callers should always prefer {@link #awaitQuerySelectorByIndex(String, int, RetryStrategyFactory)} where possible.
     *
     * @param cssSelector
     *        Ex: {@code "#input_text_username"}
     *        <br>See: <a href="https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelectorAll"
     *             >MDN: Document.querySelectorAll</a>
     *
     * @param index
     *        zero-based index to select DOM node from a list
     *
     * @return selected DOM node
     *
     * @throws IllegalStateException
     *         if parent node is unset
     *         <br>if expected count is unset
     *
     * @throws Exception
     *         if {@code index} is missing from selected DOM node list
     *
     * @see #parentNodeIsDocument()
     * @see #parentNodeId(int)
     * @see #expectedCount(CountMatcher)
     */
    @NonBlocking
    ChromeDevToolsDomNode
    querySelectorByIndex(String cssSelector, int index)
    throws Exception;

    /**
     * This is a convenience method to call {@link #querySelectorByIndex(String, int)} with a retry strategy.
     *
     * @param cssSelector
     *        Ex: {@code "#input_text_username"}
     *        <br>See: <a href="https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelectorAll"
     *             >MDN: Document.querySelectorAll</a>
     *
     * @param index
     *        zero-based index to select DOM node from a list
     *
     * @return selected DOM node
     *
     * @throws Exception
     *         if {@code index} is missing from selected DOM node list
     */
    @Blocking
    ChromeDevToolsDomNode
    awaitQuerySelectorByIndex(String cssSelector,
                              int index,
                              RetryStrategyFactory retryStrategyFactory)
    throws Exception;

    /**
     * This is a convenience method to call {@link #awaitQuerySelectorByIndex(String, int, RetryStrategyFactory)},
     * then call {@code thenRun} with selected node.
     *
     * @param cssSelector
     *        Ex: {@code "#input_text_username"}
     *        <br>See: <a href="https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelectorAll"
     *             >MDN: Document.querySelectorAll</a>
     *
     * @param index
     *        zero-based index to select DOM node from a list
     *
     * @param thenRun
     *        Ex: {@code (ChromeDevToolsDomNode n) -> n.focus()}
     *
     * @return selected DOM node
     *
     * @throws Exception
     *         if {@code index} is missing from selected DOM node list
     */
    @Blocking
    ChromeDevToolsDomNode
    awaitQuerySelectorByIndexThenRun(String cssSelector,
                                     int index,
                                     RetryStrategyFactory retryStrategyFactory,
                                     ThrowingConsumer<ChromeDevToolsDomNode> thenRun)
    throws Exception;

    /**
     * This is a convenience method to call {@link #awaitQuerySelectorByIndex(String, int, RetryStrategyFactory)},
     * then run {@code thenCall} with selected node and return result.
     *
     * @param thenCall
     *        Ex: {@code (ChromeDevToolsDomNode n) -> n.getOuterHTML()}
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         if zero or more than one DOM nodes are selected
     *         <br>or, thrown from {@link RetryStrategy#beforeRetry()}
     */
    @Blocking
    <TValue>
    TValue
    awaitQuerySelectorByIndexThenCall(String cssSelector,
                                      int index,
                                      RetryStrategyFactory retryStrategyFactory,
                                      ThrowingFunction<ChromeDevToolsDomNode, TValue> thenCall)
    throws Exception;
}
