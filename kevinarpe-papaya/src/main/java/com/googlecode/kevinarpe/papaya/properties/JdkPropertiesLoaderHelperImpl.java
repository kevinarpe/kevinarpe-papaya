package com.googlecode.kevinarpe.papaya.properties;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.input.IInputSource2Utils;
import com.googlecode.kevinarpe.papaya.input.InputSource2;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkPropertiesLoader;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.jdk.properties.RandomAccessList;

import java.io.IOException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class JdkPropertiesLoaderHelperImpl
implements JdkPropertiesLoaderHelper {

    private final JdkPropertiesLoader _jdkPropertiesLoader;
    private final IInputSource2Utils _inputSource2Utils;

    JdkPropertiesLoaderHelperImpl(
        JdkPropertiesLoader jdkPropertiesLoader, IInputSource2Utils inputSource2Utils) {
        _jdkPropertiesLoader = ObjectArgs.checkNotNull(jdkPropertiesLoader, "jdkPropertiesLoader");
        _inputSource2Utils = ObjectArgs.checkNotNull(inputSource2Utils, "inputSource2Utils");
    }

    @Override
    public RandomAccessList<JdkProperty> loadPropertyList(InputSource2 inputSource)
    throws PropertiesLoaderException {
        try {
            if (null == inputSource.getCharacterStream()) {
                RandomAccessList<JdkProperty> x =
                    _jdkPropertiesLoader.load(inputSource.getByteStream());
                return x;
            }
            else {
                RandomAccessList<JdkProperty> x =
                    _jdkPropertiesLoader.load(inputSource.getCharacterStream());
                return x;
            }
        }
        catch (IOException e) {
            throw new PropertiesLoaderException(e);
        }
        finally {
            _inputSource2Utils.closeQuietly(inputSource);
        }
    }
}
