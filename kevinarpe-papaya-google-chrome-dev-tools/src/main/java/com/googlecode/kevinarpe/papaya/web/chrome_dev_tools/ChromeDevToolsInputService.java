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

import com.github.kklisura.cdt.protocol.commands.DOM;
import com.github.kklisura.cdt.protocol.commands.Input;
import com.github.kklisura.cdt.protocol.types.input.DispatchKeyEventType;
import com.googlecode.kevinarpe.papaya.function.ThrowingConsumer;
import com.googlecode.kevinarpe.papaya.function.retry.RetryStrategyFactory;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ChromeDevToolsInputService {

    /**
     * Splits a string of text into characters and sends each as a separate input key event using
     * {@link Input#dispatchKeyEvent(DispatchKeyEventType, Integer, Double, String, String, String, String, String, Integer, Integer, Boolean, Boolean, Boolean, Integer)}.
     * <p>
     * The key events are sent to the DOM node with focus.
     * <p>
     * Implementation note: This is <b>much</b> harder than it appears!  Sending key events using Chrome DevTools
     * Protocol is complex and poorly documented.
     *
     * @param chromeDevToolsInput
     *        See: {@link ChromeDevToolsTab.Data#input}
     *
     * @param type
     *        usually {@link DispatchKeyEventType#KEY_DOWN}
     *
     * @param text
     *        one or more characters to send as key events
     *        <br>Use '\r' or '\n' for "Enter", and '\b' for "Backspace".
     *        <br>Must not be empty, but can be all whitespace.
     *
     * @throws Exception
     *         if send key event fails for any characters
     *
     * @throws IllegalArgumentException
     *         if {@code text} is empty
     *
     * @see Input#dispatchKeyEvent(DispatchKeyEventType, Integer, Double, String, String, String, String, String, Integer, Integer, Boolean, Boolean, Boolean, Integer)
     * @see DOM#focus()
     * @see ChromeDevToolsDomQuerySelector#awaitQuerySelectorByIndexThenRun(String, int, RetryStrategyFactory, ThrowingConsumer) 
     * @see ChromeDevToolsDomNode#focus()
     * @see ChromeDevToolsDomNode#awaitFocus(RetryStrategyFactory)
     */
    void sendKeys(Input chromeDevToolsInput,
                  DispatchKeyEventType type,
                  String text)
    throws Exception;
}
