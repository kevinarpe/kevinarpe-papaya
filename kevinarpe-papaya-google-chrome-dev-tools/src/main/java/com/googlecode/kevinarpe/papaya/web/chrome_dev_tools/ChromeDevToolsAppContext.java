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
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrowerImpl;
import com.googlecode.kevinarpe.papaya.function.retry.BasicRetryStrategyImp;
import com.googlecode.kevinarpe.papaya.function.retry.CollectionIndexMatcher;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;
import com.googlecode.kevinarpe.papaya.function.retry.RetryServiceImp;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;
import com.googlecode.kevinarpe.papaya.string.MessageFormatter;

import java.time.Duration;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ChromeDevToolsAppContext {

    public final RetryStrategyFactory basicFiveSecondRetryStrategyFactory;
    public final ChromeDevToolsInputService chromeDevToolsInputService;
    public final ChromeDevToolsRuntimeService chromeDevToolsRuntimeService;
    public final ChromeDevToolsTabFactory chromeDevToolsTabFactory;
    public final ChromeService2 chromeService2;
    public final ChromeLauncherService chromeLauncherService;
    public final ChromeDevToolsDomNodeFactory chromeDevToolsDomNodeFactory;
    public final ChromeDevToolsDomQuerySelectorFactory chromeDevToolsDomQuerySelectorFactory;

    public ChromeDevToolsAppContext(MessageFormatter messageFormatter) {

        ObjectArgs.checkNotNull(messageFormatter, "messageFormatter");

        this.basicFiveSecondRetryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(49)
                .beforeRetrySleepDuration(Duration.ofMillis(100))
                .build();

        final ExceptionThrower exceptionThrower = new ExceptionThrowerImpl(messageFormatter);

        this.chromeDevToolsInputService = new ChromeDevToolsInputServiceImp(exceptionThrower);

        this.chromeDevToolsRuntimeService = new ChromeDevToolsRuntimeServiceImp(exceptionThrower);

        final RetryService retryService =
            new RetryServiceImp(
                // Intentional: Both the first and last are most interesting -- others: much less.
                CollectionIndexMatcher.FIRST_AND_LAST_ONLY);

        this.chromeDevToolsTabFactory = new ChromeDevToolsTabFactoryImp(retryService);

        this.chromeService2 =
            new ChromeService2Imp(
                chromeDevToolsTabFactory,
                retryService,
                exceptionThrower);

        this.chromeLauncherService = new ChromeLauncherServiceImp(retryService, chromeDevToolsTabFactory, chromeService2);

        this.chromeDevToolsDomNodeFactory =
            new ChromeDevToolsDomNodeFactoryImp(
                chromeDevToolsInputService,
                chromeDevToolsRuntimeService,
                retryService);

        this.chromeDevToolsDomQuerySelectorFactory =
            new ChromeDevToolsDomQuerySelectorFactoryImp(
                chromeDevToolsDomNodeFactory,
                retryService,
                exceptionThrower);
    }
}
