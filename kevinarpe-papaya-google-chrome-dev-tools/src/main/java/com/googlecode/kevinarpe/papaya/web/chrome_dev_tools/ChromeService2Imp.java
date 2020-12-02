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

import com.github.kklisura.cdt.services.types.ChromeTab;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class ChromeService2Imp
implements ChromeService2 {

    private final ChromeDevToolsTabFactory chromeDevToolsTabFactory;
    private final RetryService retryService;
    private final ExceptionThrower exceptionThrower;

    public ChromeService2Imp(ChromeDevToolsTabFactory chromeDevToolsTabFactory,
                             RetryService retryService,
                             ExceptionThrower exceptionThrower) {

        this.chromeDevToolsTabFactory =
            ObjectArgs.checkNotNull(chromeDevToolsTabFactory, "chromeDevToolsTabFactory");

        this.retryService = ObjectArgs.checkNotNull(retryService, "retryService");

        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public ChromeDevToolsTab
    findChromeTab(Chrome chrome, Predicate<ChromeTab> isMatch)
    throws Exception {

        final ChromeTab chromeTab = _findChromeTab(chrome, isMatch);
        final ChromeDevToolsTab.Data data = new ChromeDevToolsTab.Data(chrome.chromeService, chromeTab);
        final ChromeDevToolsTab x = chromeDevToolsTabFactory.newInstance(data);
        return x;
    }

    private ChromeTab
    _findChromeTab(Chrome chrome, Predicate<ChromeTab> isMatch)
    throws Exception {

        @Nullable
        ChromeTab nullableChromeTab = null;

        @EmptyContainerAllowed
        @ReadOnlyContainer
        final List<ChromeTab> chromeTabList = chrome.chromeService.getTabs();
        for (final ChromeTab chromeTab : chromeTabList) {

            if (isMatch.test(chromeTab)) {

                if (null != nullableChromeTab) {

                    throw exceptionThrower.throwCheckedException(Exception.class,
                        "Found two matching Chrome tabs:"
                            + "%n\t(1) URL: [%s]"
                            + "%n\t(2) URL: [%s]",
                        nullableChromeTab.getUrl(),
                        chromeTab.getUrl());
                }
                nullableChromeTab = chromeTab;
            }
        }
        if (null == nullableChromeTab) {
            throw new Exception("Failed to match any Chrome tabs");
        }
        return nullableChromeTab;
    }
/*
chromeTabList = {Arrays$ArrayList@3302}  size = 6
 0 = {ChromeTab@3305}
  description = ""
  devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33883/devtools/page/8EA98095569AAAE49353562B44CF234D"
  faviconUrl = null
  id = "8EA98095569AAAE49353562B44CF234D"
  parentId = null
  title = ""
  type = "page"
  url = "https://webcat.hkpl.gov.hk/wicket/bookmarkable/com.vtls.chamo.webapp.component.patron.PatronAccountPage?theme=WEB&locale=en"
  webSocketDebuggerUrl = "ws://localhost:33883/devtools/page/8EA98095569AAAE49353562B44CF234D"
 1 = {ChromeTab@3303}
  description = ""
  devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33883/devtools/page/CB3829D1F2D19A78182AEFEF70C42849"
  faviconUrl = null
  id = "CB3829D1F2D19A78182AEFEF70C42849"
  parentId = null
  title = "CryptoTokenExtension"
  type = "background_page"
  url = "chrome-extension://kmendfapggjehodndflmmgagdbamhnfd/_generated_background_page.html"
  webSocketDebuggerUrl = "ws://localhost:33883/devtools/page/CB3829D1F2D19A78182AEFEF70C42849"
 2 = {ChromeTab@3306}
  description = ""
  devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33883/devtools/page/7A6789D1009BF3DAE6381AE8C0DC4397"
  faviconUrl = null
  id = "7A6789D1009BF3DAE6381AE8C0DC4397"
  parentId = null
  title = "Google Network Speech"
  type = "background_page"
  url = "chrome-extension://neajdppkdcdipfabeoofebfddakdcjhd/_generated_background_page.html"
  webSocketDebuggerUrl = "ws://localhost:33883/devtools/page/7A6789D1009BF3DAE6381AE8C0DC4397"
 3 = {ChromeTab@3307}
  description = ""
  devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33883/devtools/page/3C4BD7D06A1BECB7AB8E87440DA09FF0"
  faviconUrl = null
  id = "3C4BD7D06A1BECB7AB8E87440DA09FF0"
  parentId = null
  title = "Feedback"
  type = "background_page"
  url = "chrome-extension://gfdkimpbcpahaombhbimeihdjnejgicl/_generated_background_page.html"
  webSocketDebuggerUrl = "ws://localhost:33883/devtools/page/3C4BD7D06A1BECB7AB8E87440DA09FF0"
 4 = {ChromeTab@3308}
  description = ""
  devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33883/devtools/page/06732B192719D91DD5C091E91D8777D7"
  faviconUrl = null
  id = "06732B192719D91DD5C091E91D8777D7"
  parentId = null
  title = "Google Hangouts"
  type = "background_page"
  url = "chrome-extension://nkeimhogjdpnpccoofpliimaahmaaome/background.html"
  webSocketDebuggerUrl = "ws://localhost:33883/devtools/page/06732B192719D91DD5C091E91D8777D7"
 5 = {ChromeTab@3309}
  description = ""
  devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33883/devtools/page/01A4C3AE9C80B1CCC54D992A3A5CDB0C"
  faviconUrl = "https://www.hkpl.gov.hk/common/favicon.ico"
  id = "01A4C3AE9C80B1CCC54D992A3A5CDB0C"
  parentId = null
  title = "Hong Kong Public Libraries"
  type = "page"
  url = "https://www.hkpl.gov.hk/en/index.html"
  webSocketDebuggerUrl = "ws://localhost:33883/devtools/page/01A4C3AE9C80B1CCC54D992A3A5CDB0C"
 */

    /** {@inheritDoc} */
    @Blocking
    @Override
    public ChromeDevToolsTab
    awaitChromeTab(Chrome chrome,
                   RetryStrategyFactory retryStrategyFactory,
                   Predicate<ChromeTab> isMatch)
    throws Exception {

        final ChromeDevToolsTab x =
            retryService.call(retryStrategyFactory,
                () -> findChromeTab(chrome, isMatch));

        return x;
    }
}
