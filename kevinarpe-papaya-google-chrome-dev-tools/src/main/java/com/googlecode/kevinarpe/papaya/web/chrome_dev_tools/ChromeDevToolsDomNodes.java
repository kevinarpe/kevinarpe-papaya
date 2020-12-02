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

import com.googlecode.kevinarpe.papaya.argument.IntArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ChromeDevToolsDomNodes {

    /**
     * Minimum valid DOM node ID
     */
    public static final int MIN_NODE_ID = 1;

    /**
     * Checks if a DOM node ID is greater or equal to {@link #MIN_NODE_ID}.
     *
     * @param nodeId
     *        DOM node ID to check
     *
     * @param argName
     *        argument name for {@code nodeId} -- used in exception message
     *        <br>Ex: {@code "productTableHeaderRow"}
     *
     * @return input value ({@code nodeId})
     *
     * @throws IllegalArgumentException
     *         if value of {@code nodeId} is less than {@link #MIN_NODE_ID}
     *
     * @see IntArgs#checkMinValue(int, int, String)
     */
    public static int
    checkNodeId(final int nodeId, String argName) {

        IntArgs.checkMinValue(nodeId, MIN_NODE_ID, argName);
        return nodeId;
    }
}
