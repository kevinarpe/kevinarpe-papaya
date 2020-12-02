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
import com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.util.ChromeDevToolsTestGlobals;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ChromeService2ImpTest
extends ChromeDevToolsTest {

    @Test
    public void pass()
    throws Exception {

        appContext().chromeService2.findChromeTab(chrome(),
            (ChromeTab chromeTab) -> true);

        appContext().chromeService2.awaitChromeTab(chrome(), ChromeDevToolsTestGlobals.retryStrategyFactoryOnceZeroSleep,
            (ChromeTab chromeTab) -> true);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Failed to match any Chrome tabs$")
    public void fail_findChromeTab_WhenZeroTabsMatch()
    throws Exception {

        appContext().chromeService2.findChromeTab(chrome(),
            (ChromeTab chromeTab) -> false);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Found two matching Chrome tabs:.*$")
    public void fail_findChromeTab_WhenMultipleTabsMatch()
    throws Exception {

        final ChromeDevToolsTab chromeTab2 = appContext().chromeDevToolsTabFactory.createTab(chrome().chromeService);

        appContext().chromeService2.findChromeTab(chrome(),
            (ChromeTab chromeTab) -> true);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Failed to match any Chrome tabs$")
    public void fail_awaitChromeTab_WhenZeroTabsMatch()
    throws Exception {

        appContext().chromeService2.awaitChromeTab(chrome(), ChromeDevToolsTestGlobals.retryStrategyFactoryOnceZeroSleep,
            (ChromeTab chromeTab) -> false);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Found two matching Chrome tabs:.*$")
    public void fail_awaitChromeTab_WhenMultipleTabsMatch()
    throws Exception {

        final ChromeDevToolsTab chromeTab2 = appContext().chromeDevToolsTabFactory.createTab(chrome().chromeService);

        appContext().chromeService2.awaitChromeTab(chrome(), ChromeDevToolsTestGlobals.retryStrategyFactoryOnceZeroSleep,
            (ChromeTab chromeTab) -> true);
    }
}
