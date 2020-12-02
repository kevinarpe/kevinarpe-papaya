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

import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;
import com.googlecode.kevinarpe.papaya.string.ThrowingMessageFormatterImpl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ChromeDevToolsTest {

    private ChromeDevToolsAppContext appContext;
    private Chrome chrome;

    public ChromeDevToolsAppContext
    appContext() {
        return appContext;
    }

    public Chrome
    chrome() {
        return chrome;
    }

    @BeforeMethod
    public final void beforeEachTestMethod()
    throws Exception {

        System.out.println("ChromeDevToolsTest.beforeEachTestMethod: BEGIN");
        this.appContext = new ChromeDevToolsAppContext(ThrowingMessageFormatterImpl.INSTANCE);
        this.chrome =
            appContext.chromeLauncherService.launchChrome(IsHeadless.YES, appContext.basicFiveSecondRetryStrategyFactory);
        System.out.println("ChromeDevToolsTest.beforeEachTestMethod: END");
    }

    @AfterMethod
    public final void afterEachTestMethod()
    throws Exception {

        System.out.println("ChromeDevToolsTest.afterEachTestMethod: BEGIN");
        this.chrome.chromeTab0.awaitClose(RetryStrategyFactory.NO_RETRY);
        this.chrome.close();
        System.out.println("ChromeDevToolsTest.afterEachTestMethod: END");
    }
}
