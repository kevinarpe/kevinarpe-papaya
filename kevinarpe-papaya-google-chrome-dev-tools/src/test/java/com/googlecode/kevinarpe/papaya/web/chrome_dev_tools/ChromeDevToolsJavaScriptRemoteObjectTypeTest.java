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

import com.github.kklisura.cdt.protocol.types.runtime.RemoteObjectType;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.container.ImmutableFullEnumMap;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ChromeDevToolsJavaScriptRemoteObjectTypeTest {

    @Test
    public void pass()
    throws Exception {

        final ImmutableFullEnumMap.Builder<RemoteObjectType, ChromeDevToolsJavaScriptRemoteObjectType<?>> b =
            ImmutableFullEnumMap.builder(RemoteObjectType.class);

        @EmptyContainerAllowed
        final Field[] fieldArr = ChromeDevToolsJavaScriptRemoteObjectType.class.getDeclaredFields();
        for (final Field field : fieldArr) {

            final int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {

                final ChromeDevToolsJavaScriptRemoteObjectType<?> z =
                    (ChromeDevToolsJavaScriptRemoteObjectType<?>) field.get(null);

                b.put(z.type, z);
            }
        }
        // This will throw if any RemoteObjectType's are missing.
        final ImmutableFullEnumMap<RemoteObjectType, ChromeDevToolsJavaScriptRemoteObjectType<?>> map = b.build();
    }
}
