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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class Chrome
implements AutoCloseable {

    public final ChromeLauncher chromeLauncher;
    public final boolean isHeadless;
    public final ChromeService chromeService;
    public final ChromeDevToolsTab chromeTab0;

    public Chrome(ChromeLauncher chromeLauncher,
                  IsHeadless isHeadless,
                  ChromeService chromeService,
                  ChromeDevToolsTab chromeTab0) {

        this.chromeLauncher = ObjectArgs.checkNotNull(chromeLauncher, "chromeLauncher");
        ObjectArgs.checkNotNull(isHeadless, "isHeadless");
        this.isHeadless = IsHeadless.YES.equals(isHeadless);
        this.chromeService = ObjectArgs.checkNotNull(chromeService, "chromeService");
        this.chromeTab0 = ObjectArgs.checkNotNull(chromeTab0, "chromeTab0");
    }

    @Override
    public void
    close()
    throws Exception {

        chromeLauncher.close();

        if (chromeLauncher.isAlive()) {
            throw new Exception("Close failed: Google Chrome process is still alive");
        }
    }
}
