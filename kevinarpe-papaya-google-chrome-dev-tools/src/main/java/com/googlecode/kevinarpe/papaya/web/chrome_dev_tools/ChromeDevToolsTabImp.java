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

    private final Data data;
    private final RetryService retryService;

    public ChromeDevToolsTabImp(Data data, RetryService retryService) {

        this.data = ObjectArgs.checkNotNull(data, "chromeTab");
        this.retryService = ObjectArgs.checkNotNull(retryService, "retryService");
    }

    @Override
    public Data
    getData() {
        return data;
    }

    /** {@inheritDoc} */
    @Override
    public void
    awaitClose(RetryStrategyFactory retryStrategyFactory)
    throws Exception {

        data.chromeDevToolsService.close();
        retryService.run(retryStrategyFactory,
            () -> {
                if (false == data.chromeDevToolsService.isClosed()) {
                    throw new IllegalStateException("ChromeDevToolsService not yet closed for tab");
                }
            });
        data.chromeService.closeTab(data.chromeTab);
    }
}
