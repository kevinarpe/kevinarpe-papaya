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

import com.github.kklisura.cdt.protocol.types.input.DispatchKeyEventType;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

import javax.annotation.Nullable;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Multiple global singletons
@FullyTested
public final class ChromeDevToolsDomNodeImp
implements ChromeDevToolsDomNode {

    private final int nodeId;
    private final ChromeDevToolsTab chromeTab;
    private final ChromeDevToolsInputService inputService;
    private final ChromeDevToolsRuntimeService runtimeService;
    private final RetryService retryService;

    public ChromeDevToolsDomNodeImp(final int nodeId,
                                    ChromeDevToolsTab chromeTab,
                                    ChromeDevToolsInputService inputService,
                                    ChromeDevToolsRuntimeService runtimeService,
                                    RetryService retryService) {

        this.nodeId = ChromeDevToolsDomNodes.checkNodeId(nodeId, "nodeId");
        this.chromeTab = ObjectArgs.checkNotNull(chromeTab, "chromeTab");
        this.inputService = ObjectArgs.checkNotNull(inputService, "inputService");
        this.runtimeService = ObjectArgs.checkNotNull(runtimeService, "runtimeService");
        this.retryService = ObjectArgs.checkNotNull(retryService, "retryService");
    }

    /** {@inheritDoc} */
    @Override
    public int
    nodeId() {
        return nodeId;
    }

    /** {@inheritDoc} */
    @Override
    public ChromeDevToolsTab
    chromeTab() {
        return chromeTab;
    }

    @Override
    public String
    getOuterHTML()
    throws Exception {

        final String x = chromeTab.getDOM().getOuterHTML(nodeId, null, null);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public ChromeDevToolsDomNode
    focus()
    throws Exception {

        chromeTab.getDOM().focus(nodeId, null, null);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ChromeDevToolsDomNode
    awaitFocus(RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        retryService.run(retryStrategyFactory,
            () -> focus());

        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ChromeDevToolsDomNode
    sendKeys(String text)
    throws Exception {

        inputService.sendKeys(chromeTab.getInput(), DispatchKeyEventType.KEY_DOWN, text);
        return this;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public <TValue>
    TValue evaluateJavaScriptExpressionWithDollarZero(String javaScriptExpression,
                                                      ChromeDevToolsJavaScriptRemoteObjectType<TValue> valueType,
                                                      IsNullResultAllowed isNullResultAllowed)
    throws Exception {

        chromeTab.getDOM().setInspectedNode(nodeId);

        @Nullable
        final TValue x =
            runtimeService.evaluateJavaScriptExpression(
                chromeTab.getRuntime(),
                javaScriptExpression,
                IncludeCommandLineAPI.YES,
                valueType,
                isNullResultAllowed);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public ChromeDevToolsDomNode
    runJavaScriptExpression(String javaScriptExpression)
    throws Exception {

        chromeTab.getDOM().setInspectedNode(nodeId);
        runtimeService.runJavaScriptExpression(chromeTab.getRuntime(), javaScriptExpression, IncludeCommandLineAPI.YES);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ChromeDevToolsDomNode
    click()
    throws Exception {

        // FUTURE: Consider an option to use...
        // https://chromedevtools.github.io/devtools-protocol/tot/Input/#method-dispatchMouseEvent

        runJavaScriptExpression("$0.click()");
        return this;
    }
}
