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

import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.function.ThrowingConsumer;
import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;
import com.googlecode.kevinarpe.papaya.function.ThrowingSupplier;
import com.googlecode.kevinarpe.papaya.function.count.CountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

import javax.annotation.Nullable;
import java.util.List;

/**
 * FullyTested?  Some code below is impossible to test in a deterministic manner.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Multiple global singletons
@FullyTested
public final class ChromeDevToolsDomQuerySelectorImp
implements ChromeDevToolsDomQuerySelector {

    private static final int INVALID_PARENT_NODE_ID = ChromeDevToolsDomNodes.MIN_NODE_ID - 1;
    private static final int DOCUMENT_NODE_ID = INVALID_PARENT_NODE_ID - 1;

    private final ChromeDevToolsTab chromeTab;
    private final ChromeDevToolsDomNodeFactory chromeDevToolsDomNodeFactory;
    private final RetryService retryService;
    private final ExceptionThrower exceptionThrower;

    private int parentNodeId;
    @Nullable
    private CountMatcher nullableExpectedCountMatcher;

    public ChromeDevToolsDomQuerySelectorImp(ChromeDevToolsTab chromeTab,
                                             ChromeDevToolsDomNodeFactory chromeDevToolsDomNodeFactory,
                                             RetryService retryService,
                                             ExceptionThrower exceptionThrower) {

        this.chromeTab = ObjectArgs.checkNotNull(chromeTab, "chromeTab");

        this.chromeDevToolsDomNodeFactory =
            ObjectArgs.checkNotNull(chromeDevToolsDomNodeFactory, "domNodeFactory");

        this.retryService = ObjectArgs.checkNotNull(retryService, "retryService");

        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");

        this.parentNodeId = INVALID_PARENT_NODE_ID;
        this.nullableExpectedCountMatcher = null;
    }

    private void
    _assertAllSet() {

        if (INVALID_PARENT_NODE_ID == this.parentNodeId) {
            throw new IllegalStateException("Parent node is unset");
        }
        if (null == nullableExpectedCountMatcher) {
            throw new IllegalStateException("Expected count is unset");
        }
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public ChromeDevToolsDomQuerySelector
    parentNodeIsDocument() {

        this.parentNodeId = DOCUMENT_NODE_ID;
        return this;
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public ChromeDevToolsDomQuerySelector
    parentNodeId(final int parentNodeId) {

        this.parentNodeId = ChromeDevToolsDomNodes.checkNodeId(parentNodeId, "parentNodeId");
        return this;
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public ChromeDevToolsDomQuerySelector
    expectedCount(CountMatcher expectedCountMatcher) {

        this.nullableExpectedCountMatcher = ObjectArgs.checkNotNull(expectedCountMatcher, "expectedCountMatcher");
        return this;
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public ChromeDevToolsDomNode
    querySelectorExactlyOne(String cssSelector)
    throws Exception {

        _expectExactlyOne();
        _assertAllSet();
        @ReadOnlyContainer
        final List<Integer> nodeIdList = _querySelectorAll(cssSelector);
        final Integer nodeId = nodeIdList.get(0);
        final ChromeDevToolsDomNode node = chromeDevToolsDomNodeFactory.newInstance(nodeId, chromeTab);
        return node;
    }

    private void
    _expectExactlyOne() {

        if (null == nullableExpectedCountMatcher) {
            expectedCount(ExactlyCountMatcher.ONE);
        }
        else {
            if (false == ExactlyCountMatcher.ONE.equals(nullableExpectedCountMatcher)) {

                throw exceptionThrower.throwRuntimeException(IllegalStateException.class,
                    "expectedCountMatcher is already set: %s", nullableExpectedCountMatcher);
            }
        }
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public ChromeDevToolsDomNode
    awaitQuerySelectorExactlyOne(String cssSelector,
                                 RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        _expectExactlyOne();
        _assertAllSet();
        @ReadOnlyContainer
        final List<Integer> nodeIdList = _awaitQuerySelectorAll(cssSelector, retryStrategyFactory);
        final Integer nodeId = nodeIdList.get(0);
        final ChromeDevToolsDomNode node = chromeDevToolsDomNodeFactory.newInstance(nodeId, chromeTab);
        return node;
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public ChromeDevToolsDomNode
    awaitQuerySelectorExactlyOneThenRun(String cssSelector,
                                        RetryStrategyFactory retryStrategyFactory,
                                        ThrowingConsumer<ChromeDevToolsDomNode> thenRun)
    throws Exception {

        _expectExactlyOne();
        _assertAllSet();

        final ChromeDevToolsDomNode x =
            _awaitQuerySelectorByIndexThenRun(
                () -> awaitQuerySelectorExactlyOne(cssSelector, retryStrategyFactory),
                () -> querySelectorExactlyOne(cssSelector),
                thenRun);
        return x;
    }

    // package-private for testing
    static final String COULD_NOT_FIND_NODE_WITH_GIVEN_ID = "Could not find node with given id";

    /** {@inheritDoc} */
    @Blocking
    private ChromeDevToolsDomNode
    _awaitQuerySelectorByIndexThenRun(ThrowingSupplier<ChromeDevToolsDomNode> blockingAwaitDomNode,
                                      ThrowingSupplier<ChromeDevToolsDomNode> nonBlockingGetDomNode,
                                      ThrowingConsumer<ChromeDevToolsDomNode> thenRun)
    throws Exception {

        @Blocking
        final ChromeDevToolsDomNode domNode = blockingAwaitDomNode.get();
        try {
            // Possible outcomes:
            // (1) accept() succeeds
            // (2) accept() fails/throws
            // (2a) accept() throws ChromeDevToolsInvocationException: Could not find node with given id
            //      Action: Again query select, then call accept()
            // (2b) accept() throws ChromeDevToolsInvocationException: <another message>
            //      Action: Rethrow
            // (2c) accept() throws another exception type
            //      Action: Do not catch
            thenRun.accept(domNode);
            return domNode;
        }
        /*
com.github.kklisura.cdt.services.exceptions.ChromeDevToolsInvocationException: Could not find node with given id
at com.github.kklisura.cdt.services.impl.ChromeDevToolsServiceImpl.invoke(ChromeDevToolsServiceImpl.java:172)
at com.github.kklisura.cdt.services.invocation.CommandInvocationHandler.invoke(CommandInvocationHandler.java:87)
at com.sun.proxy.$Proxy3.focus(Unknown Source)
at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeDevToolsDomNodeImp.focus(ChromeDevToolsDomNodeImp.java:50)
at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeDevToolsDomNodeImp.awaitFocus(ChromeDevToolsDomNodeImp.java:63)
at com.kevinarpe.hkpl.web.HkplWebLoginServiceImp$1.onEvent(HkplWebLoginServiceImp.java:89)
at com.kevinarpe.hkpl.web.HkplWebLoginServiceImp$1.onEvent(HkplWebLoginServiceImp.java:66)
at com.github.kklisura.cdt.services.impl.ChromeDevToolsServiceImpl.lambda$handleEvent$1(ChromeDevToolsServiceImpl.java:306)
at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
at java.base/java.lang.Thread.run(Thread.java:834)
         */
        catch (Exception e) {
            @Nullable
            final String nullableMessage = e.getMessage();
            if (COULD_NOT_FIND_NODE_WITH_GIVEN_ID.equals(nullableMessage)) {
                try {
                    @NonBlocking
                    final ChromeDevToolsDomNode domNode2 = nonBlockingGetDomNode.get();
                    thenRun.accept(domNode2);
                    return domNode2;
                }
                catch (Exception e2) {
                    e2.addSuppressed(e);
                    throw e2;
                }
            }
            else {
                throw e;
            }
        }
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public <TValue>
    TValue
    awaitQuerySelectorExactlyOneThenCall(String cssSelector,
                                         RetryStrategyFactory retryStrategyFactory,
                                         ThrowingFunction<ChromeDevToolsDomNode, TValue> thenCall)
    throws Exception {

        _expectExactlyOne();
        _assertAllSet();

        final TValue x =
            _awaitQuerySelectorByIndexThenCall(
                () -> awaitQuerySelectorExactlyOne(cssSelector, retryStrategyFactory),
                () -> querySelectorExactlyOne(cssSelector),
                thenCall);
        return x;
    }

    /** {@inheritDoc} */
    @Blocking
    private <TValue>
    TValue
    _awaitQuerySelectorByIndexThenCall(ThrowingSupplier<ChromeDevToolsDomNode> blockingAwaitDomNode,
                                       ThrowingSupplier<ChromeDevToolsDomNode> nonBlockingGetDomNode,
                                       ThrowingFunction<ChromeDevToolsDomNode, TValue> thenCall)
    throws Exception {

        @Blocking
        final ChromeDevToolsDomNode domNode = blockingAwaitDomNode.get();
        try {
            // Possible outcomes:
            // (1) apply() succeeds
            // (2) apply() fails/throws
            // (2a) apply() throws ChromeDevToolsInvocationException: Could not find node with given id
            //      Action: Again query select, then call apply()
            // (2b) apply() throws ChromeDevToolsInvocationException: <another message>
            //      Action: Rethrow
            // (2c) apply() throws another exception type
            //      Action: Do not catch
            final TValue x = thenCall.apply(domNode);
            return x;
        }
        /*
com.github.kklisura.cdt.services.exceptions.ChromeDevToolsInvocationException: Could not find node with given id
at com.github.kklisura.cdt.services.impl.ChromeDevToolsServiceImpl.invoke(ChromeDevToolsServiceImpl.java:172)
at com.github.kklisura.cdt.services.invocation.CommandInvocationHandler.invoke(CommandInvocationHandler.java:87)
at com.sun.proxy.$Proxy3.focus(Unknown Source)
at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeDevToolsDomNodeImp.focus(ChromeDevToolsDomNodeImp.java:50)
at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeDevToolsDomNodeImp.awaitFocus(ChromeDevToolsDomNodeImp.java:63)
at com.kevinarpe.hkpl.web.HkplWebLoginServiceImp$1.onEvent(HkplWebLoginServiceImp.java:89)
at com.kevinarpe.hkpl.web.HkplWebLoginServiceImp$1.onEvent(HkplWebLoginServiceImp.java:66)
at com.github.kklisura.cdt.services.impl.ChromeDevToolsServiceImpl.lambda$handleEvent$1(ChromeDevToolsServiceImpl.java:306)
at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
at java.base/java.lang.Thread.run(Thread.java:834)
         */
        catch (Exception e) {
            @Nullable
            final String nullableMessage = e.getMessage();
            if (COULD_NOT_FIND_NODE_WITH_GIVEN_ID.equals(nullableMessage)) {
                try {
                    @NonBlocking
                    final ChromeDevToolsDomNode domNode2 = nonBlockingGetDomNode.get();
                    final TValue x = thenCall.apply(domNode2);
                    return x;
                }
                catch (Exception e2) {
                    e2.addSuppressed(e);
                    throw e2;
                }
            }
            else {
                throw e;
            }
        }
    }

    /** {@inheritDoc} */
    @NonBlocking
    @EmptyContainerAllowed
    @Override
    public ImmutableList<ChromeDevToolsDomNode>
    querySelectorAll(String cssSelector)
    throws Exception {

        _assertAllSet();
        @ReadOnlyContainer
        final List<Integer> nodeIdList = _querySelectorAll(cssSelector);
        final ImmutableList<ChromeDevToolsDomNode> x = _createList(nodeIdList);
        return x;
    }

    /** {@inheritDoc} */
    @Blocking
    @EmptyContainerAllowed
    @Override
    public ImmutableList<ChromeDevToolsDomNode>
    awaitQuerySelectorAll(String cssSelector,
                          RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        _assertAllSet();
        @ReadOnlyContainer
        final List<Integer> nodeIdList = _awaitQuerySelectorAll(cssSelector, retryStrategyFactory);
        final ImmutableList<ChromeDevToolsDomNode> x = _createList(nodeIdList);
        return x;
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public ChromeDevToolsDomNode
    querySelectorByIndex(String cssSelector,
                         final int index)
    throws Exception {

        _assertAllSet();
        @ReadOnlyContainer
        final List<Integer> nodeIdList = _querySelectorAll(cssSelector);
        final ChromeDevToolsDomNode x = _getDomNode(index, nodeIdList);
        return x;
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public ChromeDevToolsDomNode
    awaitQuerySelectorByIndex(String cssSelector,
                              final int index,
                              RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        _assertAllSet();
        @ReadOnlyContainer
        final List<Integer> nodeIdList = _awaitQuerySelectorAll(cssSelector, retryStrategyFactory);
        final ChromeDevToolsDomNode x = _getDomNode(index, nodeIdList);
        return x;
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public ChromeDevToolsDomNode
    awaitQuerySelectorByIndexThenRun(String cssSelector,
                                     final int index,
                                     RetryStrategyFactory retryStrategyFactory,
                                     ThrowingConsumer<ChromeDevToolsDomNode> thenRun)
    throws Exception {

        _assertAllSet();

        final ChromeDevToolsDomNode x =
            _awaitQuerySelectorByIndexThenRun(
                () -> awaitQuerySelectorByIndex(cssSelector, index, retryStrategyFactory),
                () -> querySelectorByIndex(cssSelector, index),
                thenRun);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public <TValue>
    TValue
    awaitQuerySelectorByIndexThenCall(String cssSelector,
                                      final int index,
                                      RetryStrategyFactory retryStrategyFactory,
                                      ThrowingFunction<ChromeDevToolsDomNode, TValue> thenCall)
    throws Exception {

        _assertAllSet();

        final TValue x =
            _awaitQuerySelectorByIndexThenCall(
                () -> awaitQuerySelectorByIndex(cssSelector, index, retryStrategyFactory),
                () -> querySelectorByIndex(cssSelector, index),
                thenCall);
        return x;
    }

    @ReadOnlyContainer
    private List<Integer>
    _querySelectorAll(String cssSelector)
    throws Exception {

        @ReadOnlyContainer
        @EmptyContainerAllowed
        final List<Integer> nodeIdList = _querySelectorAll0(cssSelector);

        final int count = nodeIdList.size();
        if (false == nullableExpectedCountMatcher.isMatch(count)) {

            throw exceptionThrower.throwCheckedException(Exception.class,
                "CSS Selector [%s]: Expected %s node(s), but found %d node(s)", cssSelector, nullableExpectedCountMatcher, count);
        }
        return nodeIdList;
    }

    @ReadOnlyContainer
    @EmptyContainerAllowed
    private List<Integer>
    _querySelectorAll0(String cssSelector)
    throws Exception {

        StringArgs.checkNotEmptyOrWhitespace(cssSelector, "cssSelector");

        final int parentNodeId2 = _getParentNodeId();
        try {
            @ReadOnlyContainer
            @EmptyContainerAllowed
            final List<Integer> nodeIdList = chromeTab.getDOM().querySelectorAll(parentNodeId2, cssSelector);
            return nodeIdList;
        }
        catch (Exception e) {
            throw exceptionThrower.throwChainedCheckedException(Exception.class,
                e,
                "CSS Selector [%s]: Unexpected error", cssSelector);
        }
    }

    private int
    _getParentNodeId() {

        if (DOCUMENT_NODE_ID == parentNodeId) {

            // Note: This can throw a runtime exception.
            final Node documentNode = chromeTab.getDOM().getDocument();
            final Integer x = documentNode.getNodeId();
            return x;
        }
        else {
            return parentNodeId;
        }
    }

    private ImmutableList<ChromeDevToolsDomNode>
    _createList(List<Integer> nodeIdList) {

        final ImmutableList.Builder<ChromeDevToolsDomNode> b = ImmutableList.builder();
        for (final int nodeId : nodeIdList) {

            final ChromeDevToolsDomNode node = chromeDevToolsDomNodeFactory.newInstance(nodeId, chromeTab);
            b.add(node);
        }
        final ImmutableList<ChromeDevToolsDomNode> x = b.build();
        return x;
    }

    @ReadOnlyContainer
    private List<Integer>
    _awaitQuerySelectorAll(String cssSelector,
                           RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        @ReadOnlyContainer
        final List<Integer> x =
            retryService.call(retryStrategyFactory,
                () -> _querySelectorAll(cssSelector));

        return x;
    }

    private ChromeDevToolsDomNode
    _getDomNode(final int index, List<Integer> nodeIdList) {

        CollectionArgs.checkAccessIndex(nodeIdList, index, "nodeIdList", "index");
        final int nodeId = nodeIdList.get(index);
        final ChromeDevToolsDomNode x = chromeDevToolsDomNodeFactory.newInstance(nodeId, chromeTab);
        return x;
    }
}
