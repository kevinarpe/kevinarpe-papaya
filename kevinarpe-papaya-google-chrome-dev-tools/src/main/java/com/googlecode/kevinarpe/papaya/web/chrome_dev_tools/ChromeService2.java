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

import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

import java.util.function.Predicate;

/**
 * Why is this interface called {@code ChromeService2} instead of {@code ChromeService}?
 * <br>Because {@link ChromeService} is already used by the CDT library.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ChromeService2 {

    /**
     * Example matching {@link Predicate}: <pre>{@code
     * (ChromeTab chromeTab) -> chromeTab.isPageType()
     *                             &&
     *                          "https://...".equals(chromeTab.getUrl());
     * }</pre>
     * <p>
     * Sample values for a {@link ChromeTab}: <pre>{@code
     *   description = ""
     *   devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33883/devtools/page/01A4C3AE9C80B1CCC54D992A3A5CDB0C"
     *   faviconUrl = "https://www.hkpl.gov.hk/common/favicon.ico"
     *   id = "01A4C3AE9C80B1CCC54D992A3A5CDB0C"
     *   parentId = null
     *   title = "Hong Kong Public Libraries"
     *   type = "page"
     *   url = "https://www.hkpl.gov.hk/en/index.html"
     *   webSocketDebuggerUrl = "ws://localhost:33883/devtools/page/01A4C3AE9C80B1CCC54D992A3A5CDB0C"
     * }</pre>
     *
     * @throws Exception
     *         if zero or many tabs match the predicate {@code isMatch}
     */
    @NonBlocking
    ChromeDevToolsTab
    findChromeTab(Chrome chrome,
                  Predicate<ChromeTab> isMatch)
    throws Exception;

    /**
     * This is a convenience method to call {@link #findChromeTab(Chrome, Predicate)} with retry logic.
     */
    @Blocking
    ChromeDevToolsTab
    awaitChromeTab(Chrome chrome,
                   RetryStrategyFactory retryStrategyFactory,
                   Predicate<ChromeTab> isMatch)
    throws Exception;
}
