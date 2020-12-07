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
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.function.retry.BasicRetryStrategyImp;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategy;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ChromeDevToolsTab {

    ChromeService
    getChromeService();

    ChromeTab
    getChromeTab();

    /**
     * @see ChromeService#createDevToolsService(ChromeTab)
     */
    ChromeDevToolsService
    getChromeDevToolsService();

    /**
     * @see ChromeDevToolsService#getPage()
     */
    Page
    getPage();

    /**
     * @see ChromeDevToolsService#getDOM()
     */
    DOM
    getDOM();

    /**
     * @see ChromeDevToolsService#getInput()
     */
    Input
    getInput();

    /**
     * @see ChromeDevToolsService#getRuntime()
     */
    Runtime
    getRuntime();

    /**
     * This is a convenience method to call:
     * {@code getDOM().getOuterHTML(getDOM().getDocument().getNodeId(), null, null)}
     */
    String getDocumentOuterHTML()
    throws Exception;

    /**
     * <ol>
     *     <li>Calls {@link ChromeDevToolsService#close()}</li>
     *     <li>Waits for {@link ChromeDevToolsService#isClosed()} to be true</li>
     *     <li>Calls {@link ChromeService#closeTab(ChromeTab)}</li>
     * </ol>
     *
     * @param retryStrategyFactory
     *        factory to generate an instance of {@link RetryStrategy}
     *        <br>See: {@link BasicRetryStrategyImp#factoryBuilder()}
     *
     * @throws Exception
     *         if method above throws
     *         <br>or, thrown from {@link RetryStrategy#beforeRetry()}
     */
    @Blocking
    void awaitClose(RetryStrategyFactory retryStrategyFactory)
    throws Exception;
}
