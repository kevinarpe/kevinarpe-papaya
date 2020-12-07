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
import com.github.kklisura.cdt.protocol.commands.Page;
import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

/**
 * FullyTested?  Some code below is impossible to test in a deterministic manner.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Multiple global singletons -- per 'data'
@FullyTested
public final class ChromeDevToolsTabImp
implements ChromeDevToolsTab {

    private final ChromeService chromeService;
    private final ChromeTab chromeTab;
    private final ChromeDevToolsService chromeDevToolsService;
    private final Page page;
    private final DOM dom;
    private final Input input;
    private final Runtime runtime;
    private final RetryService retryService;

    public ChromeDevToolsTabImp(ChromeService chromeService,
                                ChromeTab chromeTab,
                                RetryService retryService) {

        this.chromeService = ObjectArgs.checkNotNull(chromeService, "chromeService");
        this.chromeTab = ObjectArgs.checkNotNull(chromeTab, "chromeTab");
        this.chromeDevToolsService = chromeService.createDevToolsService(chromeTab);
        this.page = this.chromeDevToolsService.getPage();
        this.dom = this.chromeDevToolsService.getDOM();
        this.input = this.chromeDevToolsService.getInput();
        this.runtime = this.chromeDevToolsService.getRuntime();
        this.retryService = ObjectArgs.checkNotNull(retryService, "retryService");
    }

    @Override
    public ChromeService
    getChromeService() {
        return chromeService;
    }

    @Override
    public ChromeTab
    getChromeTab() {
        return chromeTab;
    }

    /** {@inheritDoc} */
    @Override
    public ChromeDevToolsService
    getChromeDevToolsService() {
        return chromeDevToolsService;
    }

    /** {@inheritDoc} */
    @Override
    public Page
    getPage() {
        return page;
    }

    /** {@inheritDoc} */
    @Override
    public DOM
    getDOM() {
        return dom;
    }

    /** {@inheritDoc} */
    @Override
    public Input
    getInput() {
        return input;
    }

    /** {@inheritDoc} */
    @Override
    public Runtime
    getRuntime() {
        return runtime;
    }

    /** {@inheritDoc} */
    @Override
    public String
    getDocumentOuterHTML()
    throws Exception {

        final Node documentNode = dom.getDocument();
        final String html = dom.getOuterHTML(documentNode.getNodeId(), null, null);
        return html;
    }

    /** {@inheritDoc} */
    @Override
    public void
    awaitClose(RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        chromeDevToolsService.close();
        retryService.run(retryStrategyFactory,
            () -> {
                if (false == chromeDevToolsService.isClosed()) {
                    throw new IllegalStateException("ChromeDevToolsService not yet closed for tab");
                }
            });
        chromeService.closeTab(chromeTab);
    }
}
