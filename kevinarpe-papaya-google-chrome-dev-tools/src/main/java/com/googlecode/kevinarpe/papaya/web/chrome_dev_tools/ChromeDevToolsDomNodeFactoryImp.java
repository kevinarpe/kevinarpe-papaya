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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
public final class ChromeDevToolsDomNodeFactoryImp
implements ChromeDevToolsDomNodeFactory {

    private final ChromeDevToolsInputService inputService;
    private final ChromeDevToolsRuntimeService runtimeService;
    private final RetryService retryService;

    public ChromeDevToolsDomNodeFactoryImp(ChromeDevToolsInputService inputService,
                                           ChromeDevToolsRuntimeService runtimeService,
                                           RetryService retryService) {

        this.inputService = ObjectArgs.checkNotNull(inputService, "inputService");
        this.runtimeService = ObjectArgs.checkNotNull(runtimeService, "runtimeService");
        this.retryService = ObjectArgs.checkNotNull(retryService, "retryService");
    }

    @Override
    public ChromeDevToolsDomNode
    newInstance(final int nodeId, ChromeDevToolsTab chromeTab) {

        final ChromeDevToolsDomNodeImp x =
            new ChromeDevToolsDomNodeImp(nodeId, chromeTab, inputService, runtimeService, retryService);
        return x;
    }
}
