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

import com.github.kklisura.cdt.launch.ChromeLauncher;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalWithReset;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalsWithReset;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FullyTested?  Some code below is impossible to test in a deterministic manner.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class ChromeLauncherServiceImp
implements ChromeLauncherService {

    private final RetryService retryService;
    private final ChromeDevToolsTabFactory chromeDevToolsTabFactory;
    private final ChromeService2 chromeService2;

    public ChromeLauncherServiceImp(RetryService retryService,
                                    ChromeDevToolsTabFactory chromeDevToolsTabFactory,
                                    ChromeService2 chromeService2) {

        this.retryService = ObjectArgs.checkNotNull(retryService, "retryService");

        this.chromeDevToolsTabFactory =
            ObjectArgs.checkNotNull(chromeDevToolsTabFactory, "chromeDevToolsTabFactory");

        this.chromeService2 = ObjectArgs.checkNotNull(chromeService2, "chromeService2");
    }

    /** {@inheritDoc} */
    @Override
    public Chrome
    launchChrome(IsHeadless isHeadless,
                 RetryStrategyFactory retryStrategyFactory)
    throws Exception {

//        final ChromeLauncherConfiguration clc = new ChromeLauncherConfiguration();
        final ChromeLauncher chromeLauncher = new ChromeLauncher();
        @Blocking
        final ChromeService chromeService = _awaitLaunch(chromeLauncher, isHeadless, retryStrategyFactory);
        /* Instance of ChromeTab:
description = ""
devtoolsFrontendUrl = "/devtools/inspector.html?ws=localhost:33061/devtools/page/2F52C90173AE5996E0BD156A9E621E40"
faviconUrl = null
id = "2F52C90173AE5996E0BD156A9E621E40"
parentId = null
title = "New Tab"
type = "page"
url = "chrome://newtab/"
webSocketDebuggerUrl = "ws://localhost:33061/devtools/page/2F52C90173AE5996E0BD156A9E621E40"
        */
        @Blocking
        @EmptyContainerAllowed
        @ReadOnlyContainer
        final Map<String, ChromeTab> chromeTabIdMap = _awaitTabMap(chromeService, retryStrategyFactory);

        // Why create a new tab and close the original(s)?  From time to time, the Page object is unable to navigate to
        // *any* URL.  All give navigation error: "net::ERR_ABORTED".  Google provides no hints.
        final ChromeTab chromeTab1 = chromeService.createTab();

        final ChromeDevToolsTab.Data data = new ChromeDevToolsTab.Data(chromeService, chromeTab1);
        final ChromeDevToolsTab chromeDevToolsTab = chromeDevToolsTabFactory.newInstance(data);
        final Chrome chrome = new Chrome(chromeLauncher, isHeadless, chromeService, chromeDevToolsTab);

        // To be clear: I have seen very rare cases where multiple(!) tabs exist in a new Google Chrome instance.
        // I cannot reliably replicate this issue.  I saw it when running tests using Apache Maven command line build.
        chromeService2.closeChromeTabs(chrome, retryStrategyFactory, new ExactlyCountMatcher(chromeTabIdMap.size()),
            (ChromeTab chromeTab) -> chromeTabIdMap.containsKey(chromeTab.getId()));

        return chrome;
    }

    @Blocking
    private ChromeService
    _awaitLaunch(ChromeLauncher chromeLauncher,
                 IsHeadless isHeadless,
                 RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        final boolean headless = IsHeadless.YES.equals(isHeadless);
        // user-data-dir=/tmp/cdt-user-data-dir10995086840762372025
//        final ChromeArguments ca =  ChromeArguments.defaults(headless).userDataDir("/path-to-user-data-dir").build();
        @Blocking
        final ChromeService x =
            retryService.call(retryStrategyFactory,
                () -> {
                    try {
                        @Blocking
                        final ChromeService z = chromeLauncher.launch(headless);
                        return z;
                    }
                    catch (Exception e) {
                        // @DebugBreakpoint
                        throw e;
                    }
                });
        return x;
/* Sample exception from chromeLauncher.launch(headless):
 * History behind this GLibC bug: https://sourceware.org/bugzilla/show_bug.cgi?id=19329

com.github.kklisura.cdt.launch.exceptions.ChromeProcessTimeoutException: Failed while waiting for chrome to start: Timeout expired! Chrome output: Inconsistency detected by ld.so: ../elf/dl-tls.c: 538: _dl_allocate_tls_init: Assertion `listp->slotinfo[cnt].gen <= GL(dl_tls_generation)' failed!

	at com.github.kklisura.cdt.launch.ChromeLauncher.waitForDevToolsServer(ChromeLauncher.java:368)
	at com.github.kklisura.cdt.launch.ChromeLauncher.launchChromeProcess(ChromeLauncher.java:302)
	at com.github.kklisura.cdt.launch.ChromeLauncher.launch(ChromeLauncher.java:143)
	at com.github.kklisura.cdt.launch.ChromeLauncher.launch(ChromeLauncher.java:170)
	at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeLauncherServiceImp.launchChrome(ChromeLauncherServiceImp.java:47)
	at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeDevToolsRuntimeServiceTest.beforeEachTestMethod(ChromeDevToolsRuntimeServiceTest.java:25)
 */
    }

    private static final ThreadLocalWithReset<LinkedHashMap<String, ChromeTab>> threadLocalChromeTabIdMap =
        ThreadLocalsWithReset.newInstanceForLinkedHashMap();

    @Blocking
    @EmptyContainerAllowed
    @ReadOnlyContainer
    private Map<String, ChromeTab>
    _awaitTabMap(ChromeService chromeService,
                 RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        @Blocking
        @EmptyContainerAllowed
        @ReadOnlyContainer
        final List<ChromeTab> chromeTabList = _awaitTabList(chromeService, retryStrategyFactory);

        final LinkedHashMap<String, ChromeTab> chromeTabIdMap = threadLocalChromeTabIdMap.getAndReset();
        for (final ChromeTab chromeTab : chromeTabList) {

            chromeTabIdMap.put(chromeTab.getId(), chromeTab);
        }
        return chromeTabIdMap;
    }

    @Blocking
    @EmptyContainerAllowed
    @ReadOnlyContainer
    private List<ChromeTab>
    _awaitTabList(ChromeService chromeService,
                  RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        @Blocking
        @EmptyContainerAllowed
        @ReadOnlyContainer
        final List<ChromeTab> x =
            retryService.call(retryStrategyFactory,
                () -> {
                    try {
                        final List<ChromeTab> z = chromeService.getTabs();
                        return z;
                    }
                    catch (Exception e) {
                        // @DebugBreakpoint
                        throw e;
                    }
                });
        return x;
/* Sample exception from chromeService.getTabs():
com.github.kklisura.cdt.services.exceptions.ChromeServiceException: Failed sending HTTP request.

	at com.github.kklisura.cdt.services.impl.ChromeServiceImpl.request(ChromeServiceImpl.java:292)
	at com.github.kklisura.cdt.services.impl.ChromeServiceImpl.getTabs(ChromeServiceImpl.java:127)
	at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeLauncherServiceImp.launchChrome(ChromeLauncherServiceImp.java:42)
	at com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.ChromeDevToolsRuntimeServiceTest.beforeEachTestMethod(ChromeDevToolsRuntimeServiceTest.java:24)
Caused by: java.net.ConnectException: Connection refused (Connection refused)
	at java.net.PlainSocketImpl.socketConnect(Native Method)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:476)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:218)
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:200)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:394)
	at java.net.Socket.connect(Socket.java:606)
	at java.net.Socket.connect(Socket.java:555)
	at sun.net.NetworkClient.doConnect(NetworkClient.java:180)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:463)
	at sun.net.www.http.HttpClient.openServer(HttpClient.java:558)
	at sun.net.www.http.HttpClient.<init>(HttpClient.java:242)
	at sun.net.www.http.HttpClient.New(HttpClient.java:339)
	at sun.net.www.http.HttpClient.New(HttpClient.java:357)
	at sun.net.www.protocol.http.HttpURLConnection.getNewHttpClient(HttpURLConnection.java:1226)
	at sun.net.www.protocol.http.HttpURLConnection.plainConnect0(HttpURLConnection.java:1162)
	at sun.net.www.protocol.http.HttpURLConnection.plainConnect(HttpURLConnection.java:1056)
	at sun.net.www.protocol.http.HttpURLConnection.connect(HttpURLConnection.java:990)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1570)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1498)
	at java.net.HttpURLConnection.getResponseCode(HttpURLConnection.java:480)
	at com.github.kklisura.cdt.services.impl.ChromeServiceImpl.request(ChromeServiceImpl.java:273)
	... 34 more
 */
    }
}
