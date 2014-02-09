package com.googlecode.kevinarpe.papaya.logging;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.EnumUtils;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public abstract class AbstractSLF4JLogLevel
implements SLF4JLogLevel {

    private final String _theEnumName;
    private SLF4JLogLevelEnum _theEnum;

    public AbstractSLF4JLogLevel(String theEnumName) {
        _theEnumName = StringArgs.checkNotEmptyOrWhitespace(theEnumName, "theEnumName");
    }

    @Override
    public final SLF4JLogLevelEnum getEnum() {
        if (null == _theEnum) {
            _theEnum = EnumUtils.valueOf(SLF4JLogLevelEnum.class, _theEnumName);
        }
        return _theEnum;
    }
}
