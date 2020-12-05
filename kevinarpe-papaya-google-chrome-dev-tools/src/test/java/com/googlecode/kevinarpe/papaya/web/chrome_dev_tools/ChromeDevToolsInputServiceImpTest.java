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

import com.github.kklisura.cdt.protocol.commands.Input;
import com.github.kklisura.cdt.protocol.types.input.DispatchKeyEventType;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ChromeDevToolsDomQuerySelectorImpTest
 */
public class ChromeDevToolsInputServiceImpTest
extends ChromeDevToolsTest {

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Internal error: Unsupported char.*$")
    public void sendKeys_FailWhenUnknownCharacter()
    throws Exception {

        final String nulChar = "\u0000";

        appContext().chromeDevToolsInputService.sendKeys(
            chrome().chromeTab0.getData().input, DispatchKeyEventType.KEY_DOWN, nulChar);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Failed to send char.*$")
    public void sendKeys_FailToSendChar()
    throws Exception {

        final Input mockChromeDevToolsInput = Mockito.mock(Input.class);
        final DispatchKeyEventType type = DispatchKeyEventType.KEY_DOWN;
        final ChromeDevToolsInputServiceImp._Data data = ChromeDevToolsInputServiceImp.charMap.get('Z');
        final RuntimeException runtimeException = new RuntimeException("dummy");

        Mockito.doThrow(runtimeException)
            .when(mockChromeDevToolsInput)
            .dispatchKeyEvent(type, data.nullableModifiers, data.nullableTimestamp,
                data.nullableText, data.nullableUnmodifiedText, data.nullableKeyIdentifier, data.nullableCode,
                data.nullableKey, data.nullableWindowsVirtualKeyCode, data.nullableNativeVirtualKeyCode,
                data.nullableAutoRepeat, data.nullableIsKeypad, data.nullableIsSystemKey, data.nullableLocation);

        try {
            appContext().chromeDevToolsInputService.sendKeys(
                mockChromeDevToolsInput, type, data.nullableText);
        }
        catch (Exception e) {

            Assert.assertSame(e.getCause(), runtimeException);
            throw e;
        }
    }
}
