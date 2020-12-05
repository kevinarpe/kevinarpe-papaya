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
import com.googlecode.kevinarpe.papaya.function.retry.BasicRetryStrategyImp;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategy;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ChromeLauncherService {

    /**
     * Launches Google Chrome.
     *
     * @param isHeadless
     *        if {@link IsHeadless#NO}, then Google Chrome will not appear as a GUI application.  It will be hidden, and
     *        will not take focus from other GUI applications.  Use this mode for background jobs runs from crontab.
     *        <br>If {@link IsHeadless#YES}, then Google Chrome will appears a regular GUI application and will take
     *        focus from other GUI applications.  Use this mode when debugging automation code.
     *
     * @param retryStrategyFactory
     *        factory to generate an instance of {@link RetryStrategy}
     *        <br>See: {@link BasicRetryStrategyImp#factoryBuilder()}
     *        <br>See: {@link ChromeDevToolsAppContext#basicFiveSecondRetryStrategyFactory}
     *
     * @throws Exception
     *         on any error, including fail to start or fail to create new tab
     *
     * @see Chrome#close()
     * @see ChromeDevToolsTabFactory#createTab(ChromeService)
     */
    Chrome launchChrome(IsHeadless isHeadless, RetryStrategyFactory retryStrategyFactory)
    throws Exception;
}
